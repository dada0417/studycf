package studycf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import studycf.dto.Board;
import studycf.dto.BoardComment;
import studycf.dto.BoardCtg;
import studycf.dto.Seat;
import studycf.service.BoardService;


@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	
	
	/*게시글 댓글 수정*/
	@GetMapping("/modifyComment")
	public String modifyComment(BoardComment boardComment
								,@RequestParam(name = "boardCd", required = false) String boardCd) {
		
		log.info("boardComment : {}", boardComment);
		
		boardService.modifyComment(boardComment);
		
		return "redirect:/board/boardDetail?boardCd="+ boardCd;
		
	}

	
	/* 게시물 댓글 등록 */
	@PostMapping("/addBoardComment")
	public String addBoardComment(BoardComment boardComment
								,@RequestParam(name = "boardCd", required = false) String boardCd
								,@RequestParam(name = "parentCd", required = false) String parentCd) {
		log.info("boardCd : {}", boardCd);
		log.info("parentCd : {}", parentCd);
		log.info("boardComment : {}", boardComment);
		
		if(boardComment.getParentCd() == "") {
			//댓글리스트
			List<BoardComment> commentPa = boardService.getBoardCommentList(boardCd, parentCd);
			
			if(commentPa.isEmpty()) {
				//댓글이 등록되지 않은 상태
				boardComment.setParentCd("0");
				boardService.addBoardComment(boardComment);
			}else {
				//댓글이 있을 때 댓글 추가, 리스트 마지막 값
				BoardComment recentList = commentPa.get(commentPa.size()-1);
				String recentParent = recentList.getParentCd();
				log.info("recentParent : {}", recentParent);
				
				boardComment.setParentCd(recentParent+1);
				
				log.info("boardComment : {}", boardComment);
				
				boardService.addBoardComment(boardComment);	
			}
		}else {
			//댓글에 답글 달기
			List<BoardComment> commentPa2 = boardService.getBoardCommentList(boardCd, parentCd);
			//리스트가져오기
			BoardComment recentList2 = commentPa2.get(commentPa2.size()-1);
			String paOrder = recentList2.getPaOrder();
			if(paOrder == null) {
				//답댓이 없을 때 
				boardComment.setPaOrder("0");
				boardService.addBoardComment(boardComment);
			}else {
				//있을 때
				boardComment.setPaOrder(paOrder+1);
				boardService.addBoardComment(boardComment);
			}
		}
		
		Board board = boardService.getBoardDetail(boardCd);
		
		
		return "redirect:/board/boardDetail?"+"boardCd=" + board.getBoardCd();
	}
	
	
	/* 게시글수정 처리 */
	@GetMapping("/deleteBoard")
	public String deleteBoard(Board board, Model model) {
		
		boardService.modifyBoard(board);
		
		return "redirect:/board/boardList";
	}

	/* 게시글수정 처리 */
	@PostMapping("/modifyBoard")
	public String modifyBoard(Board board, Model model) {
		
		boardService.modifyBoard(board);
		
		return "redirect:/board/boardList";
	}
	
	/* 게시글수정 페이지 */
	@GetMapping("/modifyBoard")
	public String modifyBoard(Model model, BoardCtg boardCtg ,HttpSession session,
							@RequestParam(name = "boardCd", required = false) String boardCd) {
		
		log.info("받아온 boardCd : {}", boardCd);
		
		Board board = boardService.getBoardDetail(boardCd);
		String userId = (String)session.getAttribute("SID");

		List<BoardCtg> boardCtgList = boardService.getBoardCtg(boardCtg);
		
		model.addAttribute("title", "게시글 수정");
		model.addAttribute("board", board);
		model.addAttribute("userId", userId);
		model.addAttribute("boardCtgList", boardCtgList);


		return "board/modifyBoard";
	}
	
	/* 게시글 코드로 상세 조회 */
	/* 게시물 댓글/답글 조회  */
	@GetMapping("/boardDetail")
	public String getBoardDetail(Model model 
								,@RequestParam(value = "boardCd", required = false) String boardCd
								,@RequestParam(value = "parentCd", required = false) String parentCd
								) {
		
		Board board = boardService.getBoardDetail(boardCd);
		Board boardPre = boardService.getBoardPre(boardCd);
		Board boardNext = boardService.getBoardNext(boardCd);
		List<BoardComment> commentList = boardService.getBoardCommentList(boardCd, parentCd);
		
		int commentCount = boardService.commentCount(boardCd);
		
		boardService.boardViewUpdate(boardCd);
		
		model.addAttribute("title", 				"게시글 상세보기");
		model.addAttribute("board", 				board);
		model.addAttribute("boardPre", 				boardPre);
		model.addAttribute("boardNext", 			boardNext);
		model.addAttribute("commentList", 			commentList);
		model.addAttribute("commentCount", 			commentCount);
		
		log.info("boardcd : {}", boardCd);
		log.info("board : {}", board);
		log.info("boardPre : {}", boardPre);
		log.info("boardNext : {}", boardNext);
		
		return "board/boardDetail";
	}
	
	/* 게시글 전체 목록 조회 */
	@GetMapping("/boardList")
	public String getBoardList(Model model
							,@RequestParam(name = "currentPage", required = false, defaultValue = "1") int currentPage
							,@RequestParam(value = "boardCtgCd", required = false) String boardCtgCd) {
		log.info("boardCtgCd 받기 : {}", boardCtgCd);
		
		Map<String, Object> resultMap =  boardService.getBoardList(currentPage, boardCtgCd);
		
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
					 	  ,@RequestParam MultipartFile[] boardImgFile
						  ,HttpServletRequest request) {
		
		log.info("회원이 입력한 게시글 내용 : {}", board); 
		
		String serverName = request.getServerName();
		String fileRealPath = "";
		
		if("localhost".equals(serverName)) {
			// server 가 localhost 일때 접근
			fileRealPath = System.getProperty("user.dir") + "/src/main/resources/static/";
		}else {
			//배포용 주소
			fileRealPath = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/static/");
		}
		
		boardService.addBoard(board, boardImgFile, fileRealPath);
		
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
