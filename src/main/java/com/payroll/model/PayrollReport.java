package com.payroll.model;
import java.util.List;
import java.util.Map;

public class PayrollReport {
    private int totalEmployees;
    private Map<String, List<EmployeeEvent>> monthWiseJoinsAndExits;
    private Map<String, Double> monthlySalaryReport;

    // Getters and setters for the fields

    public int getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(int totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public Map<String, List<EmployeeEvent>> getMonthWiseJoinsAndExits() {
        return monthWiseJoinsAndExits;
    }

    public void setMonthWiseJoinsAndExits(Map<String, List<EmployeeEvent>> monthWiseJoinsAndExits) {
        this.monthWiseJoinsAndExits = monthWiseJoinsAndExits;
    }

    public Map<String, Double> getMonthlySalaryReport() {
        return monthlySalaryReport;
    }

    public void setMonthlySalaryReport(Map<String, Double> monthlySalaryReport) {
        this.monthlySalaryReport = monthlySalaryReport;
    }
}
