package cn.swift.chapter3;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.swift.chapter2.AbstractFactorizer;

/**
 * 3-13 使用指向不可变容器对象的volatile类型的引用来缓存最新的结果
 */
public class VolatileCachedFactorizer extends AbstractFactorizer {

    private volatile OneValueCache cache = new OneValueCache(null, null);

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
	BigInteger i = extractFromRequest(req);
	BigInteger[] factors = cache.getFactors(i);
	if (factors == null) {
	    factors = factor(i);
	    cache = new OneValueCache(i, factors);
	}
	encodeIntoResponse(res, factors);
    }

}
