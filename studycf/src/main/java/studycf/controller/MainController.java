package studycf.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import studycf.dto.Goods;
import studycf.service.GoodsService;
import studycf.service.SeatService;

@Controller
public class MainController {
	
	public final SeatService seatService;
	public final GoodsService goodsService;
	
	public MainController(SeatService seatService, GoodsService goodsService) {
		this.seatService = seatService;
		this.goodsService = goodsService;
	}
	
	@GetMapping("/")
	public String index(Model model, String goodsCtgCd) {
		//이용중 좌석수
		int seatCount = seatService.getSeatCount();
		//이용권 목록
		List<Goods> goodsList = goodsService.getGoodsList(goodsCtgCd);
		
		model.addAttribute("seatCount", seatCount);
		model.addAttribute("goodsList", goodsList);
		return "index";
	}
}
