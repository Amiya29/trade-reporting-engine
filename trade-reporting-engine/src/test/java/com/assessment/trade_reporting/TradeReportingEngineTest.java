package com.assessment.trade_reporting;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.assessment.trade_reporting.model.Instruction;
import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.service.InstructionsProvider;
import com.assessment.trade_reporting.service.TradesReportingService;

public class TradeReportingEngineTest {
	
	@Mock
	private InstructionsProvider instructionsProvider;
	
	@Mock
	private Function<Instruction, Trade> instructionToTradeConverter;
	
	@Mock
	private TradesReportingService tradeReportingService1;
	
	@Mock
	private TradesReportingService tradeReportingService2;
	
	@Mock
	private Instruction instruction1;

	@Mock
	private Instruction instruction2;
	
	@Mock 
	private Trade trade1;
	
	@Mock 
	private Trade trade2;
	
	@Captor
	public ArgumentCaptor<Collection<Trade>> tradesCaptor;
	
	private TradeReportingEngine subject;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		
		when(instructionToTradeConverter.apply(instruction1)).thenReturn(trade1);
		when(instructionToTradeConverter.apply(instruction2)).thenReturn(trade2);
		
		subject = new TradeReportingEngine(instructionsProvider, instructionToTradeConverter, 
				Arrays.asList(tradeReportingService1, tradeReportingService2));
	}
	
	@Test
	public void publishReports() {
		//GIVEN
		when(instructionsProvider.getInstructions()).thenReturn(Arrays.asList(instruction1, instruction2));
		
		//WHEN
		subject.publishReports();
		
		//THEN
		verify(instructionsProvider).getInstructions();
		verifyReportingServices(tradeReportingService1);
		verifyReportingServices(tradeReportingService2);
	}
	
	@Test
	public void publishReports_emptyInstructions() {
		//GIVEN
		when(instructionsProvider.getInstructions()).thenReturn(null);
		
		//WHEN
		subject.publishReports();
		
		//THEN
		verify(instructionsProvider).getInstructions();
		verify(tradeReportingService1).publishReport(Collections.emptyList());
		verify(tradeReportingService2).publishReport(Collections.emptyList());
	}
	
	private void verifyReportingServices(TradesReportingService tradeReportingService) {
		verify(tradeReportingService).publishReport(tradesCaptor.capture());
		assertThat(tradesCaptor.getValue(), notNullValue());
		assertThat(tradesCaptor.getValue(), hasSize(2));
		assertThat(tradesCaptor.getValue(), containsInAnyOrder(trade1, trade2));
	}
	
}
