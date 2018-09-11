package cn.swift.chapter2;

import cn.swift.annotation.NotThreadSafe;
import cn.swift.bean.ExpensiveObject;

/**
 * 2-3 延迟初始化中竞态条件(不要这么做)
 * @author Swift Hu
 * @date 2018年9月11日 下午8:58:58
 */
@NotThreadSafe
public class LazyInitRace {
	
	private ExpensiveObject instance = null;
	
	public ExpensiveObject getInstance() {
		if(instance == null) {
			instance = new ExpensiveObject();
		}
		return instance;
	}
}
