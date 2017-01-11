package indi.jcl.magicutils.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * ����������
 * Created by Magic Long on 2016/11/17.
 */
public class ConcurrentTest {
    private static int thread_num = 10;// ������
    private static int client_num = 200;// ��������
    private static long total = 0;
    private  static List<Long> record = new ArrayList<>();



    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ExecutorService exec = Executors.newCachedThreadPool();
        // thread_num���߳̿���ͬʱ����
        final Semaphore semp = new Semaphore(thread_num);
        // ģ��client_num���ͻ��˷���
        for (int index = 0; index < client_num; index++) {
            exec.execute(new TaskThread(semp));
        }
        long timeSpend = System.currentTimeMillis() - start;
        // �˳��̳߳�
        exec.shutdown();
        while (!exec.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("��������"+thread_num);
        System.out.println("����������"+client_num);
        System.out.println("������ʱ��" + total+"ms");
        System.out.println("������Ӧʱ��" + Collections.max(record)+"ms");
        System.out.println("�����Ӧʱ��" + Collections.min(record)+"ms");
        System.out.println("ƽ����Ӧʱ��" + (total/client_num)+"ms");

    }

    static class TaskThread implements Runnable {
        Semaphore semp;
        public TaskThread(Semaphore semp) {
            this.semp = semp;
        }

        @Override
        public void run() {
            try {
                // ��ȡ���
                semp.acquire();
                long t0 = System.currentTimeMillis();
                //do something
                long t1 = System.currentTimeMillis();
                long cost = t1 - t0;
                total += cost;
                record.add(cost);
                System.out.println("��ʱ��" + (t1 - t0));

                semp.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
