package cn.swift.chapter2;

import java.math.BigInteger;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Swift Hu
 * @date 2018年9月11日 下午9:20:14
 */
public abstract class AbstractFactorizer implements Servlet{

	
	protected void encodeIntoResponse(ServletResponse res, BigInteger[] factors) {

	}

	protected BigInteger[] factor(BigInteger i) {
		return new BigInteger[] { i };
	}

	protected BigInteger extractFromRequest(ServletRequest req) {
		return new BigInteger("1");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
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
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
