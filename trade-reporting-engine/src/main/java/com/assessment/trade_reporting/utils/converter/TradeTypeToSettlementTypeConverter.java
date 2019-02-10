package com.assessment.trade_reporting.utils.converter;

import java.util.function.Function;

import com.assessment.trade_reporting.model.TradeType;

public class TradeTypeToSettlementTypeConverter implements Function<TradeType, String> {

	@Override
	public String apply(TradeType tradeType) {
		
		if (tradeType == null) {
			return null;
		}
		
		return TradeType.BUY.equals(tradeType) ? "outgoing" : "incoming";
	}

}
