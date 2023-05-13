package studycf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsManagement {
	private String GoodsManagementCd; 
	private String UserId;
	private String GoodsCd;
	private String SeatCd;
	private String AdmissionTime;
	private String LeaveTime;
	private String UseTime;
	private String RemainingTime;
	private String GoodsEpirationDate;
	
}
