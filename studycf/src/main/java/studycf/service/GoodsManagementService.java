package studycf.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	//이용자의 좌석확인
	public GoodsManagement getSeatCdById(String userId) {
		GoodsManagement userSeat = goodsManagementMapper.getSeatCdById(userId);
		return userSeat;
	}
	
	//총 이용시간
	public String getTotalTime(String userId) {
	
		String totalTime = goodsManagementMapper.getTotalTime(userId);
		
		log.info("값확인 : {}", totalTime);
		
		return totalTime;
	}

	//이용권 이용 내역
	public Map<String, Object> usageListById(int currentPage, Map<String, Object> goodsManagementMap){
		
		int rowPerPage = 5;
		
		double rowCount = goodsManagementMapper.getUsageListCount();
		
		int lastPage = (int)Math.ceil(rowCount/rowPerPage);
		
		int startRow = (currentPage -1)* rowPerPage;
		

		int startPageNum = 1;
		int endPageNum = 10;
		
		if (lastPage > 10) {
			if (currentPage >= 6) {
				startPageNum = currentPage - 4;
				endPageNum = currentPage + 5;

				if (endPageNum >= lastPage) {
					startPageNum = lastPage - 9;
					endPageNum = lastPage;
				}
			}
		} else {
			endPageNum = lastPage;
		}
				
		goodsManagementMap.put("startRow", startRow);
		goodsManagementMap.put("rowPerPage", rowPerPage);
		

		List<GoodsManagement> usageListById = goodsManagementMapper.usageListById(goodsManagementMap);
		 
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("lastPage", lastPage);
		resultMap.put("usageListById", usageListById);
		resultMap.put("startPageNum", startPageNum);
		resultMap.put("endPageNum", endPageNum);
		return resultMap;


	}
	
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
	public List<GoodsManagement> availableGoodsListById(String userId){
	
	 List<GoodsManagement> usingListById = goodsManagementMapper.availableGoodsListById(userId);
	
	 return usingListById; 
	 }
	
	
	


}
