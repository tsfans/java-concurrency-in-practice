package cn.swift.chapter8.puzzle;

import java.util.LinkedList;
import java.util.List;

import cn.swift.annotation.Immutable;

/**
 * 8-14 用于谜题解决框架的链表节点
 */
@Immutable
public class PuzzleNode<P, M> {

    final P p;

    final M m;

    final PuzzleNode<P, M> prev;

    public PuzzleNode(P p, M m, PuzzleNode<P, M> prev) {
        this.p = p;
        this.m = m;
        this.prev = prev;
    }

    public List<M> asMoveList() {
        List<M> solution = new LinkedList<>();
        for (PuzzleNode<P, M> n = this; n.m != null; n = n.prev) {
            solution.add(0, n.m);
        }
        return solution;
    }
}
