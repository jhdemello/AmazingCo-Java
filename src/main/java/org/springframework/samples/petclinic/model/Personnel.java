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

	// [JHD(2022-03-17)]: Once again, out of time to do this correctly. I don't know
	// Thymeleaf enough to pass the 'toManager' data to the "Move Employee" GET and
	// POST methods by any other means at the moment.
	@Column(name = "to_manager")
	private String toManager;

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

	public String getToManager() {
		return this.toManager;
	}

	public void setToManager(String toManager) {
		this.toManager = toManager;
	}

}
