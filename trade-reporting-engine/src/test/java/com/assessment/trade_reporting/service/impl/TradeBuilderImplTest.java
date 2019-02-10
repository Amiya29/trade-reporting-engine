package com.assessment.trade_reporting.service.impl;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.model.TradeType;
import com.assessment.trade_reporting.utils.TradeCalendar;

public class TradeBuilderImplTest {
	
	private TradeBuilderImpl subject;
	
	@Mock
	private TradeCalendar calendar;
	
	@Mock
	private TradeCalendar defaultCalendar;
	
	private static final String CURRENCY_AED = "AED";
	private static final String CURRENCY_DEFAULT_WORK_WEEK = "USD";
	
	private static final String ENTITY = "entity";
	private static final TradeType TRADE_TYPE = TradeType.BUY;
	private static final float FX_RATE = 1.0f;
	private static final int UNITS = 10;
	private static final float UNIT_PRICE = 2.0f;
	private static final LocalDate SETTLEMENT_DATE = LocalDate.of(2018, Month.NOVEMBER, 2);
	private static final LocalDate SETTLEMENT_DATE_NEXT_WORKING_DAY = LocalDate.of(2018, Month.NOVEMBER, 5);
	
	@SuppressWarnings("serial")
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		subject = new TradeBuilderImpl(new HashMap<String, TradeCalendar>() {{
			put(CURRENCY_AED, calendar);
		}});
		
		subject.setDefaultCalendar(defaultCalendar);
		
		subject.setEntity(ENTITY);
		subject.setFxRate(FX_RATE);
		subject.setSettlementDate(SETTLEMENT_DATE);
		subject.setTradeType(TRADE_TYPE);
		subject.setUnitPrice(UNIT_PRICE);
		subject.setUnits(UNITS);
		
		when(calendar.nextWorkingDay(SETTLEMENT_DATE)).thenReturn(SETTLEMENT_DATE_NEXT_WORKING_DAY);
		when(defaultCalendar.nextWorkingDay(SETTLEMENT_DATE)).thenReturn(SETTLEMENT_DATE_NEXT_WORKING_DAY);
	}
	
	@Test
	public void buildTrade_settlementDateHoliday() {
		//GIVEN
		subject.setCurrency(CURRENCY_AED);
		when(calendar.isHoliday(SETTLEMENT_DATE)).thenReturn(true);
		
		//WHEN
		Trade trade = subject.build();
		
		//THEN
		verify(calendar).nextWorkingDay(any());
		verify(calendar).isHoliday(SETTLEMENT_DATE);
		verify(defaultCalendar, never()).isHoliday(any());
		verify(defaultCalendar, never()).nextWorkingDay(any());
		verifyTrade(trade, ENTITY, SETTLEMENT_DATE_NEXT_WORKING_DAY, 20, TRADE_TYPE);
	}
	
	@Test
	public void buildTrade_settlementDateWorkingday() {
		//GIVEN
		subject.setCurrency(CURRENCY_AED);
		when(calendar.isHoliday(SETTLEMENT_DATE)).thenReturn(false);
		
		//WHEN
		Trade trade = subject.build();
		
		//THEN
		verify(calendar, never()).nextWorkingDay(any());
		verify(calendar).isHoliday(SETTLEMENT_DATE);
		verify(defaultCalendar, never()).isHoliday(any());
		verify(defaultCalendar, never()).nextWorkingDay(any());
		verifyTrade(trade, ENTITY, SETTLEMENT_DATE, 20, TRADE_TYPE);
	}
	
	@Test
	public void buildTrade_settlementDateHoliday_defaultWorkWeek() {
		//GIVEN
		subject.setCurrency(CURRENCY_DEFAULT_WORK_WEEK);
		when(defaultCalendar.isHoliday(SETTLEMENT_DATE)).thenReturn(true);
		
		//WHEN
		Trade trade = subject.build();
		
		//THEN
		verify(defaultCalendar).nextWorkingDay(any());
		verify(defaultCalendar).isHoliday(SETTLEMENT_DATE);
		verify(calendar, never()).isHoliday(any());
		verify(calendar, never()).nextWorkingDay(any());
		verifyTrade(trade, ENTITY, SETTLEMENT_DATE_NEXT_WORKING_DAY, 20, TRADE_TYPE);
	}
	
	@Test
	public void buildTrade_settlementDateWorkingday_defaultWorkWeek() {
		//GIVEN
		subject.setCurrency(CURRENCY_DEFAULT_WORK_WEEK);
		when(defaultCalendar.isHoliday(SETTLEMENT_DATE)).thenReturn(false);
		
		//WHEN
		Trade trade = subject.build();
		
		//THEN
		verify(defaultCalendar, never()).nextWorkingDay(any());
		verify(defaultCalendar).isHoliday(SETTLEMENT_DATE);
		verify(calendar, never()).isHoliday(any());
		verify(calendar, never()).nextWorkingDay(any());
		verifyTrade(trade, ENTITY, SETTLEMENT_DATE, 20, TRADE_TYPE);
	}
	
	private void verifyTrade(Trade trade, String expectedEntity, LocalDate expectedSettlementDate,
			double expectedTradeAmount, TradeType expectedTradeType) {
		assertThat(trade.getEntity(), equalTo(expectedEntity));
		assertThat(trade.getSettlementDate(), equalTo(expectedSettlementDate));
		assertThat(trade.getTradeAmount(), equalTo(expectedTradeAmount));
		assertThat(trade.getTradeType(), equalTo(expectedTradeType));
	}
	

}
