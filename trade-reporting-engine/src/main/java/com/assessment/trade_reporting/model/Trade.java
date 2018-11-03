package com.assessment.trade_reporting.model;

import java.time.LocalDate;

/**
 * Generic Trade class, (currency agnostic)
 */
public class Trade {
	
	private final String entity;
	private final TradeType tradeType;
	private final double tradeAmount;
	private final LocalDate settlementDate;
	
	public Trade(String entity, TradeType tradeType, double tradeAmount, LocalDate settlementDate) {
		this.entity = entity;
		this.tradeType = tradeType;
		this.tradeAmount = tradeAmount;
		this.settlementDate = settlementDate;
	}
	
	public String getEntity() {
		return entity;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public double getTradeAmount() {
		return tradeAmount;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	
}
