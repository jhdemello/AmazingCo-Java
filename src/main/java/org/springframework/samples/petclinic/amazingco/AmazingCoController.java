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

	@GetMapping({ "/amazingco" })
	public String welcome() {
		return "redirect:/amazingco/employee_directory.html";
	}

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

		// See ACTION: @/amazingco/move.

		// Move the employee retrieved by ACTION: @/amazingco/move
		String emp = employee.getEmployeeName();

		AmazingTreeNode<AmazingEmployee> node = employees.get(emp);
		if (node != null) {

			// Even though 'getManager()' exists in the 'employee' instance, it is not
			// captured by ACTION: @/amazingco/move. If the preference is to avoid
			// duplication of information, perhaps retrieve 'employee's manager already
			// recorded in the tree. There's got to be a better way to do this -- no
			// time now.
			String from = node.parentId;

			// We HAVE to be told to whom the employee is being assigned, this cannot
			// be derived from current data sources.
			String to = employee.getToManager();

			// Remove 'employee' from the database before re-assigning 'employee' to a
			// new manager.

			// Delete the employee from the database by ID.
			//
			// [JHD(2022-03-17)]: Late in the game and jumping thru hoops. Time to
			// [JHD(2022-03-17)]: deliver . . . with a bug. This employee will not be
			// [JHD(2022-03-17)]: removed from the database. The AmazingEmployee data
			// [JHD(2022-03-17)]: should not have been duplicated in the tree. The
			// [JHD(2022-03-17)]: AmazingEmployee's base identity might have served
			// [JHD(2022-03-17)]: well here.
			// [JHD(2022-03-17)]:
			// [JHD(2022-03-17)]: employeeRepo.deleteByIdentity(employee.id);

			// The @/amazing/move action has already gathered the employee and new manager
			// (or "TO" manager) information, so update the AmazingEmployee manager to
			// the new manager and update the database with 'employee'.
			employee.setManagerName(to);

			// Now that the employee has been assigned a new manager, update the
			// database.
			//
			// employeeRepo.save(employee);
			//
			// or not.

			// Moves are called from the root. A (rather flawed) design decision was to
			// move all the subject employee's children to their grandparent node. This
			// is a recursive solution, searches STARTING from the parent have no
			// visibility ABOVE the parent, thus no root and quite potentially no 'to'
			// node; therefore, the move is executed from the static root instance of
			// 'employees'. Room for improvement here.
			System.out.println(funcName + "Moving " + emp + " from " + from + " to " + to + ".");

			// Pre-move debug print.
			System.out.println(funcName + "== PRE-MOVE ============================");
			employees.printTree();

			// It's not designed to do this at the moment, but there should probably
			// be a check here for success or failure . . .
			employees.move(emp, from, to);

			// Post-move debug print.
			System.out.println(funcName + "== POST-MOVE ===========================");
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
