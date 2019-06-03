package runnables;

import java.util.concurrent.ThreadLocalRandom;

public class Interrupt {
  private static boolean setMe = false;
  private static boolean setMeToo = false;
  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      System.out.println("Hello!");
      while (true) {
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          System.out.println("Value of setMeToo is " + setMeToo); // hb because of interrupt
          setMe = true;
          System.out.println("Thread exiting...");
          return;
        }
      }
    });
    t1.start();
    Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 2_000));
    setMeToo = true;
    t1.interrupt();
    t1.join(); // allows me to see setMe
    System.out.println("Set me is " + setMe);
  }
}
