package com.payroll.service;

import org.springframework.stereotype.Service;

import com.payroll.model.EmployeeEvent;
import com.payroll.model.PayrollReport;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PayrollService {

	/*
	 * // Sample method to process employee data (In real scenarios, we would use a
	 * database) public PayrollReport processPayrollData(List<EmployeeEvent>
	 * employeeEvents) { PayrollReport payrollReport = new PayrollReport();
	 * 
	 * // Calculate total number of employees Set<String> uniqueEmpIds =
	 * employeeEvents.stream() .map(EmployeeEvent::getEmpId)
	 * .collect(Collectors.toSet());
	 * payrollReport.setTotalEmployees(uniqueEmpIds.size());
	 * 
	 * // Process month-wise join and exit events Map<String, List<EmployeeEvent>>
	 * monthWiseEvents = employeeEvents.stream()
	 * .collect(Collectors.groupingBy(event -> event.getEventDate().getMonth() + "-"
	 * + event.getEventDate().getYear()));
	 * 
	 * // Prepare reports payrollReport.setMonthWiseJoinsAndExits(monthWiseEvents);
	 * 
	 * // Process salary, bonus, reimbursement etc.
	 * payrollReport.setMonthlySalaryReport(processSalaryData(employeeEvents));
	 * 
	 * return payrollReport; }
	 * 
	 * private Map<String, Double> processSalaryData(List<EmployeeEvent> events) {
	 * // Filter salary events and calculate the total salary for each month return
	 * events.stream() .filter(event -> "SALARY".equalsIgnoreCase(event.getEvent()))
	 * .collect(Collectors.groupingBy( event -> event.getEventDate().getMonth() +
	 * "-" + event.getEventDate().getYear(), Collectors.summingDouble(event ->
	 * Double.parseDouble(event.getValue())) )); } }
	 */

	public PayrollReport processPayrollData(List<EmployeeEvent> employeeEvents) {
		PayrollReport payrollReport = new PayrollReport();

		// Track unique employees
		Set<String> uniqueEmpIds = employeeEvents.stream().map(EmployeeEvent::getEmpId).collect(Collectors.toSet());
		payrollReport.setTotalEmployees(uniqueEmpIds.size());

		// Track month-wise joins and exits
		 Map<String, Map<String, List<EmployeeEvent>>> monthWiseJoinsExits = new TreeMap<>();
		
		//Map<String, Map<String, List<String>>> monthWiseJoinsExits = new TreeMap<>();

		// Track financial details
		Map<String, Double> monthlySalaries = new TreeMap<>();
		Map<String, Double> monthlyBonuses = new TreeMap<>();
		Map<String, Double> monthlyReimbursements = new TreeMap<>();
		Map<String, Double> monthlyTotalAmount = new TreeMap<>();

		// Employee-wise financial details
		Map<String, String> employeeNames = new HashMap<>();
		Map<String, Double> employeeTotalPaid = new HashMap<>();

		for (EmployeeEvent event : employeeEvents) {
			String monthYear = event.getEventDate().getMonthValue() + "-" + event.getEventDate().getYear();
			String empFullName = event.getEmpFName() + " " + event.getEmpLName();

			if (!monthWiseJoinsExits.containsKey(monthYear)) {
				monthWiseJoinsExits.put(monthYear, new HashMap<>());
				monthWiseJoinsExits.get(monthYear).put("Employees Joined", new ArrayList<>());
				monthWiseJoinsExits.get(monthYear).put("Employees Exited", new ArrayList<>());
			}

			if ("ONBOARD".equalsIgnoreCase(event.getEvent())) {
				monthWiseJoinsExits.get(monthYear).get("Employees Joined").add(event);
			} else if ("EXIT".equalsIgnoreCase(event.getEvent())) {
				monthWiseJoinsExits.get(monthYear).get("Employees Exited").add(event);
			} else {
				double amount = Double.parseDouble(event.getValue());
				employeeNames.put(event.getEmpId(), empFullName);
				employeeTotalPaid.put(event.getEmpId(), employeeTotalPaid.getOrDefault(event.getEmpId(), 0.0) + amount);

				if ("SALARY".equalsIgnoreCase(event.getEvent())) {
					monthlySalaries.put(monthYear, monthlySalaries.getOrDefault(monthYear, 0.0) + amount);
				} else if ("BONUS".equalsIgnoreCase(event.getEvent())) {
					monthlyBonuses.put(monthYear, monthlyBonuses.getOrDefault(monthYear, 0.0) + amount);
				} else if ("REIMBURSEMENT".equalsIgnoreCase(event.getEvent())) {
					monthlyReimbursements.put(monthYear, monthlyReimbursements.getOrDefault(monthYear, 0.0) + amount);
				}
			}
		}

		for (String monthYear : monthlySalaries.keySet()) {
			double totalAmount = monthlySalaries.getOrDefault(monthYear, 0.0)
					+ monthlyBonuses.getOrDefault(monthYear, 0.0) + monthlyReimbursements.getOrDefault(monthYear, 0.0);
			monthlyTotalAmount.put(monthYear, totalAmount);
		}

		payrollReport.setMonthWiseJoinsAndExits(monthWiseJoinsExits);
		payrollReport.setMonthlySalaryReport(monthlySalaries);
		payrollReport.setMonthlyBonuses(monthlyBonuses);
		payrollReport.setMonthlyReimbursements(monthlyReimbursements);
		payrollReport.setMonthlyTotalAmount(monthlyTotalAmount);
		payrollReport.setEmployeeTotalPaid(employeeTotalPaid);
		payrollReport.setEmployeeNames(employeeNames);

		return payrollReport;
	}
}