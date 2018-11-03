package com.assessment.trade_reporting.service.impl;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;
import static java.util.stream.Collectors.toMap;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.model.TradeType;
import com.assessment.trade_reporting.service.ReportingService;
import com.assessment.trade_reporting.utils.converter.TradeTypeToSettlementTypeConverter;

public class DailyTradeSettlementAmountBasedEntityRankingReportingServiceImpl implements ReportingService<Trade> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DailyTradeSettlementAmountBasedEntityRankingReportingServiceImpl.class);
	
	private Function<TradeType, String> tradeTypeToSettlemetTypeConverter = new TradeTypeToSettlementTypeConverter();
	private Consumer<String> reportConsumer = (report) -> LOGGER.info(report);
	
	@Override
	public void publishReport(Collection<Trade> data) {
		Map<TradeType, Map<LocalDate, Map<Integer, String>>> tradeTypeToDailyEntityRankingsMapping = data.stream()
				.collect(groupingBy(Trade::getTradeType,
					groupingBy(Trade::getSettlementDate, 
							collectingAndThen(
									groupingBy(Trade::getEntity, 
											summingDouble(Trade::getTradeAmount)),
												entityAmountMapping -> sortAndRank(entityAmountMapping)))));
		
		tradeTypeToDailyEntityRankingsMapping.entrySet()
			.stream()
			.forEach(e -> publishReport(tradeTypeToSettlemetTypeConverter.apply(e.getKey()), e.getValue()));
	}

	private void publishReport(String settlementType, Map<LocalDate, Map<Integer, String>> dailyEntityRankings) {
		reportConsumer.accept(String.format("Based on %s amount", settlementType));
		dailyEntityRankings.entrySet()
		 .stream()
		 .sorted(Collections.reverseOrder(Map.Entry.comparingByKey())) //sort by date in desc
		 .forEach(e -> reportConsumer.accept(String.format("Settelements on %s are %s", e.getKey(), prettyPrint(e.getValue()))));
	}
	
	private String prettyPrint(Map<Integer, String> entiyRankings) {
		StringBuilder sb = new StringBuilder();
		entiyRankings.entrySet()
			.stream()
			.forEach(e -> sb.append("Rank ")
							.append(e.getKey())
							.append(" = ")
							.append(e.getValue())
							.append(", "));
		
		return sb.substring(0, sb.length() -2);
	}
	
	private Map<Integer, String> sortAndRank(Map<String, Double> entityAmountMapping) {
		final AtomicInteger rank = new AtomicInteger(1);
		
		return entityAmountMapping
				.entrySet()
				.stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(e -> rank.getAndIncrement(), Map.Entry::getKey, (e1, e2) -> e2,
		                LinkedHashMap::new));
	}
	
	public void setReportConsumer(Consumer<String> reportConsumer) {
		this.reportConsumer = reportConsumer;
	}
	
}
