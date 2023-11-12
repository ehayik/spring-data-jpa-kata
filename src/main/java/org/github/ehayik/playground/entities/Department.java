package org.github.ehayik.playground.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

/**
 * Hands on the best way to many-to-many association with extra columns.
 *
 * <ul>
 *     <li>We need is to map the composite Primary Key which belongs to the intermediary join table.</li>
 *     <li>We need to map the join table using a dedicated entity</li>
 *     <li>When mapping the intermediary join table, it’s better to map only one side as a bidirectional @OneToMany association since otherwise, a second SELECT statement will be issued while removing the intermediary join entity.</li>
 * </ul>
 *
 * @see <a href="https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/">The best way to map a JPA and Hibernate many-to-many association with extra columns</a>
 */
@Getter
@Setter
@Entity
@ToString
@NaturalIdCache
@Table(name = "departments")
@Cache(usage = READ_WRITE)
public class Department {

    @Id
    @Column(name = "dept_no")
    private String departmentNumber;

    @NaturalId
    @Column(name = "dept_name")
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "department", cascade = ALL, orphanRemoval = true)
    private List<DepartmentEmployee> employees = new LinkedList<>();

    public void addEmployee(Employee employee) {
        var deptEmployee = new DepartmentEmployee(this, employee);
        employees.add(deptEmployee);
    }

    public void removeEmployee(Employee employee) {
        employees.removeIf(
                x -> x.getDepartment().equals(this) && x.getEmployee().equals(employee));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Department other) && Objects.equals(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
