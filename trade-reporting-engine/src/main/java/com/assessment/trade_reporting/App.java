package com.assessment.trade_reporting;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.assessment.trade_reporting.model.Trade;
import com.assessment.trade_reporting.service.InstructionsProvider;
import com.assessment.trade_reporting.service.ReportingService;
import com.assessment.trade_reporting.service.TradeBuilder;
import com.assessment.trade_reporting.service.impl.DailyTradeSettlementAmountBasedEntityRankingReportingServiceImpl;
import com.assessment.trade_reporting.service.impl.DailyTradeSettlementAmountReportingServiceImpl;
import com.assessment.trade_reporting.service.impl.DummyInstructionsProvider;
import com.assessment.trade_reporting.service.impl.TradeBuilderImpl;
import com.assessment.trade_reporting.utils.TradeCalendar;
import com.assessment.trade_reporting.utils.WorkWeekCalendar;
import com.assessment.trade_reporting.utils.converter.InstructionToTradeConverter;

public class App {
		
    public static void main( String[] args ) {
    	InstructionsProvider instructionsProvider = new DummyInstructionsProvider();
    	
    	TradeCalendar sunThuWorkWeekCalendar = new WorkWeekCalendar(SUNDAY, THURSDAY);
    	TradeCalendar tueSatWorkWeekCalendar = new WorkWeekCalendar(TUESDAY, SATURDAY);
    	
    	Map<String, TradeCalendar> tradeCalendars = new HashMap<>();
    	tradeCalendars.put("AED", sunThuWorkWeekCalendar);
    	tradeCalendars.put("SAD", sunThuWorkWeekCalendar);
    	tradeCalendars.put("CZK", tueSatWorkWeekCalendar);
    	
    	TradeBuilder tradeBuilder = new TradeBuilderImpl(tradeCalendars);
    	InstructionToTradeConverter converter = new InstructionToTradeConverter(tradeBuilder);
    	
    	List<ReportingService<Trade>> reportingServices = new ArrayList<>();
    	reportingServices.add(new DailyTradeSettlementAmountReportingServiceImpl());
    	reportingServices.add(new DailyTradeSettlementAmountBasedEntityRankingReportingServiceImpl());
    	    	
        TradeReportingEngine reportingEngine = new TradeReportingEngine(instructionsProvider, converter, reportingServices);
        
        reportingEngine.publishReports();
    }
}
