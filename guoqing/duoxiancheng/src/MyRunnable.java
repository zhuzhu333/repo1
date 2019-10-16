/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/4 10:48
 * @since
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100;i++) {
            System.out.println(Thread.currentThread().getName()+":"+i);

        }
    }
}
