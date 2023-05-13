package studycf.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Goods {
	private String GoodsCd;
	private String GoodsCtgCd;
	private String GoodsNm;
	private String GoodsTimeCon;
	private String Price;
	private String GoodsExpirationDate;
	private String GoodsInsertDate;
}
