/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/4 10:52
 * @since
 */
public class Test1 {
    public static void main(String[] args) {
        //1.创建线程对象
        Runnable myRunnable =new MyRunnable();
        Thread t=new Thread(myRunnable,"MyThread:");
        t.start();//启动线程
    }
}
