/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/4 10:36
 * @since
 */
public class Test {
    public static void main(String[] args) {
        MyThread t1=new MyThread();
        MyThread t2=new MyThread();
        //启动线程
       /* t1.start();
        t2.start();*/
        t1.run();
        t2.run(); //只有主线程一条路径，而start可以有不同的线程
    }
}
