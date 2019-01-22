package cn.swift.chapter8.puzzle;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

/**
 * 8-16 并发的谜题解答器
 */
public class ConcurrentPuzzleSolver<P, M> {

    private final Puzzle<P, M> puzzle;

    private final ExecutorService exec;

    private final ConcurrentMap<P, Boolean> seen;

    final ValueLatch<PuzzleNode<P, M>> solution = new ValueLatch<>();

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec, ConcurrentMap<P, Boolean> seen) {
        this.puzzle = puzzle;
        this.exec = exec;
        this.seen = seen;
    }

    public List<M> solve() throws InterruptedException {
        P p = puzzle.initialPosition();
        exec.execute(newTask(p, null, null));
        // 阻塞直到找到答案
        PuzzleNode<P, M> solNode = solution.getValue();
        return solNode == null ? null : solNode.asMoveList();
    }

    protected Runnable newTask(P p, M m, PuzzleNode<P, M> node) {
        return new SolverTask(p, m, node);
    }

    class SolverTask extends PuzzleNode<P, M> implements Runnable {

        public SolverTask(P p, M m, PuzzleNode<P, M> prev) {
            super(p, m, prev);
        }

        @Override
        public void run() {
            if (solution.isSet() || seen.putIfAbsent(p, true) != null) {
                // 已经找到答案或已经遍历过这个位置
                return;
            }
            if (puzzle.isGoal(p)) {
                solution.setValue(this);
            } else {
                for (M move : puzzle.legalMoves(p)) {
                    exec.execute(newTask(puzzle.move(p, move), move, this));
                }
            }
        }

    }
}
