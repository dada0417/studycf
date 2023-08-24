package studycf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoodsManagement {
	private String goodsManagementCd; 
	private String userId;
	private String orderCd;
	private String goodsCd;
	private String seatCd;
	private String admissionTime;
	private String leaveTime;
	private String useTime;
	private String remainingTime;
	private String orderExpirationDate;
	
	private Order order;
	private Goods goods;
	private GoodsCtg goodsCtg;
	private Seat seat;
	
}
