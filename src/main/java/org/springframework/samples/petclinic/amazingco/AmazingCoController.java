package org.springframework.samples.petclinic.amazingco;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	private final AmazingEmployeeRepository employeeRepo;

	private AmazingTreeNode<AmazingEmployee> employees = new AmazingTreeNode<>();

	/**
	 * This function builds a tree from a Collection.
	 * @param employeeList The list from which the tree is populated.
	 */
	public void buildTree(List<AmazingEmployee> employeeList) {
		System.out.print("\n=======================================================");
		System.out.println("======================================================");
		System.out.println("== Building tree from list");
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

	public AmazingEmployeeController(AmazingEmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;

		List<AmazingEmployee> employeeList = employees.getAmazingEmployeeList();
		employeeList.addAll(this.employeeRepo.findAll());
		buildTree(employeeList);
		employees.printTree();
	}

	@GetMapping("/employee_directory.html")
	public String showEmployeeList(@RequestParam(defaultValue = "1") int page, Model model) {
		// Here we are returning an object of type 'AmazingEmployee' rather than a
		// collection of
		// AmazingEmployee objects so it is simpler for Object-Xml mapping
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
	public @ResponseBody AmazingTreeNode<AmazingEmployee> showResourcesAmazingEmployeeList() {
		// Here we are returning an object of type 'AmazingEmployees' rather than a
		// collection of
		// AmazingEmployee objects so it is simpler for JSon/Object mapping
		employees.getAmazingEmployeeList().addAll(this.employeeRepo.findAll());
		return employees;
	}

}
