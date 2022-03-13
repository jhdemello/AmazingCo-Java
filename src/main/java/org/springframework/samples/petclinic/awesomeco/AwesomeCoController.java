package org.springframework.samples.petclinic.awesomeco;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jason deMello
 */
@Controller
public class AwesomeCoController {

	@GetMapping({ "/awesomeco" })
	public String awesomeco(@RequestParam(name = "name", required = false, defaultValue = "deMello") String name,
			Model model) {
		model.addAttribute("name", name);
		return "awesomeco";
	}

}
