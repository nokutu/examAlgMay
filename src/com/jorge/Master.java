package com.jorge;

import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Master extends Thread {

    private final static int NUMBER_OF_WORKERS = 4;

    private final PriorityBlockingQueue<Node> queue;
    private Worker[] workers;

    private boolean finished;

    public Master() {
        queue = new PriorityBlockingQueue<>();
    }

    @Override
    public void run() {
        workers = new Worker[4];
        for (int i = 0; i < NUMBER_OF_WORKERS; i++) {
            workers[i] = new Worker(this, queue);
            workers[i].start();
        }
        while (!finished) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < NUMBER_OF_WORKERS; i++) {
            workers[i].finish();
        }
    }

    public void addRoot(Node n) {
        synchronized (queue) {
            queue.add(n);
        }
    }

    /**
     * Called when some worker founds a solution. Stops all the workers and the master thread itself.
     */
    public void finish() {
        finished = true;
    }
}
