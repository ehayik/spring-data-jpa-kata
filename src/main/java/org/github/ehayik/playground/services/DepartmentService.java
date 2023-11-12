package org.github.ehayik.playground.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.ehayik.playground.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public void logDepartmentEmployees(String departmentName) {
        var department =
                departmentRepository.findByNameWithEmployees(departmentName).orElseThrow();
        log.info("Found Department: {}", department);

        log.info("Fetching {} department employees", department.getName());
        department.getEmployees()
                .forEach(x -> log.info("Employee: {}, from {} to {}.", x.getEmployee().getFullName(), x.getFromDate(), x.getToDate()));
    }
}
