package com.scala.practice;

/**
 * Created by Srikanth.nelluri on 17/12/2018
 */
public enum TSVFlag {

    LIVE(110), ANY(111);

    private final int number;

    private TSVFlag(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

}
