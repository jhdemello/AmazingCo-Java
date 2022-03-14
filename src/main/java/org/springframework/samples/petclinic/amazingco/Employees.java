package org.springframework.samples.petclinic.amazingco;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple domain object representing a list of employees. Mostly here to be used for the
 * 'employees' {@link org.springframework.web.servlet.view.xml.MarshallingView}.
 *
 * @author Jason deMello
 */
@XmlRootElement
public class Employees {

	private List<Employee> employees;

	@XmlElement
	public List<Employee> getEmployeeList() {
		if (employees == null) {
			employees = new ArrayList<>();
		}
		return employees;
	}

}
