package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestThreadStop {

    static class Manageable extends Thread {

        // TODO: think of volatile, interrupt() or Atomic
        public static volatile boolean running = true;
        public static String str = "";

        @Override
        public void run() {

            while (running) {
                try {
                    str = str + "a";
                    synchronized (this) {
                        wait(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testThreadState() throws InterruptedException {

        Manageable thread = new Manageable();
        assertEquals(thread.getState(), Thread.State.NEW);
        thread.start();
        assertEquals(thread.getState(), Thread.State.RUNNABLE);

        //TODO: Employ TestThreadStop.Manageable.running = false inside of loop and stop thread when "aaa" is built
        //for (int i = 0; i < 100; i ++) {
        //}
        for (int i = 0; i < 100; i++) {
            Thread.sleep(120);
            if ("aaa".equals(Manageable.str)) {
                Manageable.running = false;
                break;
            }
        }

        System.out.println("Received : " + Manageable.str);
        assertEquals("aaa", Manageable.str);
    }
}
