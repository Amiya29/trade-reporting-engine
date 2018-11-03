package com.assessment.trade_reporting.service.impl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.assessment.trade_reporting.model.Instruction;
import com.assessment.trade_reporting.service.InstructionsProvider;

public class DummyInstructionsProvider implements InstructionsProvider {

	@Override
	public Collection<Instruction> getInstructions() {
		
		return Stream.of(
				new  Instruction("foo", "B", 0.50f, "SGP", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 2), 200, 100.25f),
				new  Instruction("bar", "S", 0.22f, "AED", LocalDate.of(2018, 1, 5), LocalDate.of(2018, 1, 7), 450, 150.50f),
				new  Instruction("joh", "B", 0.30f, "SAR", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 2), 600, 100.25f),
				new  Instruction("doe", "S", 0.52f, "SGP", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 7), 200, 100.25f),
				new  Instruction("foo", "B", 1.03f, "GBP", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 2), 200, 100.25f),
				new  Instruction("foo", "S", 0.12f, "INR", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 8), 300, 100.25f),
				new  Instruction("bar", "S", 0.51f, "SGP", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 8), 200, 100.25f),
				new  Instruction("foo", "B", 0.45f, "SGP", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 9), 200, 100.25f),
				new  Instruction("joh", "S", 0.45f, "AED", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 6), 400, 100.25f),
				new  Instruction("foo", "B", 0.12f, "INR", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 9), 200, 100.25f),
				new  Instruction("bar", "S", 0.35f, "SGP", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 4), 200, 100.25f),
				new  Instruction("bar", "B", 0.55f, "CZK", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 1), 100, 100.25f),
				new  Instruction("foo", "B", 0.75f, "SAR", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 5), 200, 100.25f),
				new  Instruction("foo", "B", 0.15f, "GBP", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 4), 200, 100.25f),
				new  Instruction("foo", "B", 0.58f, "HUF", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 2), 400, 100.25f),
				new  Instruction("foo", "B", 0.52f, "HKZ", LocalDate.of(2018, 1, 1), LocalDate.of(2018, 1, 2), 700, 100.25f)
				).collect(Collectors.toList());
	}

}
