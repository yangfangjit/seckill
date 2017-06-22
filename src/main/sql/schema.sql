-- 数据库脚本

-- 创建数库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
	`seckill_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
	`name` varchar(120) NOT NULL COMMENT '商品名称',
	`number` int(10) NOT NULL COMMENT '库存数量',
	`start_time` timestamp NOT NULL COMMENT '秒杀开始时间',
    `end_time` timestamp NOT NULL COMMENT '秒杀结束时间',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`seckill_id`),
	key idx_start_time(`start_time`)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

insert into 
	seckill(name,number,start_time,end_time)
values
	('100元秒杀iPhone6',100,'2015-10-01 00:00:00','2015-10-02 00:00:00'),
	('100元秒杀iPhone6s',200,'2015-10-01 00:00:00','2015-10-02 00:00:00'),
	('100元秒杀iPhone7',300,'2015-10-01 00:00:00','2015-10-02 00:00:00'),
	('100元秒杀iPhone7plus',500,'2015-10-01 00:00:00','2015-10-02 00:00:00');

-- 创建秒杀成功明细表
CREATE TABLE success_killed(
	`seckill_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
	`user_phone` int(11) NOT NULL COMMENT '用户手机号',
	`state` int(2) NOT NULL COMMENT '状态标识：-1无效，0：成功，1：已付款，2：已发货',
	`create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`seckill_id`,`user_phone`),
	CONSTRAINT `seckill_id` FOREIGN KEY (`seckill_id`) REFERENCES `seckill` (`seckill_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';
