package runnables;

public class Visibility {
  static volatile boolean stop = false;

  public static void main(String[] args) throws Throwable {
    new Thread(() -> {
      System.out.println(Thread.currentThread().getName() + " starting");
      while (! stop)
        ;
      System.out.println(Thread.currentThread().getName() + " ending");
    }).start();
    Thread.sleep(1_000);
    System.out.println("main woke up, about to set stop to true.");
    stop = true;
    System.out.println("Main exiting...");
  }
}
