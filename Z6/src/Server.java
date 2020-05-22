import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import java.util.Random;
import java.util.concurrent.*;

import static akka.actor.SupervisorStrategy.restart;

public class Server extends AbstractLoggingActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static private String[] clients = {"client1", "client2", "client3"};

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    Future price = getPrice();
                    log.info("Serwer otrzymał zapytanie o cenę " + s);
                    getSender().tell(price, getSelf());
                })
                .match(String[].class, s -> {
                    context().child(s[0]).get().tell(s[1],getSelf());
                })
                .matchAny(o -> log.warning("Indentyfikujemy produkty po nazwie"))
                .build();
    }

    @Override
    public void preStart() {
        for (String client: clients) {
            context().actorOf(Props.create(Client.class), client);
        }
    }

    static public String[] getClients() {
        return clients;
    }

    private Future getPrice() {
        CompletableFuture<Integer> price = new CompletableFuture<>();

        class Prices {
            int price1 = 11;
            int price2 = 11;

            public boolean checkAnyGotten() {
                return price1 + price2 < 22;
            }

            public int getPrice() {
                System.out.println(price1 + " " + price2);
                return Math.min(price1, price2);
            }
        }

        Prices prices = new Prices();
        Future price1 = Shop.genPrice();
        Future price2 = Shop.genPrice();

        Executors.newCachedThreadPool().submit(() -> {
            try {
                prices.price1 = (int) price1.get();
            } catch (Exception e) {}
        });

        Executors.newCachedThreadPool().submit(() -> {
            try {
                prices.price2 = (int) price2.get();
            } catch (Exception e) {}
        });

        Executors.newCachedThreadPool().submit(() -> {
            try {
                Thread.sleep(295);
                if (prices.checkAnyGotten()) {
                    price.complete(prices.getPrice());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return price;
    }



    private static SupervisorStrategy strategy
            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder.
            match(IllegalArgumentException.class, o -> restart()).
            matchAny(o -> restart()).
            build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}
