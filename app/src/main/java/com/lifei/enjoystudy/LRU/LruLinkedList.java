package com.lifei.enjoystudy.LRU;

public class LruLinkedList<T> extends LinkedList<T>{
    private static final int DEFAULT_MEMORY_CAPCITY=5;

    private int memory_size;

    public LruLinkedList() {
        this.memory_size=DEFAULT_MEMORY_CAPCITY;
    }

    public LruLinkedList(int memory_size) {
        this.memory_size = memory_size;
    }

    public void lruPut(T data){
        while (size>=memory_size){
            removeLast();
        }
        put(data);
    }

    public T lruRemove(){
       return removeLast();
    }

    public T lruGet(int index){
        checkPostionIndex(index);
        Node quick=head;
        Node slow=head;
        for (int i = 0; i < index; i++) {
            slow=quick;
            quick=quick.next;
        }
        slow.next=quick.next;
        quick.next=head;

        head=quick;

        return head.data;
    }
}
