package com.lifei.enjoystudy.forkjoin;

public class SingleThreadSum {
    public static void main(String[] args) {
        int count=0;
        int[] src=MakeArray.makeArray();
        long start=System.currentTimeMillis();
        for (int i = 0; i < src.length; i++) {
            count+=src[i];
        }
        System.out.println("The count is"+count+" spend time:"+(System.currentTimeMillis()-start)+"ms");
    }
}
