package studycf.dto;

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
	private String boardView;
	private String boardCommentView;
	private String boardPw;
}
