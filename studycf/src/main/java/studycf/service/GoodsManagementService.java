package studycf.service;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studycf.dto.GoodsManagement;
import studycf.mapper.GoodsManagementMapper;



@Service
@Transactional
public class GoodsManagementService {

	// 생성자 주입
	private final GoodsManagementMapper goodsManagementMapper;

	public GoodsManagementService(GoodsManagementMapper goodsManagementMapper) {
		this.goodsManagementMapper = goodsManagementMapper;
	}

	private static final Logger log = LoggerFactory.getLogger(GoodsManagementService.class);

	//이용권 사용  정보 추가
	public int modifyGM(GoodsManagement goodsManagement) {
		int result = goodsManagementMapper.modifyGM(goodsManagement);
		
		return result;
	}
	
	//이용권 사용
	public int addGoodsManagement(GoodsManagement goodsManagement) {
		
		int result = goodsManagementMapper.addGoodsManagement(goodsManagement);
		return result;
	}
	
	 //아이디별 사용가능한 이용권 목록 
	public List<GoodsManagement> usingListById(String userId){
	
	 List<GoodsManagement> usingListById = goodsManagementMapper.usingListById(userId);
	
	 return usingListById; 
	 }
	
	
	


}
