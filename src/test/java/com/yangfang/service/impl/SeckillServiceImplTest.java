/**
 * 
 */
package com.yangfang.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yangfang.dto.Exposer;
import com.yangfang.dto.SeckillExecution;
import com.yangfang.entity.Seckill;
import com.yangfang.exception.RepeatSeckillException;
import com.yangfang.exception.SeckillCloseException;
import com.yangfang.service.SeckillService;

/**
 * @author Francis Yang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class SeckillServiceImplTest {

	private static Logger logger = LoggerFactory.getLogger(SeckillServiceImplTest.class);

	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetSeckillList() {
		List<Seckill> seckills = seckillService.getSeckillList();
		logger.info("list = {}", seckills);
	}

	@Test
	public void testGetById() {
		Seckill seckill = seckillService.getById(1000L);
		logger.info(seckill.toString());
	}

	@Test
	public void testSeckillLogic() {
		long seckillId = 1003L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()) {
			logger.info("exposer = {}", exposer.toString());
			String userPhone = "12345678900";
			String md5 = exposer.getMd5();
			try {
				SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
				logger.info("result = {}", execution);
			} catch (RepeatSeckillException e) {
				logger.error(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			}
		} else {
			logger.warn("exposer = {}", exposer);
		}

	}

}
