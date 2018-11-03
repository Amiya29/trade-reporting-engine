package com.assessment.trade_reporting.service.impl;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.model.TradeType;
import com.assessment.trade_reporting.service.TradesReportingService;
import com.assessment.trade_reporting.utils.converter.TradeTypeToSettlementTypeConverter;

public class DailyTradeSettlementAmountReportingServiceImpl implements TradesReportingService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DailyTradeSettlementAmountReportingServiceImpl.class);
	
	private Function<TradeType, String> tradeTypeToSettlemetTypeConverter = new TradeTypeToSettlementTypeConverter();
	private Consumer<String> reportConsumer = (report) -> LOGGER.info(report);

	@Override
	public void publishReport(Collection<Trade> data) {
		
		Map<TradeType, Map<LocalDate, Double>> dailySettlementAmount = data.stream()
					.collect(groupingBy(Trade::getTradeType,
								groupingBy(Trade::getSettlementDate, 
										summingDouble(Trade::getTradeAmount))));
		
		dailySettlementAmount.entrySet()
		  .stream()
		  .sorted(Collections.reverseOrder(Map.Entry.comparingByKey())) //sort by date in desc
		  .forEach(e -> reportConsumer.accept(
				  String.format("Amount in USD settled %s = %s", 
						  tradeTypeToSettlemetTypeConverter.apply(e.getKey()), e.getValue())));
	}
	
	public void setReportConsumer(Consumer<String> reportConsumer) {
		this.reportConsumer = reportConsumer;
	}

}
