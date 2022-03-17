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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

		// LinkedList<AmazingEmployee> employeeList = new LinkedList<>();
		// employeeList.addAll(this.employeeRepo.findAll());
		// return employeeList;
		return employees.toList(employees);
	}

	@GetMapping("/amazingco/find")
	public String findEmployee(Model model) {
		String funcName = "[AmazingCoController::findEmployee()]:: ";
		System.out.println(funcName + "Finding employee . . .");

		model.addAttribute("employee", new AmazingEmployee());

		return "/amazingco/findEmployee.html";
	}

	@PostMapping("/amazingco/find_result")
	public String findEmployeeSubmit(@ModelAttribute AmazingEmployee employee, Model model) {
		String funcName = "[AmazingCoController::findEmployeeSubmit()]:: ";
		System.out.println(funcName + "Moving employee . . .");

		AmazingTreeNode<AmazingEmployee> node = employees.get(employee.getEmployeeName());
		if (node != null) {
			node.printTree();

			employee.setManagerName(node.parentId);
			model.addAttribute("employee", employee);

			System.out.println(funcName + ". . . employee found.");
		}

		return "/amazingco/findEmployeeResult.html";
	}

	@GetMapping("/amazingco/move")
	public String moveEmployee(Model model) {
		String funcName = "[AmazingCoController::moveEmployee()]:: ";

		model.addAttribute("employee", new AmazingEmployee());
		model.addAttribute("toManager", new String());

		return "/amazingco/moveEmployee.html";
	}

	@PostMapping("/amazingco/move_result")
	public String moveEmployeeSubmit(@ModelAttribute AmazingEmployee employee, Model model) {
		String funcName = "[AmazingCoController::moveEmployeeSubmit()]:: ";
		System.out.println(funcName + ". . . .and we're walking, we're walking . . .");

		// Move the employee retrieved by ACTION: @/amazingco/move
		String emp = employee.getEmployeeName();

		// Even though 'getManager()' exists in the 'employee' instance, it is not
		// captured by ACTION: @/amazingco/move. If the preference is to avoid duplication
		// of information, perhaps retrieve 'employee's manager already recorded in the
		// tree. There's got to be a better way to do this -- no time now.
		AmazingTreeNode<AmazingEmployee> node = employees.get(emp);
		if (node != null) {

			String from = node.parentId;

			// Pre-move debug print.
			employees.printTree();

			// See ACTION: @/amazingco/move.
			// We HAVE to be told to whom the employee is being assigned.
			String to = employee.getToManager();

			// Moves are called from the root. A (rather flawed) design decision was to
			// move all the subject employee's children to their grandparent node. This
			// is a recursive solution, searches STARTING from the parent have no
			// visibility ABOVE the parent, thus no root and quite potentially no 'to'
			// node; therefore, the move is executed from the static root instance of
			// 'employees'. Room for improvement here.
			System.out.println(funcName + "Moving " + emp + " from " + from + " to " + to + ".");
			employees.move(emp, from, to);

			// Post-move debug print.
			employees.printTree();
		}

		System.out.println("Re-directing to the employee directory.");
		return "redirect:/amazingco/employee_directory.html";
	}

	@GetMapping("/amazingco/employees")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, AmazingEmployee employee,
			BindingResult result, Model model) {
		return "redirect:/amazingco/employee_directory.html";
	}

}
