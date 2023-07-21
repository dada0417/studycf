package studycf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.Seat;


@Mapper
public interface SeatMapper {
	
	//이용중인 좌석 수
	public int getSeatCount();

	//좌석 상태 확인
	public List<Seat> seatCheck();
	
	//좌석 이용 
	public int seatSelection(Seat seat); 
	
	//좌석 리스트
	public List<Seat> getSeatList();
}
