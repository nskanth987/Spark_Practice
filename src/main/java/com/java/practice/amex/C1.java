package com.java.practice.amex;

class C1 {

    public static void main(String[] args) {
        int[] a = {1, 4, -1, 3, 2};

        System.out.println(solution(a));
    }

    public static int solution(int[] A) {
        int len = 1;
        int index = 0;
        while (A[index] != -1) {
            len++;
            index = A[index];
        }
        return len;
    }
}
