package com.payroll.service;


import org.springframework.stereotype.Service;

import com.payroll.model.EmployeeEvent;
import com.payroll.model.PayrollReport;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PayrollService {

    // Sample method to process employee data (In real scenarios, we would use a database)
    public PayrollReport processPayrollData(List<EmployeeEvent> employeeEvents) {
        PayrollReport payrollReport = new PayrollReport();

        // Calculate total number of employees
        Set<String> uniqueEmpIds = employeeEvents.stream()
                .map(EmployeeEvent::getEmpId)
                .collect(Collectors.toSet());
        payrollReport.setTotalEmployees(uniqueEmpIds.size());

        // Process month-wise join and exit events
        Map<String, List<EmployeeEvent>> monthWiseEvents = employeeEvents.stream()
                .collect(Collectors.groupingBy(event -> event.getEventDate().getMonth() + "-" + event.getEventDate().getYear()));

        // Prepare reports
        payrollReport.setMonthWiseJoinsAndExits(monthWiseEvents);
        
        // Process salary, bonus, reimbursement etc.
        payrollReport.setMonthlySalaryReport(processSalaryData(employeeEvents));

        return payrollReport;
    }

    private Map<String, Double> processSalaryData(List<EmployeeEvent> events) {
        // Filter salary events and calculate the total salary for each month
        return events.stream()
                .filter(event -> "SALARY".equalsIgnoreCase(event.getEvent()))
                .collect(Collectors.groupingBy(
                        event -> event.getEventDate().getMonth() + "-" + event.getEventDate().getYear(),
                        Collectors.summingDouble(event -> Double.parseDouble(event.getValue()))
                ));
    }
}
