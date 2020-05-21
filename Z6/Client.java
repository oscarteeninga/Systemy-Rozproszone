import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Client extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(int.class, n -> {
                    System.out.println("Cena produktu: " + n);
                })
                .matchAny(o -> log.info("Niezrozumiały komunikat!"))
                .build();
    }
}
