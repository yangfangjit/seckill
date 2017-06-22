/**
 * 
 */
package com.yangfang.exception;

/**
 * 重复秒杀异常（运行期异常）
 * 
 * @author francisyang
 *
 */
public class RepeatSeckillException extends SeckillException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3886473767565925356L;

	public RepeatSeckillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RepeatSeckillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
