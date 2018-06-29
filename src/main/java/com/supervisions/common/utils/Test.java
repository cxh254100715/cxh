package com.supervisions.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class Test
{
    /*public static void main(String[] args) {
        String url = "http://localhost:8081/app/updateProfile";
        String str = "id=11&nickname=啦啦啦1";
        String result = RequestUtils.sendPost(url, str);
        System.out.println(result);
    }*/

    final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args)
    {
        CountDownLatch latch = new CountDownLatch(5);//模拟5人并发请求

        for (int i = 0; i < 5; i++)
        {//模拟5个用户
            System.out.println("strat");
            User analogUser = new User(11, "a" + i, latch);
            analogUser.start();
        }
        latch.countDown();//计数器減一  所有线程释放 并发访问。
        System.out.println("所有模拟请求结束  at " + sdf.format(new Date()));

    }

    static class User extends Thread
    {
        Integer id;
        String nickname;
        CountDownLatch latch;

        public User(Integer id, String nickname,
                          CountDownLatch latch)
        {
            this.id = id;
            this.nickname = nickname;
            this.latch = latch;
        }

        @Override
        public void run()
        {
            try
            {
                latch.await(); //一直阻塞当前线程，直到计时器的值为0
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            post();//发送post 请求
        }

        public void post()
        {
            System.out.println("模拟用户： " + nickname + " 开始发送模拟请求  at " + sdf.format(new Date()));

            String url = "http://localhost:8081/app/updateProfile";
            String str = "id=11&nickname=" + nickname;

            String result = RequestUtils.sendPost(url, str);

            System.out.println("操作结果：" + result);
            System.out.println("模拟用户： " + nickname + " 模拟请求结束  at " + sdf.format(new Date()));
        }
    }
}
