import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.Random;
import java.util.concurrent.Callable;

public class Shop implements Callable {


    @Override
    public Integer call() throws {
        return genPrice();
    }

    private Integer genPrice() {
        try {
            Thread.sleep(new Random().nextInt() % 400 + 100); // uśpienie wątku na 100-500ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            return new Random().nextInt() % 10 + 1;
        }
    }
}
