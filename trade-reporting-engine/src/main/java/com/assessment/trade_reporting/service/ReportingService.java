package com.assessment.trade_reporting.service;

import java.util.Collection;

public interface ReportingService<T> {
	
	void publishReport(Collection<T> data);

}
