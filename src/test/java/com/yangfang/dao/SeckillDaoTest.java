package com.yangfang.dao;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yangfang.entity.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class SeckillDaoTest {

	@Resource
	private SeckillDao seckillDao;

	@Test
	public void testQueryById() throws Exception {
		long seckillId = 1000L;
		Seckill seckill = seckillDao.queryById(seckillId);
		System.out.println(seckill.toString());
	}

	@Test
	public void testQueryAll() throws Exception {
		int offset = 1;
		int limit = 4;
		List<Seckill> seckillList = seckillDao.queryAll(offset, limit);
		for (Seckill seckill : seckillList) {
			System.out.println(seckill.toString());
		}
	}

	@Test
	public void testReduceNumber() throws Exception {
		long seckillId = 1000L;
		Date killTime = new Date();
		int result = seckillDao.reduceNumber(seckillId, killTime);
		System.out.println(result);
		Seckill seckill = seckillDao.queryById(seckillId);
		System.out.println(seckill.toString());
	}

}
