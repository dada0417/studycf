package studycf.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardComment {

	private String boardCommentCd;
	private String boardCd;
	private String userId;
	private String boardCommentDetail;
	private String boardCommentDate;
	private String boardCommentState;
	private String parentCd;
	private String paOrder;
	
}
