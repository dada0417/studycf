package studycf.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Board {

	private String boardCd;
	private String boardCtgCd;
	private String userId;
	private String boardTitle;
	private String boardContent;
	private String boardDate;
	private String boardViews;
	private String boardCommentNum;
	
	private BoardCtg boardCtg;
	private List<Map<String, String>> 	relFileWithBoard;
	private List<TFile> 				tFile;
}
