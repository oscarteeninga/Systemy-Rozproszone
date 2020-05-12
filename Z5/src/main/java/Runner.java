import org.apache.zookeeper.ZooKeeper;

import java.util.Scanner;

public class Runner {
    static String exec = "say Litwo! Ojczyzno moja! Ty jesteś jak zdrowie,\n" +
            "Ile cię trzeba cenić, ten tylko się dowie,\n" +
            "Kto cię stracił. Dziś piękność twą w całej ozdobie\n" +
            "Widzę i opisuję, bo tęsknię po tobie\n" +
            "Panno święta, co Jasnej bronisz Częstochowy\n" +
            "I w Ostrej świecisz Bramie! Ty, co gród zamkowy\n" +
            "Nowogródzki ochraniasz z jego wiernym ludem!\n" +
            "Jak mnie dziecko do zdrowia powróciłaś cudem,\n" +
            "(Gdy od płaczącej matki pod Twoją opiekę\n" +
            "Ofiarowany, martwą podniosłem powiekę\n" +
            "I zaraz mogłem pieszo do Twych świątyń progu\n" +
            "Iść za wrócone życie podziękować Bogu),\n" +
            "Tak nas powrócisz cudem na Ojczyzny łono.\n" +
            "Tymczasem przenoś moją duszę utęsknioną\n" +
            "Do tych pagórków leśnych, do tych łąk zielonych,\n" +
            "Szeroko nad błękitnym Niemnem rozciągnionych;\n" +
            "Do tych pól malowanych zbożem rozmaitem,\n" +
            "Wyzłacanych pszenicą, posrebrzanych żytem;\n" +
            "Gdzie bursztynowy świerzop, gryka jak śnieg biała,\n" +
            "Gdzie panieńskim rumieńcem dzięcielina pała,\n" +
            "A wszystko przepasane jakby wstęgą, miedzą\n" +
            "Zieloną, na niej z rzadka ciche grusze siedzą.";

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("USAGE: Executor hostPort znode filename program [args ...]");
            System.exit(2);
        }

        String hostPort = args[0];
        String znode = args[1];

        if (args.length > 2) {
            exec = args[2];
        }

        try {
            Executor executor = new Executor(znode, exec);
            ZooKeeper zk = new ZooKeeper(hostPort, 3000, null);
            ExecWatcher execWatcher = new ExecWatcher(zk, executor, znode);
            zk.exists(znode, execWatcher);
            Scanner reader = new Scanner(System.in);
            while (true) {
                reader.nextLine();
                executor.showChildrenTree(znode, zk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


