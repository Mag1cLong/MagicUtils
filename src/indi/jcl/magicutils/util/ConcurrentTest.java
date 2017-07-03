package indi.jcl.magicutils.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 并发测试类
 * Created by Magic Long on 2016/11/17.
 */
public class ConcurrentTest {
    private static int thread_num = 10;// 并发数
    private static int client_num = 200;// 请求总数
    private static long total = 0;
    private  static List<Long> record = new ArrayList<>();



    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ExecutorService exec = Executors.newCachedThreadPool();
        // thread_num个线程可以同时访问
        final Semaphore semp = new Semaphore(thread_num);
        // 模拟client_num个客户端访问
        for (int index = 0; index < client_num; index++) {
            exec.execute(new TaskThread(semp));
        }
        long timeSpend = System.currentTimeMillis() - start;
        // 退出线程池
        exec.shutdown();
        while (!exec.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("并发数："+thread_num);
        System.out.println("请求总数："+client_num);
        System.out.println("请求总时间" + total+"ms");
        System.out.println("最慢响应时间" + Collections.max(record)+"ms");
        System.out.println("最快响应时间" + Collections.min(record)+"ms");
        System.out.println("平均响应时间" + (total/client_num)+"ms");

    }

    static class TaskThread implements Runnable {
        Semaphore semp;
        public TaskThread(Semaphore semp) {
            this.semp = semp;
        }

        @Override
        public void run() {
            try {
                // 获取许可
                semp.acquire();
                long t0 = System.currentTimeMillis();
                //do something
                long t1 = System.currentTimeMillis();
                long cost = t1 - t0;
                total += cost;
                record.add(cost);
                System.out.println("耗时：" + (t1 - t0));

                semp.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
