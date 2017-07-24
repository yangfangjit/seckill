package com.yangfang.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yangfang.dao.SeckillDao;
import com.yangfang.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class RedisDaoTest {
	
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
				System.out.println(seckill);
				redisDao.putSeckill(seckill);
			}
			
			Seckill seckill2 = redisDao.getSeckill(id);
			System.out.println(seckill2);
		}
		
	}

}
