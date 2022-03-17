package org.springframework.samples.petclinic.amazingco;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.samples.petclinic.amazingco.AmazingTreeNode;
import org.springframework.samples.petclinic.amazingco.AmazingEmployee;
import org.springframework.samples.petclinic.amazingco.AmazingEmployeeRepository;

/**
 * @author Jason deMello
 */
@Controller
class AmazingEmployeeController {

	private static final String VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM = "amazingco/createOrUpdateEmployeeForm";

	private final AmazingEmployeeRepository employeeRepo;

	private static AmazingTreeNode<AmazingEmployee> employees = new AmazingTreeNode<>();

	/**
	 * This function builds a tree from a Collection.
	 * @param employeeList The list from which the tree is populated.
	 */
	public AmazingEmployeeController(AmazingEmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;

		ArrayList<AmazingEmployee> employeeList = new ArrayList<>();
		employeeList.addAll(this.employeeRepo.findAll());
		buildTree(employeeList);
		employees.printTree();
	}

	public void buildTree(List<AmazingEmployee> employeeList) {
		System.out.print("\n=======================================================");
		System.out.println("======================================================");
		System.out.println("== Building tree from list of " + employeeList.size() + " employees.");
		System.out.println("== ");
		IntStream.range(0, employeeList.size()).forEachOrdered(i -> {
			System.out.println("== MGR " + employeeList.get(i).getManagerName() + " / EMP "
					+ employeeList.get(i).getEmployeeName());

			String strID = employeeList.get(i).getEmployeeName();
			String strParent = employeeList.get(i).getManagerName();
			if (strID.equalsIgnoreCase(strParent)) {
				employees = new AmazingTreeNode<AmazingEmployee>(strID);
			}
			else {
				AmazingTreeNode<AmazingEmployee> node = employees.get(strParent);
				node.insert(strID);
			}
		});
		System.out.println("==");
	}

	@GetMapping("/amazingco/employee_directory.html")
	public String showEmployeeList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'AmazingEmployee' rather than a
		// collection of AmazingEmployee objects so it is simpler for Object-Xml mapping.
		employees.printTree();

		Page<AmazingEmployee> paginated = findPaginated(page);
		return addPaginationModel(page, paginated, model);

	}

	private String addPaginationModel(int page, Page<AmazingEmployee> paginated, Model model) {
		List<AmazingEmployee> listEmployees = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listEmployees", listEmployees);
		return "amazingco/employeeList";
	}

	private Page<AmazingEmployee> findPaginated(int page) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		return employeeRepo.findAll(pageable);
	}

	@GetMapping({ "/amazingco" })
	public @ResponseBody LinkedList<AmazingTreeNode<AmazingEmployee>> showResourcesAmazingEmployeeList() {
		// Here we are returning an object of type 'AmazingEmployees' rather than a
		// collection of AmazingEmployee objects so it is simpler for JSon/Object mapping
		employees.printTree();

		LinkedList<AmazingEmployee> employeeList = new LinkedList<>();
		employeeList.addAll(this.employeeRepo.findAll());

		// return employeeList;
		return employees.toList(employees);
	}

	@GetMapping("/amazingco/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("employee", new AmazingEmployee());
		return "amazingco/findEmployees";
	}

	@GetMapping("/amazingco/new")
	public String initCreationForm(Map<String, Object> model) {
		AmazingEmployee employee = new AmazingEmployee();
		model.put("employee", employee);
		return VIEWS_EMPLOYEE_CREATE_OR_UPDATE_FORM;
	}

	@GetMapping("/amazingco/employees")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, AmazingEmployee employee,
			BindingResult result, Model model) {
		return "redirect:/amazingco/employee_directory.html";
	}

}
