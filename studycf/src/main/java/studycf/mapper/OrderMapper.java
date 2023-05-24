package studycf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.Order;


@Mapper
public interface OrderMapper {


	//이용권 등록
	public int addOrder(Order order);

	//이용권 리스트
	public List<Order> getOrderList();
}
