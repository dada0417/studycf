package studycf.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import studycf.common.FileUtils;
import studycf.dto.Board;
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
	

	
	
	/* 게시글 등록 */
	public String addBoard(Board board, MultipartFile[] boardImgFile, String fileRealPath) { 
		
		// 1. 파일 업로드
		// 2. 파일 업로드 성공시 파일 DB 인서트
		// 3. 게시글 인서트
 		// 4. 결과값 리턴
		
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
			
			System.out.println(relationFileList);
			fileMapper.uploadRelationFileWithBoard(relationFileList);
			
			return boardCd;
 		}else {
 			
 			boardMapper.addBoard(board);
			log.info("add 이후 board : {}", board);
			
			String boardCd = board.getBoardCd();
			log.info("boardCd : {}", boardCd);
			
			return boardCd;
 		}
	
	}
	/* 게시글 카테고리 조회 */
	public List<BoardCtg> getBoardCtg(BoardCtg boardCtg){
		List<BoardCtg> boardC = boardMapper.getBoardCtg(boardCtg);
		return boardC;
	}
	

	
}
