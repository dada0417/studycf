package studycf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	private String userId;
	private String userPw;
	private String userNm;
	private String userPhone;
	private String userEmail; 
	private String userDrop;
	private String userRegDate;
	private String role;
	
	private GoodsManagement goodsManagement;
}
