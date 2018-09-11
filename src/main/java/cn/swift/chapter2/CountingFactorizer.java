package cn.swift.chapter2;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.swift.annotation.ThreadSafe;

/**
 * 2-4 使用AtomicLong类型的变量来统计已处理请求的数量
 * @author Swift Hu
 * @date 2018年9月11日 下午9:04:07 
 */
@ThreadSafe
public class CountingFactorizer extends AbstractFactorizer {

	private final AtomicLong count = new AtomicLong(0);

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		BigInteger[] factors = factor(i);
		//保证操作原子性
		count.incrementAndGet();
		encodeIntoResponse(res, factors);
	}

}
