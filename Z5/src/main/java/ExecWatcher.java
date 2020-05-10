import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ExecWatcher implements Watcher {

    Executor executor;
    String znode;
    ChildWatcher childWatcher;
    ZooKeeper zk;

    public ExecWatcher(ZooKeeper zk, Executor executor, String znode) throws KeeperException, InterruptedException {
        this.executor = executor;
        this.childWatcher = new ChildWatcher(zk, executor, znode);
        this.znode = znode;
        this.zk = zk;
    }

    public void process(WatchedEvent event) {
        try {
            System.out.println(event.getType());
            switch (event.getType()) {
                case NodeCreated:
                    zk.getChildren(znode, childWatcher);
                    executor.exists();
                    break;
                case NodeDeleted:
                    executor.closing();
                    break;
                default:
                    break;
            }
            zk.exists(znode, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
