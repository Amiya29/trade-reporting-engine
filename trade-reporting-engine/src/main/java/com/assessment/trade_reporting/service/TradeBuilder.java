package com.assessment.trade_reporting.service;

import java.time.LocalDate;

import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.model.TradeType;

public interface TradeBuilder {
	
	Trade build();
	
	public TradeBuilder setEntity(String entity);
	
	public TradeBuilder setTradeType(TradeType tradeType);
	
	public TradeBuilder setFxRate(float fxRate);
	
	public TradeBuilder setCurrency(String currency);
	
	public TradeBuilder setUnits(int units);
	
	public TradeBuilder setUnitPrice(float unitPrice);
	
	public TradeBuilder setSettlementDate(LocalDate settlementDate);

}
