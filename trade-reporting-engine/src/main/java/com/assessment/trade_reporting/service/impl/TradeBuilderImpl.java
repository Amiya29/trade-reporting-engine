package com.assessment.trade_reporting.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.model.TradeType;
import com.assessment.trade_reporting.service.TradeBuilder;
import com.assessment.trade_reporting.utils.TradeCalendar;
import com.assessment.trade_reporting.utils.WorkWeekCalendar;

public class TradeBuilderImpl implements TradeBuilder {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeBuilderImpl.class); 
	
	private String entity;
	private TradeType tradeType;
	private float fxRate;
	private String currency;
	private int units;
	private float unitPrice;
	private LocalDate settlementDate;
	
	private final Map<String, TradeCalendar> tradeCalendars = new HashMap<>();
	private TradeCalendar defaultCalendar = new WorkWeekCalendar(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
	
	/**
	 * It uses default work week from Monday to Friday
	 * <br> If you want to set a different work week for some regions, use {@link #TradeBuilderImpl(Map)}
	 */
	public TradeBuilderImpl() {	}
	
	/**
	 * Default calendar gets used, if nothing specified in given map
	 * <br> Default calendar is a work week from Monday to Friday  
	 * 
	 * @param tradeCalendars Calendars for different currency region
	 */
	public TradeBuilderImpl(Map<String, TradeCalendar> tradeCalendars) {
		this.tradeCalendars.putAll(tradeCalendars);
	}
	
	@Override
	public Trade build() {
		double tradeAmount = units * unitPrice * fxRate;
		
		TradeCalendar calendar = getTradeCalendar(currency);
		
		if (calendar.isHoliday(settlementDate)) {
			
			LocalDate nextWorkingDay = calendar.nextWorkingDay(settlementDate);
			LOGGER.debug("{} is holiday for {}. Settlement will be done at next working day ({})", settlementDate, currency, nextWorkingDay);
			settlementDate = nextWorkingDay;
		}
				
		return new Trade(entity, tradeType, tradeAmount, settlementDate);
	}
	
	private TradeCalendar getTradeCalendar(String currency) {
		return tradeCalendars.getOrDefault(currency, defaultCalendar);
	}
	
	@Override
	public TradeBuilderImpl setEntity(String entity) {
		this.entity = entity;
		return this;
	}
	
	@Override
	public TradeBuilderImpl setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
		return this;
	}
	
	@Override
	public TradeBuilderImpl setFxRate(float fxRate) {
		this.fxRate = fxRate;
		return this;
	}
	
	@Override
	public TradeBuilderImpl setCurrency(String currency) {
		this.currency = currency;
		return this;
	}
	
	@Override
	public TradeBuilderImpl setUnits(int units) {
		this.units = units;
		return this;
	}
	
	@Override
	public TradeBuilderImpl setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
		return this;
	}
	
	@Override
	public TradeBuilderImpl setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
		return this;
	}
	
	// Package private (for testing purpose only)
	void setDefaultCalendar(TradeCalendar defaultCalendar) {
		this.defaultCalendar = defaultCalendar;
	}

}
