package studycf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import studycf.service.SeatService;

@Controller
public class MainController {
	
	public final SeatService seatService;
	
	public MainController(SeatService seatService) {
		this.seatService = seatService;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		int seatCount = seatService.getSeatCount();
		
		model.addAttribute("seatCount", seatCount);
		return "index";
	}
}
