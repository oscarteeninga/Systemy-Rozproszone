import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Server extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    getSender().tell(getPrice(), getSelf());
                })
                .matchAny(o -> log.info("Indentyfikujemy produkty po nazwie!"))
                .build();
    }

    public int getPrice() {
        return new Shop().call();
    }


}
