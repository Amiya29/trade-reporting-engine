package com.assessment.trade_reporting.utils.converter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.assessment.trade_reporting.model.Instruction;
import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.model.TradeType;
import com.assessment.trade_reporting.service.TradeBuilder;
import com.assessment.trade_reporting.utils.AnswerWithSelf;

public class InstructionToTradeConverterTest {
	
	@Mock
	private Instruction instruction;
	
	@Mock
	private Trade trade;
	
	@Mock
	private Function<String, TradeType> instructionFlagToTradeTypeConverter;
	
	private TradeBuilder tradeBuilder;
	private InstructionToTradeConverter subject;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		tradeBuilder = Mockito.mock(TradeBuilder.class, new AnswerWithSelf(TradeBuilder.class));
		
		subject = new InstructionToTradeConverter(tradeBuilder);
		subject.setInstructionFlagToTradeTypeConverter(instructionFlagToTradeTypeConverter);
	}
	
	@Test
	public void apply() {
		
		final float fxRate = 1.5f;
		final LocalDate instructionDate = LocalDate.ofEpochDay(1234566);
		final LocalDate settlementDate = LocalDate.ofEpochDay(56893212);
		final float unitPrice = 200.5f;
		final int units = 12;
		final TradeType knownTradeType = TradeType.BUY;
		
		when(instruction.getCurrency()).thenReturn("currency");
		when(instruction.getEntity()).thenReturn("entity");
		when(instruction.getFxRate()).thenReturn(fxRate);
		when(instruction.getInstructionDate()).thenReturn(instructionDate);
		when(instruction.getSettlementDate()).thenReturn(settlementDate);
		when(instruction.getInstructionFlag()).thenReturn("flag");
		when(instruction.getUnitPrice()).thenReturn(unitPrice);
		when(instruction.getUnits()).thenReturn(units);
		
		when(instructionFlagToTradeTypeConverter.apply(eq("flag"))).thenReturn(knownTradeType);
		when(tradeBuilder.build()).thenReturn(trade);
		
		//WHEN
		Trade result = subject.apply(instruction);
		
		//THEN
		assertThat(result, equalTo(trade));
		
		verify(tradeBuilder).setCurrency("currency");
		verify(tradeBuilder).setEntity("entity");
		verify(tradeBuilder).setFxRate(fxRate);
		verify(tradeBuilder).setSettlementDate(settlementDate);
		verify(tradeBuilder).setTradeType(knownTradeType);
		verify(tradeBuilder).setUnitPrice(unitPrice);
		verify(tradeBuilder).setUnits(units);
		
		verify(instructionFlagToTradeTypeConverter).apply("flag");
	}
	

}
