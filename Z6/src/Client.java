import akka.actor.AbstractLoggingActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.concurrent.Future;
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
                .match(Future.class, f -> {
                    try {
                        Integer price = (Integer) f.get(300, TimeUnit.MILLISECONDS);
                        log.info("Klient otrzymał cenę " + price);
                        System.out.println("Cena to " + price);
                    } catch (Exception e) {
                        log.info("Klient nie otrzymał ceny");
                        System.out.println("Cena nieznana");
                    }
                })
                .match(Server.CountRequest.class, r -> {
                    int count = r.count.get() + 1;
                    System.out.println("Liczba zapytań: " + count);
                })
                .matchAny(o -> log.info("Niezrozumiały komunikat!"))
                .build();
    }
}
