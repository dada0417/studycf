package studycf.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import studycf.dto.Board;
import studycf.dto.BoardComment;
import studycf.dto.BoardCtg;

@Mapper
public interface BoardMapper {
	
	
	//게시글 댓글 수 
	public int commentCount(String boardCd);
	
	// 게시글 댓글 조회 
	public List<BoardComment> getBoardCommentList(String boardCd, String parentCd, String boardCommentCd);
	
	//게시글 댓글 등록
	public int addBoardComment(BoardComment boardComment);
	
	// 게시글 수정 
	public int modifyBoard(Board board);
	
	//게시글 상세보기 페이지에서 다음글  보기
	public Board getBoardNext(String boardCd);

	//게시글 상세보기 페이지에서 이전글  보기
	public Board getBoardPre(String boardCd);
	
	//게시글 코드로 상세 조회 
	public Board getBoardDetail(String boardCd);
	
	//게시글 조회수 업데이트
	public int boardViewUpdate(String boardCd);
	
	//게시글 전체 목록 조회 
	public List<Map<String, Object>> getBoardList(Map<String, Object> paramMap);
	
	// 게시글 테이블 총 row(튜플) 수
	public int getBoardCount();
	
	// 게시글 등록
	public int addBoard(Board board);
	
	// 게시글 카테고리 조회
	public List<BoardCtg> getBoardCtg(BoardCtg boardCtg);
	
	
}
