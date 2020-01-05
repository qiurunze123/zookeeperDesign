import com.zkdesign.zookeeper.Agent;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author qiurunze
 **/
public class AgentTest {

    @Test
    @Ignore
    public void initTest() {
        Agent.premain(null, null);
        runCPU(2); //20% 占用
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //
    private void runCPU(int count) {
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                while (true) {
                    long bac = 1000000;
                    bac = bac >> 1;
                }
            }).start();
            ;
        }
    }
}
