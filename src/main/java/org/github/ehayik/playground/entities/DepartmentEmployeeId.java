package org.github.ehayik.playground.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * Hands on the best way to map a composite key.
 *     <li>
 *         <ul>The JPA specification says that all entity identifiers should be serializable and implement <code>equals</code> and <code>hashCode</code>.</ul>
 *         <ul>The <code>@EmbeddedId</code> is used to instruct Hibernate that the a given entity uses a compound key.</ul>
 *     </li>
 *
 * @see <a href="https://vladmihalcea.com/the-best-way-to-map-a-composite-primary-key-with-jpa-and-hibernate/">The best way to map a Composite Key with JPA and Hibernate</a>
 */
@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEmployeeId implements Serializable {

    @Column(name = "dept_no")
    private String departmentNumber;

    @Column(name = "emp_no")
    private int employeeNumber;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof DepartmentEmployeeId other)
                && Objects.equals(departmentNumber, other.departmentNumber)
                && employeeNumber == other.employeeNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentNumber, employeeNumber);
    }
}
