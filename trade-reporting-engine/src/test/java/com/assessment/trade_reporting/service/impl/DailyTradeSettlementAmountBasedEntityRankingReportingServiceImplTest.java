package com.assessment.trade_reporting.service.impl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.model.TradeType;

public class DailyTradeSettlementAmountBasedEntityRankingReportingServiceImplTest {
	
	@Mock
	private Consumer<String> reportConsumer;
	
	private DailyTradeSettlementAmountBasedEntityRankingReportingServiceImpl subject;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		subject = new DailyTradeSettlementAmountBasedEntityRankingReportingServiceImpl();
		subject.setReportConsumer(reportConsumer);
	}
	
	@Test
	public void publishReports() {
		//GIVEN
		Collection<Trade> trades = new ArrayList<>();
		trades.add(new Trade("ent1", TradeType.BUY, 10, LocalDate.of(2018, Month.OCTOBER, 1)));
		trades.add(new Trade("ent2", TradeType.BUY, 20, LocalDate.of(2018, Month.OCTOBER, 1)));
		
		trades.add(new Trade("ent1", TradeType.SELL, 30, LocalDate.of(2018, Month.OCTOBER, 2)));
		trades.add(new Trade("ent2", TradeType.BUY, 10, LocalDate.of(2018, Month.OCTOBER, 2)));
		trades.add(new Trade("ent3", TradeType.SELL, 10, LocalDate.of(2018, Month.OCTOBER, 2)));
		
		trades.add(new Trade("ent1", TradeType.BUY, 10, LocalDate.of(2018, Month.OCTOBER, 3)));
		trades.add(new Trade("ent2", TradeType.BUY, 30, LocalDate.of(2018, Month.OCTOBER, 3)));
		trades.add(new Trade("ent3", TradeType.BUY, 20, LocalDate.of(2018, Month.OCTOBER, 3)));
		trades.add(new Trade("ent1", TradeType.SELL, 10, LocalDate.of(2018, Month.OCTOBER, 3)));
		trades.add(new Trade("ent2", TradeType.SELL, 20, LocalDate.of(2018, Month.OCTOBER, 3)));
		trades.add(new Trade("ent3", TradeType.SELL, 30, LocalDate.of(2018, Month.OCTOBER, 3)));
		
		//WHEN
		subject.publishReport(trades);
		
		//THEN
		verify(reportConsumer, times(7)).accept(ArgumentMatchers.anyString());
		verify(reportConsumer).accept("Based on outgoing amount");
		verify(reportConsumer).accept("Settelements on 2018-10-03 are Rank 1 = ent2, Rank 2 = ent3, Rank 3 = ent1");
		verify(reportConsumer).accept("Settelements on 2018-10-02 are Rank 1 = ent2");
		verify(reportConsumer).accept("Settelements on 2018-10-01 are Rank 1 = ent2, Rank 2 = ent1");
		verify(reportConsumer).accept("Based on incoming amount");
		verify(reportConsumer).accept("Settelements on 2018-10-03 are Rank 1 = ent3, Rank 2 = ent2, Rank 3 = ent1");
		verify(reportConsumer).accept("Settelements on 2018-10-02 are Rank 1 = ent1, Rank 2 = ent3");
	}

}
