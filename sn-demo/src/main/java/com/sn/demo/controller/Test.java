package com.sn.demo.controller;

public class Test {

    private static Object object = new Object();
    private static boolean count = true;

    public static void main(String[] args) {

        Thread t1 = new Thread(new Task("线程1",1));
        Thread t2 = new Thread(new Task("线程2",2));
        t1.start();
        t2.start();

    }

    static class Task implements Runnable{

        private  final String threadName;
        private  final Integer number;


        public Task(String threadName, Integer number) {
            this.threadName = threadName;
            this.number = number;
        }

        @Override
        public void run() {
           for(int i=0;i<100;i++){
               synchronized (object){
                   while(count != (number==1)){
                       try {
                           object.wait();
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       }
                   }
                   System.out.println(threadName+":"+i);
                   count = !count;
                   object.notify();
               }
           }
        }
    }

}
