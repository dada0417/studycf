package studycf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Goods {
	private String goodsCd;
	private String goodsCtgCd;
	private String goodsNm;
	private String goodsTimeCon;
	private String price;
	private String goodsExpirationDate;
	private String goodsInsertDate;
	
	private GoodsCtg goodsCtg;
}
