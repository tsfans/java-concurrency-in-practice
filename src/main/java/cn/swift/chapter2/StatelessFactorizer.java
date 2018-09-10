package cn.swift.chapter2;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * @author Swift Hu
 * @date 2018年9月10日 下午2:01:06
 */
public class StatelessFactorizer implements Servlet{

	@Override
	public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		BigInteger i = extractFromRequest(req);
		BigInteger[] factors = factor(i);
		encodeIntoResponse(resp, factors);
	}
	
	private void encodeIntoResponse(ServletResponse resp, BigInteger[] factors) {
		
	}

	private BigInteger[] factor(BigInteger i) {
		return new BigInteger[] {i};
	}

	private BigInteger extractFromRequest(ServletRequest req) {
		return new BigInteger("4");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	

}
