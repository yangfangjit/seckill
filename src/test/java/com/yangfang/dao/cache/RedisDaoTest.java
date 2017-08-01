package com.yangfang.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yangfang.dao.SeckillDao;
import com.yangfang.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class RedisDaoTest {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisDaoTest.class);
	
	private long id = 1001; 
	
	@Autowired
	private SeckillDao seckillDao;
	
	@Autowired
	private RedisDao redisDao;
	
	@Test
	public void test() {
		Seckill seckill = redisDao.getSeckill(id);
		if (seckill == null) {
			seckill = seckillDao.queryById(id);
			if (seckill != null) {
				logger.info(seckill.toString());
				redisDao.putSeckill(seckill);
			}
			
			Seckill seckill2 = redisDao.getSeckill(id);
			logger.info(seckill2.toString());
		}
		
	}

}
