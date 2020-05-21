import org.apache.zookeeper.ZooKeeper;

import java.util.Scanner;

public class Runner {
    static String exec = "open -a calculator";

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


