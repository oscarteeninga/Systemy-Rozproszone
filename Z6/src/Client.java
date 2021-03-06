import akka.actor.AbstractLoggingActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client extends AbstractLoggingActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    log.info("Klient wysłał zapytanie produkt " + s);
                    getSender().tell(s, getSelf());
                })
                .match(Server.PriceMessage.class, msg -> {
                    Executors.newCachedThreadPool().submit(() -> {
                        try {
                            Integer price = msg.price.get(300, TimeUnit.MILLISECONDS);
                            // log.info("Klient otrzymał cenę " + price);
                            System.out.println("Cena to " + price);
                        } catch (Exception e) {
                            // log.info("Klient nie otrzymał ceny");
                            System.out.println("Cena nieznana");
                        }
                    });
                    try {
                        Integer count = msg.count.get(300, TimeUnit.MILLISECONDS);
                        // log.info("Liczba zapytań to " + count);
                        System.out.println("Liczba zapytań to " + count);
                    } catch (Exception e) {
                        // log.info("Nie udało się uzyskać liczby zapytań");
                        System.out.println("Nieznana liczba zapytań");
                    }
                })
                .matchAny(o -> log.info("Niezrozumiały komunikat!"))
                .build();
    }
}
