package cn.swift.chapter5;

import java.io.IOException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import cn.swift.annotation.ThreadSafe;
import cn.swift.chapter2.AbstractFactorizer;
import cn.swift.chapter5.cache.Computable;
import cn.swift.chapter5.cache.Memorizer;

/**
 * 5-20 在因式分解Servlet中使用Memorizer来缓存结果
 */
@ThreadSafe
public class CacheFactorizer extends AbstractFactorizer {

    private final Computable<BigInteger, BigInteger[]> c = new Computable<BigInteger, BigInteger[]>() {
	@Override
	public BigInteger[] compute(BigInteger arg) throws InterruptedException {
	    return factor(arg);
	}
    };

    private final Memorizer<BigInteger, BigInteger[]> cache = new Memorizer<>(c);

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
	try {
	    BigInteger i = extractFromRequest(req);
	    encodeIntoResponse(res, cache.compute(i));
	} catch (InterruptedException e) {
	    encodeError(res, "factorization is interrupted");
	}
    }

    private void encodeError(ServletResponse res, String string) {

    }

}
