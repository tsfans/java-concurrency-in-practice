package cn.swift.chapter3;

/**
 * 3-15 由于未被正常发布，这个类可能会出现问题
 */
public class UnsafePublish {

    /**
     * 不安全的发布
     */
    public Holder holder;

    public void init() {
	holder = new Holder(22);
    }

    class Holder {
	private int n;

	public Holder(int n) {
	    this.n = n;
	}

	public void assertSanity() {
	    if (n != n) {
		throw new AssertionError("This statement is false.");
	    }
	}
    }
}
