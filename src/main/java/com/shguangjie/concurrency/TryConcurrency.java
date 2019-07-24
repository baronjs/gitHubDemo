/*
 * Copyright (c) 2019 Vast Pure Technology(广洁科技). All rights reserved.
 *
 */
package com.shguangjie.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * @author baronjs
 * @since JDK1.8.0.181
 */
public class TryConcurrency {
    public static void main(String[] args) {
        // 通过匿名内部类的方式/箭头函数创建线程，并且重写其中的run方法
        // 方式①
//        new Thread(() -> {
//            // 听音乐
//            enjoyMusic();
//        }).start();
        // 方式②
//        new Thread() {
//            @Override
//            public void run() {
//            // 听音乐
//            enjoyMusic();
//            }
//        }.start();
        // 方式③
        new Thread(TryConcurrency::enjoyMusic).start();
        // 浏览新闻
        browseNews();
    }

    /**
     * 浏览新闻
     */
    private static void browseNews() {
        for (; ; ) {
            System.out.println("Uh-huh, the good news.");
            // 睡眠1秒
            sleep(1);
        }
    }

    /**
     * 听音乐
     */
    private static void enjoyMusic() {
        for (; ; ) {
            System.out.println("Uh-huh, the nice music.");
            // 睡眠1秒
            sleep(1);
        }
    }

    /**
     * 模拟等待并且忽略异常
     *
     * @param seconds 秒
     */
    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
