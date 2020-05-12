import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import static org.apache.zookeeper.Watcher.Event.EventType.NodeChildrenChanged;

import java.util.List;

public class ChildWatcher implements Watcher {

    Executor executor;
    String znode;
    ZooKeeper zk;

    public ChildWatcher(ZooKeeper zk, Executor executor, String znode) throws KeeperException, InterruptedException {
        this.executor = executor;
        this.znode = znode;
        this.zk = zk;
        signWatcher(znode);
    }

    public void process(WatchedEvent event) {
        try {
            signWatcher(znode);
            if (event.getType().equals(NodeChildrenChanged)) {
                System.out.println("Count of childrens: " + executor.getChildrenCount(znode, zk));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void signWatcher(String node) throws KeeperException, InterruptedException {
        if (executor.doesNodeExist(node, zk)) {
            List<String> children = zk.getChildren(node, this);
            for (String child : children) {
                signWatcher(node + "/" + child);
            }
        }
    }
}