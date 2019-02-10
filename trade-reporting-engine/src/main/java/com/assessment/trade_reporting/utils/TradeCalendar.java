package com.assessment.trade_reporting.utils;

import java.time.LocalDate;

public interface TradeCalendar {
	
	boolean isHoliday(LocalDate date);
	LocalDate nextWorkingDay(LocalDate date);

}
