package package1;

/**
 * @author
 * @description
 * @return
 * @throws
 * @date 2019/10/4 16:08
 * @since
 */
public class Site implements Runnable{

    private int count=10;//记录余票数
    private int num=0;//记录当前抢到第几张票
    private boolean flag=false;//记录票是否售完
    public void run(){

        while (!flag){
            sale();
        }
    }
    //同步方法。售票
    public synchronized void sale(){
        if(count<=0){
            flag=true;
            return;
        }
        //修改票数，抢到第几张票
        count--;
        num++;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //显示信息，返回用户抢到第几张票
        System.out.println(Thread.currentThread().getName()+"抢到第"+num+"张票，剩余"+count+"张票");
    }


}
