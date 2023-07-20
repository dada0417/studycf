package studycf.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/addGoodsManagement")
	public String addGoods(Model model) {
				
		model.addAttribute("title", "이용권 등록");		
		return "/goodsManagement/addGoodsManagement";
	}
	
	
}
