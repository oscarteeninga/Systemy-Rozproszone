import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.*;

import static akka.actor.SupervisorStrategy.restart;

public class Server extends AbstractLoggingActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static private ArrayList<String> clients = new ArrayList<>();

    class PriceMessage {
        Future<Integer> price;
        Future<Integer> count;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    log.info("Serwer otrzymał zapytanie o cenę " + s);
                    PriceMessage priceMessage = new PriceMessage();
                    priceMessage.price = getPrice();
                    priceMessage.count = getQuestionCount(s);
                    getSender().tell(priceMessage, getSelf());
                    logToDatabase(s);
                })
                .match(String[].class, s -> {
                    if (s[0].equals("add")) {
                        addClient(s[1]);
                        log.info(s[1] + " dodany");
                    } else {
                        context().child(s[0]).get().tell(s[1],getSelf());
                    }
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

    public void addClient(String client) {
        context().actorOf(Props.create(Client.class), client);
        clients.add(client);
    }

    private CompletableFuture getQuestionCount(String question) {
        CompletableFuture countRequest = new CompletableFuture();
        Executors.newCachedThreadPool().submit(() -> {
            try {
                countRequest.complete(Database.getInstance().getQuestionCount(question));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return countRequest;
    }

    private void logToDatabase(String question) {
        Executors.newCachedThreadPool().submit(() -> {
            try {
                Database.getInstance().increase(question);
            } catch (Exception e) {}
        });
    }

    static public ArrayList<String> getClients() {
        return clients;
    }

    private Future getPrice() {
        CompletableFuture<Integer> price = new CompletableFuture<>();

        class Prices {
            volatile int price1 = 11;
            volatile int price2 = 11;

            public boolean checkAnyGotten() {
                return price1 + price2 < 22;
            }

            public int getPrice() {
                System.out.println(price1 + " " + price2);
                return Math.min(price1, price2);
            }
        }

        Prices prices = new Prices();

        Executors.newCachedThreadPool().submit(() -> {
            try {
                prices.price1 = (int) Shop.genPrice().get();
            } catch (Exception e) {}
        });

        Executors.newCachedThreadPool().submit(() -> {
            try {
                prices.price2 = (int) Shop.genPrice().get();
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
