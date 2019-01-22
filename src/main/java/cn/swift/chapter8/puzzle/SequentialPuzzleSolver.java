package cn.swift.chapter8.puzzle;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 8-15 串行的谜题解答器
 */
public class SequentialPuzzleSolver<P, M> {

    private final Puzzle<P, M> puzzle;

    private final Set<P> seen = new HashSet<>();

    public SequentialPuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
    }

    public List<M> solve() {
        P pos = puzzle.initialPosition();
        return search(new PuzzleNode<P, M>(pos, null, null));
    }

    private List<M> search(PuzzleNode<P, M> node) {
        if (!seen.contains(node.p)) {
            seen.add(node.p);
            if (puzzle.isGoal(node.p)) {
                return node.asMoveList();
            }
            for (M move : puzzle.legalMoves(node.p)) {
                P pos = puzzle.move(node.p, move);
                PuzzleNode<P, M> child = new PuzzleNode<>(pos, move, node);
                List<M> result = search(child);
                if (result != null) {
                    return result;
                }
            }
        }
        return Collections.emptyList();
    }
}
