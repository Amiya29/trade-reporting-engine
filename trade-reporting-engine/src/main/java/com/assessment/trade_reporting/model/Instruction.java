package com.assessment.trade_reporting.model;

import java.time.LocalDate;

public class Instruction {
	
	private final String entity;
	private final String instructionFlag;
	private final float fxRate;
	private final String currency;
	private final LocalDate instructionDate;
	private final LocalDate settlementDate;
	private final int units;
	private final float unitPrice;
	
	public Instruction(String entity, String instructionFlag, float fxRate, String currency,
			LocalDate instructionDate, LocalDate settlementDate, int units, float unitPrice) {
		this.entity = entity;
		this.instructionFlag = instructionFlag;
		this.fxRate = fxRate;
		this.currency = currency;
		this.instructionDate = instructionDate;
		this.settlementDate = settlementDate;
		this.units = units;
		this.unitPrice = unitPrice;
	}

	public String getEntity() {
		return entity;
	}

	public String getInstructionFlag() {
		return instructionFlag;
	}

	public float getFxRate() {
		return fxRate;
	}

	public String getCurrency() {
		return currency;
	}

	public LocalDate getInstructionDate() {
		return instructionDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public int getUnits() {
		return units;
	}

	public float getUnitPrice() {
		return unitPrice;
	}
	
}
