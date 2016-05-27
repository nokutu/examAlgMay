package com.jorge;

import java.util.HashSet;

/**
 * Represents each of the states of the Branch and Bound tree.
 */
public class Node implements Comparable<Node> {

    private final static HashSet<Integer> states = new HashSet<>();

    private int priority;

    private int from;
    private int to;
    private int steps;
    private Node father;

    public Node(int from, int to, int steps, int priority, Node father) {
        this.from = from;
        this.to = to;
        this.steps = steps;
        this.priority = priority;
        this.father = father;
    }

    public Node[] execute() {
        Node a = null;
        Node b = null;
        synchronized (states) {
            if (!states.contains(from/2)) {
                a = new Node(from/2, to, steps+1, steps * 10, this);
                states.add(from/2);
            }
            if (!states.contains(from * 3)) {
                b = new Node(from*3, to, steps+1, steps * 10, this);
                states.add(from*3);
            }
        }
        if (from / 2 == to || from * 3 == to) {
            if ( Main.SolutionFound()) {
                System.out.println("Length: " + (steps + 1));
                System.out.println("Chain: " + getHistory() + " " + to);
            }
            return null;
        }
        return new Node[]{a, b};
    }

    @Override
    public int compareTo(Node o) {
        return this.priority - o.priority;
    }

    private String getHistory() {
        if (father != null) {
            return father.getHistory() + " " + from;
        }
        return "" + from;
    }
}
