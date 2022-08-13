
package com.lifei.enjoystudy.midtoafter;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MidPrefixToAfterPrefix {

    public  static String transform(String midPrefix){
        //操作数队列
        Queue<Character> opdQueue=new LinkedList<>();
        //运算符栈
        Stack<Character> oprStack=new Stack<>();

        char[] chs=midPrefix.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            char c=chs[i];
            if (Character.isDigit(c)){
                //数字直接插入操作数队列
                opdQueue.offer(c);
            }else if (c=='('){
                //直接插入运算符栈
                oprStack.push(c);
            }else if (c==')'){
                //一直弹到c=='(',把中间的运算符加到操作数队列
                while(!oprStack.isEmpty()&&oprStack.peek()!='('){
                    opdQueue.offer(oprStack.pop());
                }
                //弹出'('
                oprStack.pop();
            }else {
                //运算符
                if (oprStack.isEmpty()||oprStack.peek()=='('){
                    oprStack.push(c);
                }else {
                    if(c=='+'||c=='-'){
                        opdQueue.add(oprStack.pop());
                    }else {
                        if (oprStack.peek()=='*'||oprStack.peek()=='/'){
                            opdQueue.add(oprStack.pop());
                        }else {
                            oprStack.push(c);
                        }
                    }
                }


            }
        }
        while(!oprStack.isEmpty()){
            opdQueue.offer(oprStack.pop());
        }

        StringBuilder sb=new StringBuilder();
        while(!opdQueue.isEmpty()){
            sb.append(opdQueue.poll());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s="9+(3-1)*3+10/2";
        System.out.println(MidPrefixToAfterPrefix.transform(s));
    }
}
