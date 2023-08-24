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
	
	
	@GetMapping("/usingList")
	public String availableGoodsListById(HttpSession session, Model model) {
		String sessionId = (String)session.getAttribute("SID");
		
		 List<GoodsManagement> availableGoodsList = goodsManagementService.availableGoodsListById(sessionId);
		
		model.addAttribute("availableGoodsList", availableGoodsList);
		
		return "/goodsManagement/usingList";
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
	public String addGoods(Model model) {
				
		model.addAttribute("title", "이용권 등록");		
		return "/goodsManagement/addGoodsManagement";
	}
	
	
}
