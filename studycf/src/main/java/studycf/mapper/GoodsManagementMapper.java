package studycf.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.GoodsManagement;



@Mapper
public interface GoodsManagementMapper {
	
	//이용권 이용 내역 총 튜플 수 
	public int getUsageListCount();
	
	//회원의 카페 이용 내역 목록
	public List<Map<String, Object>> usageListById(Map<String, Object> paramMap);

	//회원의 사용가능한 이용권 목록
	public List<GoodsManagement> availableGoodsListById(String userId);
	
	//이용권 사용 정보 추가
	public int modifyGM(GoodsManagement goodsManagement);
	
	//이용권 사용
	public int addGoodsManagement(GoodsManagement goodsManagement);

	
}
