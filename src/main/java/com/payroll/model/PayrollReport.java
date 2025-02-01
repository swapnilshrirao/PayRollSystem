package com.payroll.model;


import java.util.List;
import java.util.Map;


public class PayrollReport {
	private int totalEmployees;
	//private Map<String, List<EmployeeEvent>> monthWiseJoinsAndExits;
	
	private Map<String, Double> monthlySalaryReport;
	

	private Map<String, Map<String, List<EmployeeEvent>>> monthWiseJoinsAndExits;
	private Map<String, Double> monthlyBonuses;
	private Map<String, Double> monthlyReimbursements;
	private Map<String, Double> monthlyTotalAmount;
	private Map<String, String> employeeNames;
	private Map<String, Double> employeeTotalPaid;
	// Getters and setters for the fields


	public Map<String, Double> getMonthlyBonuses() {
		return monthlyBonuses;
	}

	public void setMonthlyBonuses(Map<String, Double> monthlyBonuses) {
		this.monthlyBonuses = monthlyBonuses;
	}

	public Map<String, Double> getMonthlyReimbursements() {
		return monthlyReimbursements;
	}

	public void setMonthlyReimbursements(Map<String, Double> monthlyReimbursements) {
		this.monthlyReimbursements = monthlyReimbursements;
	}

	public Map<String, Double> getMonthlyTotalAmount() {
		return monthlyTotalAmount;
	}

	public void setMonthlyTotalAmount(Map<String, Double> monthlyTotalAmount) {
		this.monthlyTotalAmount = monthlyTotalAmount;
	}

	public Map<String, String> getEmployeeNames() {
		return employeeNames;
	}

	public void setEmployeeNames(Map<String, String> employeeNames) {
		this.employeeNames = employeeNames;
	}

	public Map<String, Double> getEmployeeTotalPaid() {
		return employeeTotalPaid;
	}

	public void setEmployeeTotalPaid(Map<String, Double> employeeTotalPaid) {
		this.employeeTotalPaid = employeeTotalPaid;
	}

	public int getTotalEmployees() {
		return totalEmployees;
	}

	public void setTotalEmployees(int totalEmployees) {
		this.totalEmployees = totalEmployees;
	}

	public Map<String, Map<String, List<EmployeeEvent>>> getMonthWiseJoinsAndExits() {
        return monthWiseJoinsAndExits;
    }

    public void setMonthWiseJoinsAndExits(Map<String, Map<String, List<EmployeeEvent>>> monthWiseJoinsAndExits) {
        this.monthWiseJoinsAndExits = monthWiseJoinsAndExits;
    }

	public Map<String, Double> getMonthlySalaryReport() {
		return monthlySalaryReport;
	}

	public void setMonthlySalaryReport(Map<String, Double> monthlySalaryReport) {
		this.monthlySalaryReport = monthlySalaryReport;
	}
}
