package org.github.ehayik.playground.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "dept_emp")
public class DepartmentEmployee {

    @EmbeddedId
    private DepartmentEmployeeId id;

    @MapsId("departmentNumber")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "dept_no")
    private Department department;

    @MapsId("employeeNumber")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public DepartmentEmployee(Department department, Employee employee) {
        this.department = department;
        this.employee = employee;
        id = new DepartmentEmployeeId(department.getDepartmentNumber(), employee.getEmployeeNumber());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof DepartmentEmployee other)
                && Objects.equals(department, other.department)
                && Objects.equals(employee, other.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(department, employee);
    }
}
