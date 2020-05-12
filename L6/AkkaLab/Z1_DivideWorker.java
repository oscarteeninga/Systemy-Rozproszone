import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z1_DivideWorker extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private int count = 0;

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    String result = Divide(s);
                    getSender().tell("result: " + result + " count: " + ++count, getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private String Divide(String s) throws IllegalArgumentException {
        String[] split = s.split(" ");
        float a = Float.parseFloat(split[1]);
        float b = Float.parseFloat(split[2]);
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        } else {
            return (a / b) + "";
        }
    }
}
