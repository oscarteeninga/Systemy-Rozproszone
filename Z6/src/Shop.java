import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Math.abs;

public class Shop {

    static public Future genPrice() {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        Executors.newCachedThreadPool().submit(() -> {
            int waiting = abs(new Random().nextInt()) %  400 + 100;
            System.out.println("Czas oczekiwania sklepu " + waiting + " ms");
            Thread.sleep(waiting);
            completableFuture.complete(abs(new Random().nextInt() % 10) +  1);
            return null;
        });
        return completableFuture;
    }
}
