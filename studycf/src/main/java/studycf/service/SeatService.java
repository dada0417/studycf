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

	
	//좌석 이용 
	public int modifySeat(String seatCd) {
		
		return seatMapper.modifySeat(seatCd);
	}
	
	//좌석 목록
	public List<Seat> getSeatList(){
		
		List<Seat> seatList = seatMapper.getSeatList();
		log.info("좌석 리스트 서비스");
	
		return seatList;
	}
	
	


}
