package studycf.mapper;


import org.apache.ibatis.annotations.Mapper;

import studycf.dto.GoodsManagement;



@Mapper
public interface GoodsManagementMapper {


	//이용권 사용 정보 추가
	public int modifyGM(GoodsManagement goodsManagement);
	
	//이용권 사용
	public int addGoodsManagement(GoodsManagement goodsManagement);

	
}
