package com.nexushr.nexushr.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.nexushr.nexushr.repository.CandidateRepository;
import com.nexushr.nexushr.repository.EmployeeRepository;
import com.nexushr.nexushr.repository.LeaveRepository;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin("*")
public class DashboardController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping("/department-stats")
    public Map<String, Long> getDepartmentStats() {

        Map<String, Long> data =
                new HashMap<>();

        employeeRepository.findAll()
                .forEach(emp -> {

                    String dept =
                            emp.getDepartment();

                    data.put(
                            dept,
                            data.getOrDefault(
                                    dept,
                                    0L
                            ) + 1
                    );
                });

        return data;
    }
    @GetMapping("/leave-stats")
    public Map<String, Long> getLeaveStats() {

        Map<String, Long> data =
                new HashMap<>();

        data.put(
                "Approved",

                leaveRepository.findAll()
                        .stream()
                        .filter(l ->
                                "APPROVED".equals(
                                        l.getStatus()
                                )
                        )
                        .count()
        );

        data.put(
                "Pending",

                leaveRepository.findAll()
                        .stream()
                        .filter(l ->
                                "PENDING".equals(
                                        l.getStatus()
                                )
                        )
                        .count()
        );

        data.put(
                "Rejected",

                leaveRepository.findAll()
                        .stream()
                        .filter(l ->
                                "REJECTED".equals(
                                        l.getStatus()
                                )
                        )
                        .count()
        );

        return data;
    }
    @GetMapping("/candidate-stats")
    public Map<String, Long> getCandidateStats() {

        Map<String, Long> data =
                new HashMap<>();

        candidateRepository.findAll()
                .forEach(candidate -> {

                    String status =
                            candidate.getStatus();

                    data.put(
                            status,

                            data.getOrDefault(
                                    status,
                                    0L
                            ) + 1
                    );
                });

        return data;
    }
    @GetMapping("/stats")
    public Map<String, Object> getStats() {

        Map<String, Object> data =
                new HashMap<>();

        long pendingLeaves =
                leaveRepository.findAll()
                        .stream()
                        .filter(l ->
                                "PENDING".equals(
                                        l.getStatus()
                                )
                        )
                        .count();

        long approvedLeaves =
                leaveRepository.findAll()
                        .stream()
                        .filter(l ->
                                "APPROVED".equals(
                                        l.getStatus()
                                )
                        )
                        .count();

        long rejectedLeaves =
                leaveRepository.findAll()
                        .stream()
                        .filter(l ->
                                "REJECTED".equals(
                                        l.getStatus()
                                )
                        )
                        .count();

        long selectedCandidates =
                candidateRepository.findAll()
                        .stream()
                        .filter(c ->
                                "SELECTED".equals(
                                        c.getStatus()
                                )
                        )
                        .count();

        data.put(
                "totalEmployees",
                employeeRepository.count()
        );

        data.put(
                "pendingLeaves",
                pendingLeaves
        );

        data.put(
                "approvedLeaves",
                approvedLeaves
        );

        data.put(
                "rejectedLeaves",
                rejectedLeaves
        );

        data.put(
                "totalCandidates",
                candidateRepository.count()
        );

        data.put(
                "selectedCandidates",
                selectedCandidates
        );

        return data;
    }
}