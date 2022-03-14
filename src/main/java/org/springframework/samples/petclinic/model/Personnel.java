package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

/**
 * Simple JavaBean domain object representing an person.
 *
 * @author Jason deMello
 */
@MappedSuperclass
public class Personnel extends BaseEntity {

	@Column(name = "employee_name")
	@NotEmpty
	private String employeeName;

	@Column(name = "manager_name")
	@NotEmpty
	private String managerName;

	public String getEmployeeName() {
		return this.employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getManagerName() {
		return this.managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

}
