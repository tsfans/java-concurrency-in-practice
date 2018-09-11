package cn.swift.chapter2;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.swift.annotation.ThreadSafe;

/**
 * 2-1 一个无状态的Servlet
 * @author Swift Hu
 * @date 2018年9月10日 下午2:01:06 
 */
@ThreadSafe
public class StatelessFactorizer extends AbstractFactorizer {

	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		BigInteger[] factors = factor(i);
		encodeIntoResponse(resp, factors);
	}

}
