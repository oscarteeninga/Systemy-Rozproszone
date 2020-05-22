import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Shop {

    static public Future genPrice() {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(new Random().nextInt() % 400 + 100);
            completableFuture.complete(Math.abs(new Random().nextInt() % 10) +  1);
            return null;
        });
        return completableFuture;
    }

}
