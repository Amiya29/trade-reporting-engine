package com.assessment.trade_reporting.service;

public interface ReportingService<T> {
	
	void publishReport(T data);

}
