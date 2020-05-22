import akka.actor.AbstractLoggingActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.sql.Time;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
                        showPrice(price);
                    } catch (Exception e) {
                        log.info("Klient nie otrzymał ceny");
                        System.out.println("Cena nieznana");
                    }
                })
                .matchAny(o -> log.info("Niezrozumiały komunikat!"))
                .build();
    }

    private void showPrice(Integer price) {
        log.info("Klient otrzymał cenę " + price);
        System.out.println("Cena to " + price);
    }
}
