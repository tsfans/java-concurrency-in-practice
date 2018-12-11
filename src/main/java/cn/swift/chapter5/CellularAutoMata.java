package cn.swift.chapter5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 通过CyclicBarrier协调细胞自动衍生的系统中的计算
 */
public class CellularAutoMata {

    private final Board minBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutoMata(Board board) {
	this.minBoard = board;
	int count = Runtime.getRuntime().availableProcessors();
	this.barrier = new CyclicBarrier(count, new Runnable() {
	    @Override
	    public void run() {
		minBoard.commitNewValues();
	    }
	});
	this.workers = new Worker[count];
	for (int i = 0; i < count; i++) {
	    this.workers[i] = new Worker(minBoard.getSubBoard(count, i));
	}
    }

    public void start() {
	for (int i = 0; i < workers.length; i++) {
	    new Thread(workers[i]).start();
	    minBoard.waitForConvergence();
	}
    }

    private class Worker implements Runnable {
	private final Board board;

	public Worker(Board board) {
	    this.board = board;
	}

	private int computeValue(int x, int y) {
	    // compute new value that goes in (x, y)
	    return 0;
	}

	@Override
	public void run() {
	    while (!board.hasConverged()) {
		for (int i = 0; i < board.getMaxX(); i++) {
		    for (int j = 0; j < board.getMaxY(); j++) {
			board.setNewValue(i, j, computeValue(i, j));
			try {
			    barrier.await();
			} catch (InterruptedException e) {
			    e.printStackTrace();
			    return;
			} catch (BrokenBarrierException e) {
			    e.printStackTrace();
			    return;
			}
		    }
		}
	    }
	}
    }

}

interface Board {
    int getMaxX();

    int getMaxY();

    void setNewValue(int x, int y, int result);

    boolean hasConverged();

    void commitNewValues();

    Board getSubBoard(int count, int i);

    void waitForConvergence();
}
