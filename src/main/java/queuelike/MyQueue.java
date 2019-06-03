package queuelike;

public class MyQueue {
  private boolean hasData = false;
  private String theData = null;

  public void put(String s) throws InterruptedException {
    synchronized (this) {
      // must give back "key" to have any chance of this value being changed...
      while (hasData) { // POSSIBLE TO WAKE for wrong reasons MUST USE LOOP
        // MUST BE "Transactionally safe" when calling wait...
        this.wait(); // releases "key" and "goes to sleep with head on 'this' pillow"
        // on notify move from "blocked waiting for notification"
        // to "blocked waiting for key" MUST HOLD KEY TO EXECUTE in a synch block...
      }
      theData = s;
      hasData = true;
      this.notify();
    }
  }

  public String take() throws InterruptedException {
    synchronized (this) {
      while (!hasData) {
        this.wait();
      }

      this.notify(); // ODD!!! But safe..
      hasData = false;
      return theData;
    }
  }

  public static void main(String[] args) throws Throwable {
    MyQueue mq = new MyQueue();

    Thread prod = new Thread(()-> {
      for (int i = 0; i < 100; i++) {
        try {
//          Thread.sleep(1);
          if (i == 50) {
            mq.put("49"); // test the test....
          } else {
            mq.put("" + i);
          }
        } catch (InterruptedException e) {
          System.out.println("Early shutdown requested in producer...");
          return;
        }
      }
    });
    prod.start();

    Thread cons = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        try {
          Thread.sleep(1);
          String s = mq.take();
          if (!s.equals("" + i)) {
            System.out.println("BROKE at value " + s);
          }
        } catch (InterruptedException e) {
          System.out.println("Early shutdown requested in consumer...");
          return;
        }
      }
    });
    cons.start();
    prod.join();
    cons.join();
  }
}

