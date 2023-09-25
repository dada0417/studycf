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

import studycf.dto.Goods;
import studycf.dto.GoodsManagement;
import studycf.dto.Order;
import studycf.service.GoodsManagementService;
import studycf.service.GoodsService;
import studycf.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	public final OrderService orderService;
	public final GoodsService goodsService;
	public final GoodsManagementService goodsManagementService;
	
	public OrderController(OrderService orderService, GoodsService goodsService, GoodsManagementService goodsManagementService) {
		this.orderService	=	orderService;
		this.goodsService	=	goodsService;
		this.goodsManagementService = goodsManagementService;
		
	}
	
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	
	
	
	//이용권 구매
	@PostMapping("/addOrder")
	public String addOrder(Order order) {
		log.info("이용권 주문에 입력한 데이터 : {}", order);
		
		orderService.addOrder(order);
		
	
		return "redirect:/seat/seatSelection?orderCd="+order.getOrderCd()+"&goodsCd="+order.getGoodsCd()+"&giveTime="+order.getGoodsCtgCd()+"&orderExpirationDate="+order.getOrderExpirationDate();
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
