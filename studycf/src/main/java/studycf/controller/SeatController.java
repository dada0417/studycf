package studycf.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import studycf.dto.Seat;
import studycf.service.SeatService;


@Controller
@RequestMapping("/seat")
public class SeatController {
	
	public final SeatService seatService;
	
	
	public SeatController(SeatService seatService) {
		this.seatService	=	seatService;

	}
	
	private static final Logger log = LoggerFactory.getLogger(SeatController.class);
	
	


	//이용권 목록 조회
	@GetMapping ("/seatList")
	public String seatList(Model model) {
		
		List<Seat> seatList = seatService.getSeatList();
		
		log.info("목록 : {}", seatList);
		
		model.addAttribute("title", "좌석 목록");
		model.addAttribute("seatList", seatList);
		
		return "seat/seatList";
	}
	
	
	

	
}
