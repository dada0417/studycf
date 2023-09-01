package studycf.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.Board;
import studycf.dto.BoardCtg;

@Mapper
public interface BoardMapper {
	
	//게시글 전체 목록 조회 
	public List<Map<String, Object>> getBoardList(Map<String, Object> paramMap);
	
	// 게시글 테이블 총 row(튜플) 수
	public int getBoardCount();
	
	// 게시글 등록
	public int addBoard(Board board);
	
	// 게시글 카테고리 조회
	public List<BoardCtg> getBoardCtg(BoardCtg boardCtg);
	
	
}
