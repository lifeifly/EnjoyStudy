package com.lifei.enjoystudy.jvm;

public class ObjectAndClass {//类在方法区
    static int age=18;//方法区
    final static int sex=1;//方法区
    ObjectAndClass object=new ObjectAndClass();
    private boolean isKing;

    public static void main(String[] args) {
        int x=18;
        long y=1;
        ObjectAndClass lobject= new ObjectAndClass();
    }
}
