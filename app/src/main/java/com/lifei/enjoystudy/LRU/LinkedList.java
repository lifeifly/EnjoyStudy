package com.lifei.enjoystudy.LRU;

public class LinkedList<T> {
    protected Node head;
    public int size;

    public LinkedList() {
        this.size = 0;
    }

    //增加节点
    public void put(T data){
        Node list=head;
        Node curNode=new Node(data,list);
        head=curNode;

        size++;
    }
    public void put(int index,T data){
        checkPostionIndex(index);
        Node list=head;
        Node cur=head;
        for (int i = 0; i < index; i++) {
            list=cur;
            cur=cur.next;
        }

        Node newNode=new Node(data,cur);
        list.next=newNode;
        size++;
    }

    protected void checkPostionIndex(int index) {
        if (index<0||index>=size){
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    //删除节点
    public T remove(){
        if (head!=null){
            Node list=head;
            head=head.next;
            list.next=null;
            size--;
            return list.data;
        }
        return null;
    }
    public T remove(int index){
        checkPostionIndex(index);
        Node list=head;
        Node cur=head;
        for (int i = 0; i < index; i++) {
            list=cur;
            cur=cur.next;
        }
        list.next=cur.next;
        cur.next=null;
        return cur.data;
    }
    public T removeLast(){
        if (head!=null){
            Node list=head;
            Node cur=head;
            while(cur!=null&&cur.next!=null) {
                list=cur;
                cur=cur.next;
            }
            list.next=null;
            size--;
            return cur.data;
        }
        return null;
    }
    //修改节点
    public void set(int index,T data){
        checkPostionIndex(index);
        Node cur=head;
        for (int i = 0; i < index; i++) {
            cur=cur.next;
        }
        cur.data=data;
    }
    //查询节点
    public T get(){
        if (head!=null){
            return head.data;
        }
        return null;
    }
    public T get(int index){
        checkPostionIndex(index);
        Node cur=head;
        for (int i = 0; i < index; i++) {
            cur=cur.next;
        }
        return cur.data;
    }


    class Node{
        T data;
        Node next;

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

    }

    public static void main(String[] args) {

    }
}
