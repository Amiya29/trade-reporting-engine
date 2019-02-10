package com.assessment.trade_reporting.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class WorkWeekCalendarTest {
	
	private WorkWeekCalendar subject;
	
	//Power mock is not worth here to mock LocalDate final class
	private static final LocalDate KNOWN_MONDAY = LocalDate.of(2018, Month.OCTOBER, 1);
	private static final LocalDate KNOWN_TUESDAY = LocalDate.of(2018, Month.OCTOBER, 2);
	private static final LocalDate KNOWN_WEDNESDAY = LocalDate.of(2018, Month.OCTOBER, 3);
	private static final LocalDate KNOWN_THURSDAY = LocalDate.of(2018, Month.OCTOBER, 4);
	private static final LocalDate KNOWN_FRIDAY = LocalDate.of(2018, Month.OCTOBER, 5);
	private static final LocalDate KNOWN_SATURDAY = LocalDate.of(2018, Month.OCTOBER, 6);
	private static final LocalDate KNOWN_SUNDAY = LocalDate.of(2018, Month.OCTOBER, 7);
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void monFriWorkWeek_isHoliday() {
		//WHEN
		subject = new WorkWeekCalendar(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
		
		//THEN
		verifyWorkDays(KNOWN_MONDAY, KNOWN_TUESDAY, KNOWN_WEDNESDAY, KNOWN_THURSDAY, KNOWN_FRIDAY);
		verifyHolidays(KNOWN_SATURDAY, KNOWN_SUNDAY);
	}
	
	@Test
	public void sunThuWorkWeek_isHoliday() {
		//WHEN
		subject = new WorkWeekCalendar(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);
		
		//THEN
		verifyWorkDays(KNOWN_SUNDAY, KNOWN_MONDAY, KNOWN_TUESDAY, KNOWN_WEDNESDAY, KNOWN_THURSDAY);
		verifyHolidays(KNOWN_FRIDAY, KNOWN_SATURDAY);
	}
	
	//Verify it can handle for shorter work week also
	@Test
	public void monWedWorkWeek_isHoliday() {
		//WHEN
		subject = new WorkWeekCalendar(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY);
		
		//THEN
		verifyWorkDays(KNOWN_MONDAY, KNOWN_TUESDAY, KNOWN_WEDNESDAY);
		verifyHolidays(KNOWN_THURSDAY, KNOWN_FRIDAY, KNOWN_SATURDAY, KNOWN_SUNDAY);
	}
	
	//Verify it can handle for longer work week also
	@Test
	public void monSatWorkWeek_isHoliday() {
		//WHEN
		subject = new WorkWeekCalendar(DayOfWeek.MONDAY, DayOfWeek.SATURDAY);
		
		//THEN
		verifyWorkDays(KNOWN_MONDAY, KNOWN_TUESDAY, KNOWN_WEDNESDAY, KNOWN_THURSDAY, KNOWN_FRIDAY, KNOWN_SATURDAY);
		verifyHolidays(KNOWN_SUNDAY);
	}
	
	//Verify it can handle no holiday week
	@Test
	public void monSunWorkWeek_isHoliday() {
		//WHEN
		subject = new WorkWeekCalendar(DayOfWeek.MONDAY, DayOfWeek.SUNDAY);
		
		//THEN
		verifyWorkDays(KNOWN_MONDAY, KNOWN_TUESDAY, KNOWN_WEDNESDAY, KNOWN_THURSDAY, KNOWN_FRIDAY, KNOWN_SATURDAY, KNOWN_SUNDAY);
	}
	
	@Test
	public void sameStartEndDayWorkWeek_isHoliday() {
		//WHEN
		subject = new WorkWeekCalendar(DayOfWeek.MONDAY, DayOfWeek.MONDAY);
		
		//THEN
		verifyWorkDays(KNOWN_MONDAY);
		verifyHolidays(KNOWN_TUESDAY, KNOWN_WEDNESDAY, KNOWN_THURSDAY, KNOWN_FRIDAY, KNOWN_SATURDAY, KNOWN_SUNDAY);
	}
	
	@Test
	public void monFriWorkWeek_nextWorkingDayOfSaturday() {
		//GIVEN
		subject = new WorkWeekCalendar(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
		
		//WHEN
		LocalDate result = subject.nextWorkingDay(KNOWN_SATURDAY);
		
		//THEN
		assertThat(result.getDayOfWeek(), equalTo(DayOfWeek.MONDAY));
		assertThat(result.getDayOfYear(), equalTo(KNOWN_SATURDAY.plusDays(2).getDayOfYear()));
	}
	
	@Test
	public void monFriWorkWeek_nextWorkingDayOfSunday() {
		//GIVEN
		subject = new WorkWeekCalendar(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
		
		//WHEN
		LocalDate result = subject.nextWorkingDay(KNOWN_SUNDAY);
		
		//THEN
		assertThat(result.getDayOfWeek(), equalTo(DayOfWeek.MONDAY));
		assertThat(result.getDayOfYear(), equalTo(KNOWN_SUNDAY.plusDays(1).getDayOfYear()));
	}
	
	@Test
	public void monFriWorkWeek_nextWorkingDayOfThuesday() {
		//GIVEN
		subject = new WorkWeekCalendar(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
		
		//WHEN
		LocalDate result = subject.nextWorkingDay(KNOWN_TUESDAY);
		
		//THEN
		assertThat(result.getDayOfWeek(), equalTo(DayOfWeek.WEDNESDAY));
		assertThat(result.getDayOfYear(), equalTo(KNOWN_WEDNESDAY.getDayOfYear()));
	}
	
	@Test
	public void sunThuWorkWeek_nextWorkingDayOfFriday() {
		//GIVEN
		subject = new WorkWeekCalendar(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);
		
		//WHEN
		LocalDate result = subject.nextWorkingDay(KNOWN_FRIDAY);
		
		//THEN
		assertThat(result.getDayOfWeek(), equalTo(DayOfWeek.SUNDAY));
		assertThat(result.getDayOfYear(), equalTo(KNOWN_FRIDAY.plusDays(2).getDayOfYear()));
	}
	
	@Test
	public void sunThuWorkWeek_nextWorkingDayOfSaturday() {
		//GIVEN
		subject = new WorkWeekCalendar(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);
		
		//WHEN
		LocalDate result = subject.nextWorkingDay(KNOWN_SATURDAY);
		
		//THEN
		assertThat(result.getDayOfWeek(), equalTo(DayOfWeek.SUNDAY));
		assertThat(result.getDayOfYear(), equalTo(KNOWN_SATURDAY.plusDays(1).getDayOfYear()));
	}
	
	private void verifyWorkDays(LocalDate... dates) {
		for (LocalDate dt : dates) {
			assertFalse(subject.isHoliday(dt));
		}
	}

	private void verifyHolidays(LocalDate... dates) {
		for (LocalDate dt : dates) {
			assertTrue(subject.isHoliday(dt));
		}
	}

}
