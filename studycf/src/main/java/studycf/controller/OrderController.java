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

import studycf.dto.Goods;
import studycf.dto.Order;
import studycf.service.GoodsService;
import studycf.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	public final OrderService orderService;
	public final GoodsService goodsService;
	
	public OrderController(OrderService orderService, GoodsService goodsService) {
		this.orderService	=	orderService;
		this.goodsService	=	goodsService;
	}
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	
	
	
	//이용권 구매
	@PostMapping("/addOrder")
	public String addOrder(Order order) {
		log.info("이용권 주문에 입력한 데이터 : ", order);
		
	
		orderService.addOrder(order);
		return "redirect:/goods/goodsList";
	}
	
	@GetMapping("/addOrder")
	public String addGoods(Model model, 
			@RequestParam(name="goodsCtgCd", required = false) String goodsCtgCd) {
		
		List<Goods> goodsList = goodsService.getGoodsList(goodsCtgCd);
		
		model.addAttribute("title", "이용권 등록");
		model.addAttribute("goodsList", goodsList);
		
		return "/order/addOrder";
	}
	
	
	//이용권 목록 조회
	@GetMapping ("/orderList")
	public String getOrderList(Model model) {
		
		List<Order> orderList = orderService.getOrderList();
		
		model.addAttribute("title", "이용권 목록");
		model.addAttribute("orderList", orderList);
		
		return "order/orderList";
	}
	
	
	

	
}
