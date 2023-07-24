package studycf.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.GoodsManagement;
import studycf.dto.Order;



@Mapper
public interface GoodsManagementMapper {


	//회원의 사용가능한 이용권 목록
	public List<Order> orderListById(String userId);
	
	//이용권 사용 정보 추가
	public int modifyGM(GoodsManagement goodsManagement);
	
	//이용권 사용
	public int addGoodsManagement(GoodsManagement goodsManagement);

	
}
