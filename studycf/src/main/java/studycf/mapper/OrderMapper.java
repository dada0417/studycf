package studycf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.Order;


@Mapper
public interface OrderMapper {
	
	//이용권 구매 목록
	public List<Order> getOrderById(String userId);
	
	//이용권 남은 시간 수정
	public int modifyOrder(Order order);
	
	//이용권 등록
	public int addOrder(Order order);

	//이용권 리스트
	public List<Order> getOrderList();
}
