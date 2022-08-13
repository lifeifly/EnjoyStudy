package com.lifei.enjoystudy;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 模仿ReentrantLock
 */
public class SelfLock implements Lock {
    //静态内部类，自定义同步器
    private static final class Sync extends AbstractQueuedSynchronizer{
        //判断是否被占用
        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0,1)){
                //设置当前占用锁的线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }else if (getExclusiveOwnerThread()==Thread.currentThread()){
                //之前获取过了
                setState(getState()+1);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getExclusiveOwnerThread()!=Thread.currentThread()){
                throw new IllegalMonitorStateException();
            }
            if (getState()==0){
                throw new IllegalMonitorStateException();
            }

            setState(getState()-1);
            if (getState()==0){
                setExclusiveOwnerThread(null);
            }
            return true;
        }
        //每一个Condition包含一个Condition队列
        Condition newCondition(){
            return new ConditionObject();
        }
    }

    private final Sync sync=new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }
    public boolean isLocked() {
        return sync.isHeldExclusively();
    }
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }
    public  boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
      }
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
