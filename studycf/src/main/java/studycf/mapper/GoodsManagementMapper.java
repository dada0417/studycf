package studycf.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.GoodsManagement;



@Mapper
public interface GoodsManagementMapper {
	
	//본인 이용 좌석 확인
	public GoodsManagement getSeatCdById(String userId);
	
	//카페 총 이용시간 구하기
	public String getTotalTime(String userId); 
	
	//이용권 이용 내역 총 튜플 수 
	public int getUsageListCount();
	
	//회원의 카페 이용 내역 목록
	public List<GoodsManagement> usageListById(Map<String, Object> goodsManagementMap);

	//회원의 사용가능한 이용권 목록
	public List<GoodsManagement> availableGoodsListById(String userId);
	
	//이용권 사용 정보 추가
	public int modifyGM(GoodsManagement goodsManagement);
	
	//이용권 사용
	public int addGoodsManagement(GoodsManagement goodsManagement);

	
}
