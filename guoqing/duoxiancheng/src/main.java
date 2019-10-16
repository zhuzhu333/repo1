/**
 * @author
 * @description 多线程
 * @return
 * @throws
 * @date 2019/10/4 10:07
 * @since
 */
public class main {
    public static void main(String[] args) {
        //获取主线程对象
        Thread t=Thread.currentThread();
        System.out.println("当前线程是："+t.getName());
        t.setName("myJavaThread");
        System.out.println("当期线程是"+t.getName());
    }
}
