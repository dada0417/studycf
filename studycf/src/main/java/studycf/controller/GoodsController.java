package studycf.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import studycf.dto.Goods;
import studycf.mapper.GoodsMapper;
import studycf.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	public final GoodsService goodsService;
	public final GoodsMapper goodsMapper;
	
	public GoodsController(GoodsService goodsService, GoodsMapper goodsMapper) {
		this.goodsService	=	goodsService;
		this.goodsMapper	=	goodsMapper;
	}
	
	private static final Logger log = LoggerFactory.getLogger(GoodsController.class);
	
	
	//이용권 수정
	@PostMapping("/modifyGoods")
	public String modifyGoods(Goods goods) {
		log.info("이용권 정보 수정 폼 입력값 : {}", goods); 
		goodsService.modifyGoods(goods);
		return "redirect:/goods/goodsList";
	}
	
	
	//이용권 수정
	@GetMapping("/modifyGoods")
	public String modifyGoods(@RequestParam(value="goodsCd") String goodsCd
			,Model model) {
		Goods goods = goodsService.getGoodsInfoByCd(goodsCd);
		log.info("상세보기 값 = {}", goods);
		
		model.addAttribute("title", "이용권 수정");
		model.addAttribute("goods", goods);
		
		return "goods/modifyGoods";
	}
	
	
	//이용권 등록
	@PostMapping("/addGoods")
	public String addGoods(Goods goods) {
		log.info("이용권 등록에 입력한 데이터 : ", goods);
		
		goodsService.addGoods(goods);
		return "redirect:/goods/goodsList";
	}
	//이용권 등록
	@GetMapping("/addGoods")
	public String addGoods(Model model) {
		model.addAttribute("title", "이용권 등록");
		
		return "/goods/addGoods";
	}
	
	@PostMapping("/goodsList")
	@ResponseBody
	public List<Goods> getTownCdList(@RequestParam(value = "goodsCtgCd" , required = false) String goodsCtgCd){
		return goodsMapper.getGoodsList(goodsCtgCd);
	}
	
	//이용권 목록 조회
	@GetMapping ("/goodsList")
	public String getGoodsList(Model model, @RequestParam(value="goodsCtgCd", required = false) String goodsCtgCd) {
		
		List<Goods> goodsList = goodsService.getGoodsList(goodsCtgCd);
		
		model.addAttribute("title", "이용권 목록");
		model.addAttribute("goodsList", goodsList);
		
		return "goods/goodsList";
	}
	
	
	

	
}
