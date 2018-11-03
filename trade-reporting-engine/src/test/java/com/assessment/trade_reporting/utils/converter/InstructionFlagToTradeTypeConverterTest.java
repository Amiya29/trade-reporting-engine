package com.assessment.trade_reporting.utils.converter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.assessment.trade_reporting.model.TradeType;

public class InstructionFlagToTradeTypeConverterTest {
	
	private InstructionFlagToTradeTypeConverter subject = new InstructionFlagToTradeTypeConverter();
	
	@Test
	public void apply_null() {
		assertNull(subject.apply(null));
	}
	
	@Test
	public void apply_Buy() {
		assertThat(subject.apply("Buy"), equalTo(TradeType.BUY));
	}
	
	@Test
	public void apply_BUY() {
		assertThat(subject.apply("BUY"), equalTo(TradeType.BUY));
	}
	
	@Test
	public void apply_Sell() {
		assertThat(subject.apply("Sell"), equalTo(TradeType.SELL));
	}
	
	@Test
	public void apply_SELL() {
		assertThat(subject.apply("SELL"), equalTo(TradeType.SELL));
	}
	
	@Test
	public void apply_b() {
		assertThat(subject.apply("b"), equalTo(TradeType.BUY));
	}
	
	@Test
	public void apply_B() {
		assertThat(subject.apply("B"), equalTo(TradeType.BUY));
	}
	
	@Test
	public void apply_s() {
		assertThat(subject.apply("s"), equalTo(TradeType.SELL));
	}
	
	@Test
	public void apply_S() {
		assertThat(subject.apply("S"), equalTo(TradeType.SELL));
	}

}
