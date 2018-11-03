package com.assessment.trade_reporting.service;

import java.util.Collection;

import com.assessment.trade_reporting.model.Instruction;

public interface InstructionsProvider {
	
	Collection<Instruction> getInstructions();
	
}
