package studycf.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import studycf.dto.Goods;
import studycf.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	public final GoodsService goodsService;
	
	public GoodsController(GoodsService goodsService) {
		this.goodsService	=	goodsService;
	}
	
	private static final Logger log = LoggerFactory.getLogger(GoodsController.class);
	
	
	
	//이용권 목록 조회
	@GetMapping ("/goodsList")
	public String getGoodsList(Model model) {
		
		List<Goods> goodsList = goodsService.getGoodsList();
		
		model.addAttribute("title", "이용권 목록");
		model.addAttribute("goodsList", goodsList);
		
		return "goods/goodsList";
	}
	
	
	

	
}
