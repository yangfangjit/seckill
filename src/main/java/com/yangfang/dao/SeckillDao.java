/**
 * 
 */
package com.yangfang.dao;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.yangfang.entity.Seckill;

/**
 * @author Francis Yang
 *
 */
public interface SeckillDao {

	/**
	 * 
	 * @param seckillId
	 * @param killTime
	 * @return 如果影响行数>1，秒杀成功
	 */
	int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
	
	/**
	 * 
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
