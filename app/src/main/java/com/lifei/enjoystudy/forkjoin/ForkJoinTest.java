package com.lifei.enjoystudy.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

    public static void main(String[] args) {
        int[] src=MakeArray.makeArray();
        ForkJoinPool pool=new ForkJoinPool();
        SumTask innerFind=new SumTask(src,0,src.length-1);
    }


    private static class SumTask extends RecursiveTask<Integer>{
        private static final int THRESHOLD=MakeArray.ARRAY_LENGTH/10;
        private int[] src;
        private int fromIndex;
        private int toIndex;

        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        protected Integer compute() {
            if (toIndex-fromIndex<THRESHOLD){
                System.out.println("toIndex:"+toIndex+" fromIndex:"+fromIndex);
                int count=0;
                for (int i = fromIndex; i <=toIndex ; i++) {
                    count+=src[i];
                }
                return count;
            }else {
                int mid=(fromIndex+toIndex)/2;
                SumTask left=new SumTask(src,fromIndex,mid);
                SumTask right=new SumTask(src,mid+1,toIndex);
                invokeAll(left,right);
                return left.join()+right.join();
            }
        }
    }
}
