package studycf.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import studycf.config.auth.PrincipalDetails;
import studycf.dto.GoodsManagement;
import studycf.dto.Order;
import studycf.dto.Seat;
import studycf.service.GoodsManagementService;
import studycf.service.OrderService;
import studycf.service.SeatService;


@Controller
@RequestMapping("/seat")
public class SeatController {
	
	public final SeatService seatService;
	public final GoodsManagementService goodsManagementService;
	public final OrderService orderService;

	
	public SeatController(SeatService seatService, GoodsManagementService goodsManagementService, OrderService orderService) {
		this.seatService	=	seatService;
		this.goodsManagementService	=	goodsManagementService;
		this.orderService	=	orderService;

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
		//좌석 상태 변경
		seatService.seatSelection(seat);
				
		return "redirect:/seat/seatSelection?goodsManagementCd="+goodsManagement.getGoodsManagementCd();
	}
	/*좌석 이용 */
	@PostMapping("/seatSelection")
	public String seatSelection(Seat seat, GoodsManagement goodsManagement, Order order) {
		//좌석 선택
		if(goodsManagement.getGoodsManagementCd() == null) {
			//좌석 처음 선택시
			goodsManagementService.addGoodsManagement(goodsManagement);
			seatService.seatSelection(seat);
		}else if(goodsManagement.getLeaveTime() == null){
			//좌석 변경 선택 시 좌석 재 선택
			goodsManagementService.modifyGM(goodsManagement);
			seatService.seatSelection(seat);
		}else {
			//좌석 이용 종료 시
			goodsManagementService.modifyGM(goodsManagement);
			orderService.modifyOrder(order);
			seatService.seatSelection(seat);
		}
		
		return "redirect:/";
	}
	
	/*좌석 이용 */
	@GetMapping("/seatSelection")
	public String seatSelection(Model model,
								@RequestParam(name="goodsManagementCd", required=false) String goodsManagementCd,
								@RequestParam(name="orderCd", required=false) String orderCd,
								@RequestParam(name="goodsCd", required=false) String goodsCd,
								@RequestParam(name="orderExpirationDate", required=false) String orderExpirationDate,
								@RequestParam(name="giveTime", required=false) String giveTime,
								@AuthenticationPrincipal PrincipalDetails principal, GoodsManagement goodsManagement, Order order) {
		
		if(principal != null) {
			String sessionId = (String)principal.getUsername();
			log.info("sessionId 확인 : {}", sessionId);
			
			//현재 카페 이용중인 정보 확인
			GoodsManagement useById = goodsManagementService.getUseById(sessionId);
			log.info("좌석이용 확인 : {}", useById);
			model.addAttribute("useById", useById);
		}
		
			
	
		
		
		model.addAttribute("goodsManagementCd", goodsManagementCd);
		model.addAttribute("orderCd", orderCd);
		model.addAttribute("goodsCd", goodsCd);
		model.addAttribute("giveTime", giveTime);
		model.addAttribute("orderExpirationDate", orderExpirationDate);
		
		
		return"seat/seatSelection";
	}

	
	

	
}
