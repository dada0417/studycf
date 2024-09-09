package studycf.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studycf.dto.GoodsManagement;
import studycf.dto.Seat;
import studycf.mapper.GoodsManagementMapper;
import studycf.mapper.SeatMapper;



@Service
@Transactional
public class GoodsManagementService {

	// 생성자 주입
	private final GoodsManagementMapper goodsManagementMapper;
	private final SeatMapper seatMapper;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	public GoodsManagementService(GoodsManagementMapper goodsManagementMapper, SeatMapper seatMapper) {
		this.goodsManagementMapper = goodsManagementMapper;
		this.seatMapper = seatMapper;
	}

	private static final Logger log = LoggerFactory.getLogger(GoodsManagementService.class);
	
	//만료시간이 지나면 자동으로 좌석이용 종료
	@Scheduled(fixedRate = 60000)
	public void checkOrderExpirationDate() {
		List<GoodsManagement> useSeat =  goodsManagementMapper.checkUser();
		LocalDateTime now = LocalDateTime.now();
		
		
		for(GoodsManagement gm : useSeat) {
			Seat seat = new Seat();
			LocalDateTime expiration = LocalDateTime.parse(gm.getOrderExpirationDate(), formatter);
			if(expiration.isBefore(now)) {
				LocalDateTime addmision = LocalDateTime.parse(gm.getAdmissionTime(), formatter);
				gm.setLeaveTime(gm.getOrderExpirationDate());
				gm.setRemainingTime("00:00:00");
				Duration du = Duration.between(addmision, expiration);
				String h = String.format("%02d", du.toHours());
				String m = String.format("%02d", du.toMinutes()%60);
				String s = String.format("%02d", du.getSeconds()%60);
				gm.setUseTime(h+":"+m+":"+s);
				seat.setSeatCd(gm.getSeatCd());
				seat.setSeatState("Y");
				goodsManagementMapper.modifyGM(gm);
				seatMapper.seatSelection(seat);
				
				
			}
		}
		
	}
	
	//회원이 이용권을 사용해 카페 이용중인 정보 내역
	public GoodsManagement getUseById(String userId) {
		GoodsManagement  result = goodsManagementMapper.getUseById(userId);
		
		return result;
	}
	
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

	//회원의 이용권 이용 내역
	public Map<String, Object> usageListById(int currentPage, Map<String, Object> goodsManagementMap){
	
		
		int rowPerPage = 9;
		
		
		double rowCount = goodsManagementMapper.getUsageListCount(goodsManagementMap.get("sessionId"));
		log.info("rowCount: {}", rowCount);
		
		int lastPage = (int) Math.ceil(rowCount / rowPerPage);
		
		int startRow = (currentPage - 1) * rowPerPage;
		

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
		
		log.info("goodsManagementMap 서비스: {}", goodsManagementMap);
		
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


