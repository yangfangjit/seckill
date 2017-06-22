package com.yangfang.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yangfang.entity.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class SuccessKilledDaoTest {

	@Resource
	private SuccessKilledDao successKilledDao;
	
	@Test
	public void testInsertSuccessKilled() {
		long seckillId = 1000L;
		String userPhone = "17600106057";
		int result = successKilledDao.insertSuccessKilled(seckillId, userPhone);
		System.out.println(result);
	}
	
	@Test
	public void testQueryIdByWithSeckill() {
		long seckillId = 1000L;
		String userPhone = "17600106057";
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
		System.out.println(successKilled);
	}
}
