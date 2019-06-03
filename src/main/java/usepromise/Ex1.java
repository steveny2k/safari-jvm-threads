package usepromise;

import java.util.concurrent.CompletableFuture;

public class Ex1 {
  public static CompletableFuture<String> readFile(String name) {
    CompletableFuture<String> retVal = new CompletableFuture<>();
    System.out.println("Background job about to start...");
    new Thread(() -> {
      try {
        Thread.sleep(1000);
        String data = "I read from the file " + name + " this text...";
        retVal.complete(data);
      } catch (InterruptedException ie) {

      }
    }).start();
    System.out.println("Background job under way...");
    return retVal;
  }
  public static void main(String[] args) {
    CompletableFuture<Void> cfv = CompletableFuture
        .supplyAsync(() -> "Fred")
        .thenApply(s -> s.toUpperCase())
        .thenCompose(s -> readFile(s))
        .thenApply(s -> s.toUpperCase())
        .thenAccept(s -> System.out.println("The result is:\n" + s));
    System.out.println("Pipeline built");
    cfv.join();
    System.out.println("Looks like pipeline completed");
  }
}
