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
import studycf.service.GoodsManagementService;


@Controller
@RequestMapping("/goodsManagement")
public class GoodsManagementController {
	
	public final GoodsManagementService goodsManagementService;

	
	
	public GoodsManagementController(GoodsManagementService goodsManagementServicee) {
		this.goodsManagementService	=	goodsManagementServicee;
	}
	
	private static final Logger log = LoggerFactory.getLogger(GoodsManagementController.class);
	
	//좌석 선택시 입장시간 여부 확인
	@PostMapping("/admissionTimeCheck")
	@ResponseBody
	public GoodsManagement admissionTimeCheck(@RequestParam(name = "userId" , required = false) String userId){
		GoodsManagement admissionTimeCheck = goodsManagementService.getUseById(userId);
		log.info("입장시간 확인 컨트롤 목록 :{}", admissionTimeCheck);
		return admissionTimeCheck;
	}
	
	
	//예약된 좌석 확인
	@PostMapping("/seatCheckById")
	@ResponseBody
	public GoodsManagement getSeatCdById(@RequestParam(name = "userId" , required = false) String userId){

		GoodsManagement seatCheckById = goodsManagementService.getSeatCdById(userId);
		
		log.info("본인 좌석 확인 컨트롤 목록 :{}", seatCheckById);
		return seatCheckById;
	}
	
	//회원이 이용가능한 이용권 목록
	@GetMapping("/usingList")
	public String availableGoodsListById(@AuthenticationPrincipal PrincipalDetails principal, Model model, Order order) {
		
		if(principal == null) {
			model.addAttribute("error", "로그인이 필요한 페이지입니다.");
			return "goodsManagement/usingList";
		}else {
			String sessionId = (String)principal.getUsername();
			
			log.info("여긴됨?");
			
			 List<GoodsManagement> availableGoodsList = goodsManagementService.availableGoodsListById(sessionId);
			 log.info("여기눈?");
			model.addAttribute("availableGoodsList", availableGoodsList);
			
			return "goodsManagement/usingList";
		}

	}
	
	//이용권 사용 정보 추가
	@PostMapping("/modifyGM")
	public String modifyGM(GoodsManagement goodsManagement) {
				
		goodsManagementService.modifyGM(goodsManagement);
		
		return "redirect:/";
	}
	
	//이용권 사용
	@PostMapping("/addGoodsManagement")
	public String addGoodsManagement(GoodsManagement goodsManagement) {
		log.info("이용권 주문에 입력한 데이터 : ", goodsManagement);
		
	
		goodsManagementService.addGoodsManagement(goodsManagement);
		return "redirect:/seat/seatSelection";
	}
	//이용권 사용 화면 
	@GetMapping("/addGoodsManagement")
	public String addGoodsManagement(Model model) {
				
		model.addAttribute("title", "이용권 등록");		
		return "goodsManagement/addGoodsManagement";
	}
	
	
}
