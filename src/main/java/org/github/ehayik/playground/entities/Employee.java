package org.github.ehayik.playground.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@Entity
@ToString
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "emp_no")
    private int employeeNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Employee other) &&
                Objects.equals(getEmployeeNumber(), other.getEmployeeNumber());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum Gender {
        M, F
    }
}

