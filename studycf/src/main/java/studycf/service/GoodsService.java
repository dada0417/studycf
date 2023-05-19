package studycf.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import studycf.dto.Goods;
import studycf.mapper.GoodsMapper;


@Service
@Transactional
public class GoodsService {

	// 생성자 주입
	private final GoodsMapper goodsMapper;

	public GoodsService(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

	private static final Logger log = LoggerFactory.getLogger(GoodsService.class);

	
	
	public List<Goods> getGoodsList(){
		
		
		List<Goods> goodsList = goodsMapper.getGoodsList();
		log.info("서비스 됨?");
		
		
		return goodsList;
	}
	
	


}
