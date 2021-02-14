package com.epam.at.pageobjectmodel.tools;

public class Delay {

    public static void makeDelay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
