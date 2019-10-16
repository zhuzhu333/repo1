package package1;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/4 16:21
 * @since
 */
public class Test {
    public static void main(String[] args) {
        Site site=new Site();
        Thread person1=new Thread(site,"逃跑跑");
        Thread person2=new Thread(site,"黄牛党");
        Thread person3=new Thread(site,"抢票代理");
        person1.start();
        person2.start();
        person3.start();
    }
}
