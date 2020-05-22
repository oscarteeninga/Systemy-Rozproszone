import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import java.util.concurrent.*;

import static akka.actor.SupervisorStrategy.restart;

public class Server extends AbstractLoggingActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    static private String[] clients = {"client1", "client2", "client3"};

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    Future<Integer> price = Shop.genPrice(); // czas potrzebny na to to 4 ms
                    getSender().tell(price, getSelf());
                    log.info("Serwer otrzymał zapytanie o cenę " + s);
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
