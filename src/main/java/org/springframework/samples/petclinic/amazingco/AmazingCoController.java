package org.springframework.samples.petclinic.amazingco;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.samples.petclinic.amazingco.EmployeeRepository;
import org.springframework.samples.petclinic.amazingco.Employee;
import org.springframework.samples.petclinic.amazingco.Employees;

/**
 * @author Jason deMello
 */
@Controller
class AmazingCoController {

	private final EmployeeRepository employees;

	public AmazingCoController(EmployeeRepository employees) {
		this.employees = employees;
	}

	@GetMapping("/employee_directory.html")
	public String showEmployeeList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'Employees' rather than a collection of
		// Employee objects so it is simpler for Object-Xml mapping
		Employees employees = new Employees();
		Page<Employee> paginated = findPaginated(page);
		employees.getEmployeeList().addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);

	}

	private String addPaginationModel(int page, Page<Employee> paginated, Model model) {
		List<Employee> listEmployees = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listEmployees", listEmployees);
		return "amazingco/employeeList";
	}

	private Page<Employee> findPaginated(int page) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return employees.findAll(pageable);
	}

	@GetMapping({ "/amazingco" })
	public @ResponseBody Employees showResourcesEmployeeList() {
		// Here we are returning an object of type 'Employees' rather than a collection of
		// Employee objects so it is simpler for JSon/Object mapping
		Employees employees = new Employees();
		employees.getEmployeeList().addAll(this.employees.findAll());
		return employees;
	}

}
