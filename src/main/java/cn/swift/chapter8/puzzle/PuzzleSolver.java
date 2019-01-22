package cn.swift.chapter8.puzzle;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 8-18 在解答器中找不到解答
 */
public class PuzzleSolver<P, M> extends ConcurrentPuzzleSolver<P, M> {

    private final AtomicInteger taskCount = new AtomicInteger(0);

    public PuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec, ConcurrentMap<P, Boolean> seen) {
        super(puzzle, exec, seen);
    }

    @Override
    protected Runnable newTask(P p, M m, PuzzleNode<P, M> node) {
        return new CountingSolverTask(p, m, node);
    }

    class CountingSolverTask extends SolverTask {

        public CountingSolverTask(P p, M m, PuzzleNode<P, M> prev) {
            super(p, m, prev);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                if (taskCount.decrementAndGet() == 0) {
                    solution.setValue(null);
                }
            }
        }

    }

}
