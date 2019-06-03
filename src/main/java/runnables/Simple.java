package runnables;

class MyJob implements Runnable {
  private int i = 0;

  @Override
  public void run() {
    System.out.println(Thread.currentThread().getName() + " running...");
    for (; i < 10_000; i++) {
      System.out.println(Thread.currentThread().getName() + " i is " + i);
    }
    System.out.println(Thread.currentThread().getName() + " ending...");
  }
}

public class Simple {
  public static void main(String[] args) {
    System.out.println(Thread.currentThread().getName() + " running main");
    Runnable r = new MyJob();
    Thread t = new Thread(r);
    t.start();
    Thread t2 = new Thread(r);
    t2.start();
    System.out.println("main exiting");
  }
}
