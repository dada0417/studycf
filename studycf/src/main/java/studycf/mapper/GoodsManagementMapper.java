package studycf.mapper;


import org.apache.ibatis.annotations.Mapper;

import studycf.dto.GoodsManagement;



@Mapper
public interface GoodsManagementMapper {


	//이용권 등록
	public int addGoodsManagement(GoodsManagement goodsManagement);

	
}
