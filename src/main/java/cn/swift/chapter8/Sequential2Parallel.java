package cn.swift.chapter8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.w3c.dom.Element;

public class Sequential2Parallel<T> {

    /**
     * 8-11 将串行递归转换为并行递归
     */
    void processSequential(List<Element> elements) {
        for (Element e : elements) {
            process(e);
        }
    }

    void processParallel(List<Element> elements, Executor exec) {
        for (final Element e : elements) {
            exec.execute(() -> process(e));
        }
    }

    void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
        for (Node<T> node : nodes) {
            results.add(node.compute());
            sequentialRecursive(node.getChildren(), results);
        }
    }

    void parallelRecursive(List<Node<T>> nodes, Collection<T> results, Executor exec) {
        for (final Node<T> node : nodes) {
            exec.execute(() -> results.add(node.compute()));
            parallelRecursive(node.getChildren(), results, exec);
        }
    }

    /**
     * 8-12 等待通过并行方式计算的结果
     */
    Collection<T> getParallelRecursiveResults(List<Node<T>> nodes) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Queue<T> resultQueue = new ConcurrentLinkedQueue<>();
        parallelRecursive(nodes, resultQueue, exec);
        exec.shutdown();
        exec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        return resultQueue;
    }

    private void process(Element e) {
    }

    class Node<E> {
        E t;
        Node<E> child;

        public Node(E t, Node<E> child) {
            this.t = t;
            this.child = child;
        }

        public E compute() {
            return t;
        }

        public List<Node<E>> getChildren() {
            List<Node<E>> nodes = new ArrayList<>();
            nodes.add(child);
            return nodes;
        }
    }
}
