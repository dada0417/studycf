package studycf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.Board;
import studycf.dto.BoardCtg;

@Mapper
public interface BoardMapper {

	
	// 게시글 등록
	public int addBoard(Board board);
	
	// 게시글 카테고리 조회
	public List<BoardCtg> getBoardCtg(BoardCtg boardCtg);
	
	
}
