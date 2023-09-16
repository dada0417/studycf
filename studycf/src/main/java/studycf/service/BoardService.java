package studycf.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import studycf.common.FileUtils;
import studycf.dto.Board;
import studycf.dto.BoardComment;
import studycf.dto.BoardCtg;
import studycf.mapper.BoardMapper;
import studycf.mapper.FileMapper;


@Service
@Transactional
public class BoardService {
	
	private final BoardMapper boardMapper;
	private final FileMapper fileMapper;
	
	public BoardService(BoardMapper boardMapper, FileMapper fileMapper) {
		this.boardMapper = boardMapper;
		this.fileMapper = fileMapper;
	}
	
	private static final Logger log = LoggerFactory.getLogger(BoardService.class);
	

	
	/*게시글 댓글 수*/
	public int commentCount(String boardCd) {
		return boardMapper.commentCount(boardCd);
	}
	
	/* 게시글 코드로 댓글 조회 */
	public List<BoardComment> getBoardCommentList(String boardCd, String parentCd){
		List<BoardComment> boardCommentList = boardMapper.getBoardCommentList(boardCd, parentCd); 
		return boardCommentList; 
	}
	
	/* 게시글 답글등록 */
	public int addBoardComment(BoardComment boardComment) {
		int result =  boardMapper.addBoardComment(boardComment); 
		return result;
	}
	
	/* 게시글 수정 */
	public int modifyBoard(Board board) { 
		int	result = boardMapper.modifyBoard(board);
			return result;
		
		}

	/*게시글 상세보기  다음글*/
	public Board getBoardNext(String boardCd){
		Board result = boardMapper.getBoardNext(boardCd);
		
		return result;
	}
	
	/*게시글 상세보기 이전글*/
	public Board getBoardPre(String boardCd){
		Board result = boardMapper.getBoardPre(boardCd);
		
		return result;
	}
	
	/* 게시글 코드로 상세 조회   */
	public Board getBoardDetail(String boardCd) { 
		Board board = boardMapper.getBoardDetail(boardCd); 
		return board; 
	}
	
	/* 게시글 조회수 증가 */
	public int boardViewUpdate(String boardCd) { 
		int result = boardMapper.boardViewUpdate(boardCd);
		return result; 
	}
	

	/* 게시글 전체 목록 조회 */
	public Map<String, Object> getBoardList(int currentPage, String boardCtgCd) {
		
		//페이지 당 게시글 수
		int rowPerPage = 9;
		//게시글 전체 수
		double rowCount = boardMapper.getBoardCount();
		//시작페이지 마지막페이지 계산
		int lastPage = (int) Math.ceil(rowCount / rowPerPage);
		
		int startRow = (currentPage - 1) * rowPerPage;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("startRow", startRow);
		paramMap.put("rowPerPage", rowPerPage);
		paramMap.put("boardCtgCd", boardCtgCd);
		
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
		
		log.info("paramMap : {}", paramMap);
		
		List<Map<String, Object>> boardList = boardMapper.getBoardList(paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("lastPage", 		lastPage);
		resultMap.put("boardList", 		boardList);
		resultMap.put("startPageNum", 	startPageNum);
		resultMap.put("endPageNum", 	endPageNum);
		
		return resultMap;
	}
	
	
	/* 게시글 등록 */
	public void addBoard(Board board, MultipartFile[] boardImgFile, String fileRealPath) { 
		
		boolean fileCheck = true;
		
		for (MultipartFile multipartFile : boardImgFile){
			if(!multipartFile.isEmpty()) {
				fileCheck = false;
			}
		}
		
		if(!fileCheck) {
			log.info("파일 있음"+ boardImgFile);
			
			//파일 업로드 위한 객체 생성 
			FileUtils fu = new FileUtils(boardImgFile, board.getUserId(), fileRealPath);
			List<Map<String, String>> dtoFileList = fu.parseFileInfo();
			
			// t_file 테이블에 삽입
			fileMapper.uploadFile(dtoFileList);
			
			// 게시글 등록 - 게시글 코드 selectKey로 담아 줌
			boardMapper.addBoard(board);
			log.info("add 이후 board : {}", board);
			
			String boardCd = board.getBoardCd();
			log.info("boardCd : {}", boardCd);
			
			// 릴레이션 테이블에 삽입
			List<Map<String, String>> relationFileList = new ArrayList<>();
			for(Map<String, String> m : dtoFileList) {
				m.put("boardCd", boardCd);
				relationFileList.add(m);
			}
			
			fileMapper.uploadRelationFileWithBoard(relationFileList);
			
 		}else {
 			
 			boardMapper.addBoard(board);
			log.info("add 이후 board : {}", board);
			
 		}
	
	}
	/* 게시글 카테고리 조회 */
	public List<BoardCtg> getBoardCtg(BoardCtg boardCtg){
		List<BoardCtg> boardC = boardMapper.getBoardCtg(boardCtg);
		return boardC;
	}
	

	
}
