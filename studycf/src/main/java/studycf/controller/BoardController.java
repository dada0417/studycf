package studycf.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import studycf.dto.Board;
import studycf.dto.BoardCtg;
import studycf.service.BoardService;


@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	/* 게시글 전체 목록 조회 */
	@GetMapping("/boardList")
	public String getBoardList(Model model
							,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage) {
		
		Map<String, Object> resultMap =  boardService.getBoardList(currentPage);
		
		log.info("resultMap 받기 : {}", resultMap);
		log.info("resultMap.get(\"boardList\") : {}",resultMap.get("boardList"));
		
		model.addAttribute("title", 				"게시판");
		model.addAttribute("resultMap", 			resultMap);
		model.addAttribute("currentPage", 			currentPage);
		model.addAttribute("boardList",				resultMap.get("boardList"));
		model.addAttribute("lastPage", 				resultMap.get("lastPage"));
		model.addAttribute("startPageNum", 			resultMap.get("startPageNum"));
		model.addAttribute("endPageNum", 			resultMap.get("endPageNum"));
		
		
		return "board/boardList";
	}
	
	/* 게시글 등록 처리 */
	@PostMapping("/addBoard")
	public String addBoard(Board board
						  ,RedirectAttributes reAttr
					 	  ,@RequestParam MultipartFile[] boardImgFile
						  ,HttpServletRequest request) {
		
		log.info("회원이 입력한 게시글 내용 : {}", board); 
		
		String serverName = request.getServerName();
		String fileRealPath = "";
		
		if("localhost".equals(serverName)) {
			// server 가 localhost 일때 접근
			fileRealPath = System.getProperty("user.dir") + "/src/main/resources/static/";
			System.out.println(System.getProperty("user.dir"));
			//fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}else {
			//배포용 주소
			fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}
		
		String boardCd = boardService.addBoard(board, boardImgFile, fileRealPath);
		log.info("boardCd : {}", boardCd);
		
		reAttr.addAttribute("boardCd", boardCd);
		
		
		return "redirect:/board/boardList";
	}
	
	/* 게시글 등록 페이지 */
	@GetMapping("/addBoard")
	public String addBoard(Board board, BoardCtg boardCtg, Model model) {
		
		List<BoardCtg> boardCtgList = boardService.getBoardCtg(boardCtg);
		
		model.addAttribute("title", "게시글 등록");
		model.addAttribute("boardCtgList", boardCtgList);
		
		log.info("boardCtgList : {}", boardCtgList);
		
		return "board/addBoard";
	}
	

	
	
	

	
}
