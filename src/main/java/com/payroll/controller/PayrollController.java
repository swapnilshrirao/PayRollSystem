package com.payroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.payroll.model.EmployeeEvent;
import com.payroll.model.PayrollReport;
import com.payroll.service.PayrollService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr.trim(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
    }

    @PostMapping("/upload")
    public PayrollReport uploadEmployeeData(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty. Please upload a valid file.");
        }
        List<EmployeeEvent> employeeEvents = parseFile(file);
        return payrollService.processPayrollData(employeeEvents);
    }

    private List<EmployeeEvent> parseFile(MultipartFile file) throws Exception {
        List<EmployeeEvent> events = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            reader.readLine(); // Skip header
            
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length < 9) continue; // Skip invalid rows
                
                EmployeeEvent event = new EmployeeEvent();
                event.setSequenceNo(columns[0].trim());
                event.setEmpId(columns[1].trim());
                event.setEmpFName(columns[2].trim());
                event.setEmpLName(columns[3].trim());
                event.setDesignation(columns[4].trim());
                event.setEvent(columns[5].trim());
                event.setValue(columns[6].trim());
                event.setEventDate(parseDate(columns[7].trim()));
                event.setNotes(columns[8].trim());
                
                events.add(event);
            }
        }
        return events;
    }
}
