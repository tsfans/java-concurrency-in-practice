package cn.swift.chapter2;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.swift.annotation.GuardedBy;
import cn.swift.annotation.ThreadSafe;

/**
 * 2-6 这个Servlet能正确地缓存最新的计算结果,但并发性非常糟糕(不要这么做)
 * @author Swift Hu
 * @date 2018年9月11日 下午9:33:36
 */
@ThreadSafe
public class SynchronizedFactorizer extends AbstractFactorizer{

	@GuardedBy("this")
	private BigInteger lastNumber;
	@GuardedBy("this")
	private BigInteger[] lastFactors;
	
	@Override
	public synchronized void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		if(i.equals(lastNumber)) {
			encodeIntoResponse(res, lastFactors);
		} else {
			BigInteger[] factors = factor(i);
			lastNumber = i;
			lastFactors = factors;
			encodeIntoResponse(res, factors);
		}
	}

}
