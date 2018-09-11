package cn.swift.chapter2;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.swift.annotation.NotThreadSafe;

/**
 * 2-5 该Servlet在没有足够原子性保证的情况下对其最近计算结果进行缓存(不要这么做)
 * @author Swift Hu
 * @date 2018年9月11日 下午9:15:34
 */
@NotThreadSafe
public class UnsafeCachingFactorizer extends AbstractFactorizer{

	private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
	
	private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();
	
	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		if(i.equals(lastNumber.get())) {
			encodeIntoResponse(res, lastFactors.get());
		}else {
			BigInteger[] factors = factor(i);
			lastNumber.set(i);
			lastFactors.set(factors);
			encodeIntoResponse(res, factors);
		}
	}
	
}
