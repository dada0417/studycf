package studycf.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import studycf.dto.GoodsManagement;
import studycf.dto.Seat;
import studycf.service.GoodsManagementService;
import studycf.service.SeatService;


@Controller
@RequestMapping("/seat")
public class SeatController {
	
	public final SeatService seatService;
	public final GoodsManagementService goodsManagementService;

	
	public SeatController(SeatService seatService, GoodsManagementService goodsManagementService) {
		this.seatService	=	seatService;
		this.goodsManagementService	=	goodsManagementService;

	}
	
	private static final Logger log = LoggerFactory.getLogger(SeatController.class);
	
	//예약된 좌석 확인
	@PostMapping("/seatCheck")
	@ResponseBody
	public List<Seat> seatCheck(){
		List<Seat> seatCk = seatService.seatCheck();
		
		log.info("예약된 좌석 확인 컨트롤 목록 :{}", seatCk);
		return seatCk;
	}
	
	
	
	/*좌석 변경시 */
	@PostMapping("/seatSelection2")
	public String seatSelection2(Seat seat, GoodsManagement goodsManagement) {
		
		seatService.seatSelection(seat);
				
		return "redirect:/seat/seatSelection?goodsManagementCd="+goodsManagement.getGoodsManagementCd();
	}
	/*좌석 이용 */
	@PostMapping("/seatSelection")
	public String seatSelection(Seat seat, GoodsManagement goodsManagement) {
		
		goodsManagementService.modifyGM(goodsManagement);
		seatService.seatSelection(seat);
		
		return "redirect:/";
	}
	
	/*좌석 이용 */
	@GetMapping("/seatSelection")
	public String seatSelection(Model model,
								@RequestParam(name="goodsManagementCd", required=false) String goodsManagementCd
								,HttpSession session, GoodsManagement goodsManagement) {
		String sessionId = (String)session.getAttribute("SID");
		GoodsManagement useById = goodsManagementService.getUseById(sessionId);
		
		
		model.addAttribute("goodsManagementCd", goodsManagementCd);
		model.addAttribute("useById", useById);
		
		return"seat/seatSelection";
	}
	/*
	 * //좌석 목록 조회
	 * 
	 * @GetMapping ("/seatList") public String seatList(Model model) {
	 * 
	 * List<Seat> seatList = seatService.getSeatList();
	 * 
	 * log.info("목록 : {}", seatList);
	 * 
	 * model.addAttribute("title", "좌석 목록"); model.addAttribute("seatList",
	 * seatList);
	 * 
	 * return "seat/seatList"; }
	 */
	
	
	

	
}
