import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Runner {
    public static void main(String[] args) throws Exception {

        final ActorSystem system = ActorSystem.create("local_system");

        Server.getClients();

        final ActorRef actor = system.actorOf(Props.create(Server.class), "server");

        actor.tell(new String []{"add", "client1"}, ActorRef.noSender());
        actor.tell(new String []{"add", "client2"}, ActorRef.noSender());

        System.out.println("Start...");
        System.out.println("Protokół zapytania o cenę: <nazwa_klienta>:<nazwa_produktu>");
        System.out.println("Protokół dodania clienta: add:<nazwa_klienta>");

        Database.getInstance();


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            } else if (line.equals("all")) {
                Database.getInstance().showAll();
            } else if (line.equals("show")) {
                for (String client : Server.getClients()) {
                    System.out.print(client + " ");
                }
                System.out.println("");
            } else if (!line.equals("")){
                actor.tell(line.split(":"), ActorRef.noSender());
            }
        }

        system.terminate();
    }
}
