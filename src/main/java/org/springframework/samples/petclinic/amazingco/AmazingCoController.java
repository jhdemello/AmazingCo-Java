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

/**
 * @author Jason deMello
 */
@Controller
public class AmazingCoController {

	@GetMapping({ "/amazingco" })
	public String amazingco(@RequestParam(name = "name", required = false, defaultValue = "Mario") String name,
			Model model) {
		model.addAttribute("name", name);
		return "amazingco";
	}

}
