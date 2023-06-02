package studycf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.Seat;


@Mapper
public interface SeatMapper {

	//좌석 이용 
	public int modifySeat(String seatCd); 
	
	//좌석 리스트
	public List<Seat> getSeatList();
}
