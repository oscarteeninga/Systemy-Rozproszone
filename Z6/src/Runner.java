import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Runner {
    public static void main(String[] args) throws Exception {

        final ActorSystem system = ActorSystem.create("local_system");
        final ActorRef actor = system.actorOf(Props.create(Server.class), "server");

        System.out.println("Start...");
        System.out.println("Protokół: <nazwa_klienta>:<nazwa_produktu>");
        System.out.print("Klienci: ");
        for (String client: Server.getClients()) {
            System.out.print(client + ", ");
        }
        System.out.println("");
        Database.getInstance();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            } else if (line.equals("all")) {
                Database.getInstance().showAll();
            } else {
                actor.tell(line.split(":"), ActorRef.noSender());
            }
        }

        system.terminate();
    }
}
