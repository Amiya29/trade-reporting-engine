package com.assessment.trade_reporting.utils.converter;

import java.util.function.Function;

import com.assessment.trade_reporting.model.TradeType;

public class InstructionFlagToTradeTypeConverter implements Function<String, TradeType>{

	@Override
	public TradeType apply(String instructionFlag) {
		if (instructionFlag == null) {
			return null;
		}
		
		for (TradeType type : TradeType.values()) {
			if (type.name().equalsIgnoreCase(instructionFlag)) {
				return type;
			}
		}
		
		switch (instructionFlag.toUpperCase()) {
		case "B":
			return TradeType.BUY;
		
		case "S":
			return TradeType.SELL;

		default:
			return null;
		}
	}

}
