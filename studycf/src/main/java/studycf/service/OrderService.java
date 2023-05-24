package studycf.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studycf.dto.Order;
import studycf.mapper.OrderMapper;


@Service
@Transactional
public class OrderService {

	// 생성자 주입
	private final OrderMapper orderMapper;

	public OrderService(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	//이용권 등록
	public int addOrder(Order order) {
		
		int result = orderMapper.addOrder(order);
		return result;
	}
	
	//이용권 목록
	public List<Order> getOrderList(){
		
		List<Order> orderList = orderMapper.getOrderList();
		log.info("이용권 리스트 서비스");
	
		return orderList;
	}
	
	


}
