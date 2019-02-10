package com.assessment.trade_reporting.utils.converter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.assessment.trade_reporting.model.TradeType;

public class TradeTypeToSettlementTypeConverterTest {
	
	private TradeTypeToSettlementTypeConverter subject = new TradeTypeToSettlementTypeConverter();
	
	@Test
	public void convert_null() {
		assertNull(subject.apply(null));
	}
	
	@Test
	public void convert_tradeTypeBuy() {
		assertThat(subject.apply(TradeType.BUY), equalTo("outgoing"));
	}

	@Test
	public void convert_tradeTypeSell() {
		assertThat(subject.apply(TradeType.SELL), equalTo("incoming"));
	}
}
