/**
 * 
 */
package com.yangfang.service;

import java.util.List;

import com.yangfang.dto.Exposer;
import com.yangfang.dto.SeckillExecution;
import com.yangfang.entity.Seckill;
import com.yangfang.exception.RepeatSeckillException;
import com.yangfang.exception.SeckillCloseException;
import com.yangfang.exception.SeckillException;

/**
 * 业务接口：站在使用者的角度设计接口 三个方面：方法定义粒度、参数、返回类型（return or 抛出异常）
 * 
 * @author Francis Yang
 *
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀记录
	 * 
	 * @return
	 */
	List<Seckill> getSeckillList();

	Seckill getById(long seckillId);

	/**
	 * 输出秒杀接口
	 * 
	 * @param seckillId
	 */
	Exposer exportSeckillUrl(long seckillId);

	/**
	 * 执行秒杀接口
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 */
	SeckillExecution executeSeckill(long seckillId, String userPhone, String md5)
			throws SeckillException, RepeatSeckillException, SeckillCloseException;
}
