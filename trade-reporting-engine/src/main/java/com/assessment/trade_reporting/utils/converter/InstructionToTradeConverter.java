package com.assessment.trade_reporting.utils.converter;

import java.util.function.Function;

import com.assessment.trade_reporting.model.Instruction;
import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.model.TradeType;
import com.assessment.trade_reporting.service.TradeBuilder;

public class InstructionToTradeConverter implements Function<Instruction, Trade> {
	
	private TradeBuilder tradeBuilder;
	private Function<String, TradeType> instructionFlagToTradeTypeConverter = new InstructionFlagToTradeTypeConverter();

	public InstructionToTradeConverter(TradeBuilder tradeBuilder) {
		this.tradeBuilder = tradeBuilder;
	}
	
	@Override
	public Trade apply(Instruction instruction) {
		return tradeBuilder.setEntity(instruction.getEntity())
				.setTradeType(instructionFlagToTradeTypeConverter.apply(instruction.getInstructionFlag()))
				.setFxRate(instruction.getFxRate())
				.setCurrency(instruction.getCurrency())
				.setSettlementDate(instruction.getSettlementDate())
				.setUnits(instruction.getUnits())
				.setUnitPrice(instruction.getUnitPrice())
				.build();
	}
	
	public void setInstructionFlagToTradeTypeConverter(
			Function<String, TradeType> instructionFlagToTradeTypeConverter) {
		this.instructionFlagToTradeTypeConverter = instructionFlagToTradeTypeConverter;
	}

}
