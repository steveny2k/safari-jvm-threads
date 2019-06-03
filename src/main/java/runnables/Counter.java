package runnables;

public class Counter {
  private /*volatile*/ int count = 0;

  public static void main(String[] args) throws Throwable {
    final Counter c = new Counter();
    Runnable r = new Runnable() {
      public void run() {
        int x = 0;
        for (int i = 0; i < 1_000; i++) {
//        c.count++;
//          if (x % 10 == 0) System.out.print(".");
//          x++;
          synchronized (c) {
            int v = c.count;
            v++;
            try {
              Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
            c.count = v;
          }
        }
      }
    };
    new Thread(r).start();
    new Thread(r).start();
    System.out.println("counter started...");
    Thread.sleep(5_000);
    System.out.println("\nCount is " + c.count);
  }
}
