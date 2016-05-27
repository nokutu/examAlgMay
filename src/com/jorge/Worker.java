package com.jorge;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Worker extends Thread {

    private final PriorityBlockingQueue<Node> queue;
    private boolean finished;

    private Master master;

    public Worker(Master master, PriorityBlockingQueue<Node> queue) {
        this.queue = queue;
        this.master = master;
    }

    @Override
    public void run() {
        while (!finished) {
            Node n = null;
            while (n == null) {
                try {
                    n = queue.poll(10, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Node[] result = n.execute();
            // Finished
            if (result == null) {
                master.finish();
            } else {
                if (result[0] != null) {
                    queue.add(result[0]);
                }
                if (result[1] != null) {
                    queue.add(result[1]);
                }
            }
        }
    }

    public void finish() {
        finished = true;
    }
}
