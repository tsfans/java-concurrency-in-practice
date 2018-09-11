package cn.swift.annotation;

/**
 * 声明锁类型
 * @author Swift Hu
 * @date 2018年9月11日 下午9:35:55
 */
public @interface GuardedBy {

	String value() default "this";
}
