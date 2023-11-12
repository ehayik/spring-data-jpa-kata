package org.github.ehayik.playground.entities;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE;

/**
 * Hands on good practices to handle <code>@ManyToMany</code> relationships.
 *
 * <ul>
 *     <li>The entity has a unique business key which is marked with the Hibernate-specific <code>@NaturalId</code> annotation. When that’s the case, the unique business key is the best candidate for equality checks</li>
 *     <li>The REMOVE entity state transition doesn't make any sense for a <code>@ManyToMany</code> JPA association since it could trigger a chain deletion that would ultimately wipe both sides of the association.</li>
 *     <li>When using the <code>@ManyToMany</code> annotation, always use a <code>java.util.Set</code> and avoid the <code>java.util.List</code>.</li>
 *     <li>Add/remove utility methods are mandatory if you use bidirectional associations so that you can make sure that both sides of the association are in sync.</li>
 * </ul>
 *
 * @see <a href="https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/">Best way to map the JPA and Hibernate ManyToMany relationship</a>
 * @see <a href="https://vladmihalcea.com/the-best-way-to-map-a-naturalid-business-key-with-jpa-and-hibernate/">The best way to map a @NaturalId business key with JPA and Hibernate</a>
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
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "dept_emp",
            joinColumns = @JoinColumn(name = "dept_no"),
            inverseJoinColumns = @JoinColumn(name = "emp_no")
    )
    private Set<Employee> employees = new HashSet<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.getDepartments().add(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.getDepartments().remove(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Department other) &&
                Objects.equals(getName(), other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

}

