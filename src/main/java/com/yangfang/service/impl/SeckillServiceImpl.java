/**
 * 
 */
package com.yangfang.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.yangfang.dao.SeckillDao;
import com.yangfang.dao.SuccessKilledDao;
import com.yangfang.dao.cache.RedisDao;
import com.yangfang.dto.Exposer;
import com.yangfang.dto.SeckillExecution;
import com.yangfang.entity.Seckill;
import com.yangfang.entity.SuccessKilled;
import com.yangfang.enums.SeckillStateEnum;
import com.yangfang.exception.RepeatSeckillException;
import com.yangfang.exception.SeckillCloseException;
import com.yangfang.exception.SeckillException;
import com.yangfang.service.SeckillService;

/**
 * @author Francis Yang
 *
 */
@Service
public class SeckillServiceImpl implements SeckillService {

	private static Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

	@Autowired
	private SeckillDao seckillDao;

	@Autowired
	private SuccessKilledDao successKilledDao;
	
	@Autowired
	private RedisDao redisDao;

	// 盐值，混淆MD5
	private final String slat = "lhasdhlsdlf*alsfdjalj^#ljasdjlkfjalksdjflkajfdlskfklajd&^&%$";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yangfang.service.SeckillService#getSeckillList()
	 */
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yangfang.service.SeckillService#getById(long)
	 */
	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yangfang.service.SeckillService#exportSeckillUrl(long)
	 */
	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		Seckill seckill = redisDao.getSeckill(seckillId);
		if(seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			}
			redisDao.putSeckill(seckill);
		}

		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yangfang.service.SeckillService#executeSeckill(long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	/**
	 * 使用注解控制事务方法的优点
	 * 开发团队达成一致约定，明确标注事务方法的编程风格
	 * 保证事务方法执行的时间尽可能短，不要穿插其他的网络操作
	 * 不是所有的方法都需要事务
	 */
	public SeckillExecution executeSeckill(long seckillId, String userPhone, String md5)
			throws SeckillException, RepeatSeckillException, SeckillCloseException {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		// 执行秒杀逻辑：减库存，记录购买成功
		Date nowTime = new Date();
		try {
			int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
			if (updateCount <= 0) {
				throw new SeckillCloseException("seckill is closed");
			} else {
				// 记录购买行为
				int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if (insertCount <= 0) {
					throw new RepeatSeckillException("seckill repeated");
				} else {
					SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatSeckillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SeckillException("seckill inner error" + e.getMessage());
		}
	}

	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
}
