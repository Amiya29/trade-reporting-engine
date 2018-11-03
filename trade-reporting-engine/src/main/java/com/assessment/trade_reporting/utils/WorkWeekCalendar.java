package com.assessment.trade_reporting.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkWeekCalendar implements TradeCalendar {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkWeekCalendar.class);
	
	private List<DayOfWeek> workingDays = new ArrayList<>();
	
	public WorkWeekCalendar(DayOfWeek startDay, DayOfWeek endDay) {
		
		int counter = 0;
		while(!startDay.plus(counter).equals(endDay)) {
			workingDays.add(startDay.plus(counter));
			counter++;
		}
		
		workingDays.add(endDay);
	}

	@Override
	public boolean isHoliday(LocalDate date) {
		return isHoliday(date, true);
	}
	
	private boolean isHoliday(LocalDate date, boolean log) {
		boolean isHoliday = !workingDays.contains(date.getDayOfWeek());
		
		if(log && isHoliday) {
			LOGGER.debug("{} ({}) is holiday. Working days are: {}", date, date.getDayOfWeek(), workingDays);
		}
		
		return isHoliday;
		
	}

	@Override
	public LocalDate nextWorkingDay(LocalDate date) {
		int counter = 1;
		
		//increment days until it is a work day 
		while(isHoliday(date.plusDays(counter), false)) {
			counter++;
		}
		
		return date.plusDays(counter);
	}
	

}
