package com.java.practice.misc;

public class RecursionTest {

  public static void main(String[] args) {
    fact(5);
  }

  private static int fact(int x) {
    System.out.println("Called.....");
    Long tm = Runtime.getRuntime().totalMemory() / (1024 * 1024);
    Long fm = Runtime.getRuntime().freeMemory() / (1024 * 1024);
    System.out.println("total: " + tm + ", free: " + fm);
    return fact(x);
    /*if (x > 0) {
      return fact(--x);
    } else {
      return 0;
    }*/
  }

}
