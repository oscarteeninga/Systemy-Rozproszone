import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class Executor {

    String znode;
    String exec;
    Process child;
    List<String> nodes = new ArrayList<String>();

    public Executor(String znode, String exec) {
        this.exec = exec;
        this.znode = znode;
    }

    public void showChildrenTree(String znode, ZooKeeper zk) {
        System.out.println("=== CHILDREN ===");
        for (String node : getChildrenList(znode, zk)) {
            System.out.println(node);
        }
        System.out.println("COUNT: " + getChildrenList(znode, zk).size());
        System.out.println("================");
        nodes = getChildrenList(znode, zk);
    }

    public List<String> getChildrenList(String znode, ZooKeeper zk) {
        List<String> result = new ArrayList<String>();
        try {
            if (doesNodeExist(znode, zk)) {
                List<String> dir = zk.getChildren(znode, false);
                for (String node : dir) {
                    result.add(znode + "/" + node);
                    result.addAll(getChildrenList(znode + "/" + node, zk));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public boolean doesNodeExist(String znode, ZooKeeper zk) throws KeeperException, InterruptedException {
        return zk.exists(znode, false) != null;
    }

    public int getChildrenCount(String znode, ZooKeeper zk) {
        return getChildrenList(znode, zk).size();
    }

    public void closing() {
        synchronized (this) {
            System.out.println("Stopping exec");
            if (child != null) child.destroy();
        }
    }

    public void exists() {
        try {
            System.out.println("Starting exec");
            child = Runtime.getRuntime().exec(exec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
