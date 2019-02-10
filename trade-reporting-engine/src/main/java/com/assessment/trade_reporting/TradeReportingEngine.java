package com.assessment.trade_reporting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.assessment.trade_reporting.model.Instruction;
import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.service.InstructionsProvider;
import com.assessment.trade_reporting.service.TradesReportingService;

public class TradeReportingEngine {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TradeReportingEngine.class);
	
	private InstructionsProvider instructionsProvider;
	private Function<Instruction, Trade> instructionToTradeConverter;
	private List<TradesReportingService> tradeReportingServices;
	
	public TradeReportingEngine(InstructionsProvider instructionsProvider,
			Function<Instruction, Trade> instructionToTradeConverter, List<TradesReportingService> reportingServices) {
		this.instructionsProvider = instructionsProvider;
		this.instructionToTradeConverter = instructionToTradeConverter;
		
		//Do not allow list to be modifiable outside class 
		this.tradeReportingServices = new ArrayList<>(reportingServices); 
	}

	public void publishReports() {
		//Get Instructions
		Collection<Instruction> instructions = instructionsProvider.getInstructions();
		LOGGER.info("{} instructions has been fetched.", instructions == null ? 0 : instructions.size());
		
		//Convert Instructions to Trades
		Collection<Trade> trades = convertToTrades(instructions);
		LOGGER.info("Instructions converted to trades successfully.");
		
		//Publish Trade Reports
		tradeReportingServices.stream()
			.forEach(reportingService -> reportingService.publishReport(trades));
		
		LOGGER.info("All reports published successfully.");
	}

	private Collection<Trade> convertToTrades(Collection<Instruction> instructions) {
		
		if (instructions == null) {
			return Collections.emptyList();
		}
		
		return instructions.stream()
				.map(instructionToTradeConverter::apply)
				.collect(Collectors.toList());
	}

}
