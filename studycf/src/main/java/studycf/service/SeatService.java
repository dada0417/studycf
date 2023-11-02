package studycf.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studycf.dto.Seat;
import studycf.mapper.SeatMapper;


@Service
@Transactional
public class SeatService {

	// 생성자 주입
	private final SeatMapper seatMapper;

	public SeatService(SeatMapper seatMapper) {
		this.seatMapper = seatMapper;
	}

	private static final Logger log = LoggerFactory.getLogger(SeatService.class);
	
	//이용중인 좌석 확인
	public List<Seat> seatCheck(){
		List<Seat> seatCheck = seatMapper.seatCheck();
		log.info("예약된 좌석 확인 서비스");
		return seatCheck;
	}
	
	//이용중인 좌석 갯수
	public int getSeatCount() {
		int seatCount = seatMapper.getSeatCount();
		return seatCount;
	}
	
	
	//좌석 이용 
	public int seatSelection(Seat seat) {
		
		return seatMapper.seatSelection(seat);
	}
	
	//좌석 목록
	public List<Seat> getSeatList(){
		
		List<Seat> seatList = seatMapper.getSeatList();
		log.info("좌석 리스트 서비스");
	
		return seatList;
	}
	
	


}
