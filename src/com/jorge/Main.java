package com.jorge;

public class Main {

    private static boolean solutionFound = false;
    private static long startTime;

    public static void main(String[] args) throws InterruptedException {
        Calculate(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        //Calculate(1000, 10000);
    }

    public static void Calculate(int a, int b) {
        Master m = new Master();
        Node root = new Node(a, b, 0, 0, null);
        m.addRoot(root);

        startTime = System.currentTimeMillis();
        m.start();
        try {
            m.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method used to about multiple solutions arriving at the same time.
     *
     * @return true if it is the first solution arriving
     */
    public static boolean SolutionFound() {
        synchronized (Main.class) {
            if (!solutionFound) {
                solutionFound = true;
                long endTime = System.currentTimeMillis();
                System.out.println("Solution found, took: " + (endTime - startTime) + "ms");
                return true;
            }
            return false;
        }
    }
}
