package com.lifei.enjoystudy.graph;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Graph {
    //key:编号0，1，2，3  value:对应的点
    public HashMap<Integer,Node> nodes;
    public HashSet<Edge> edges;

    public Graph() {
        nodes=new HashMap<>();
        edges=new HashSet<>();
    }

    /**
     * 宽度优先
     * @param head
     */
    public void widthPriority(Node head){
        //保存将要弹出的节点
        Queue<Node> queue=new LinkedList<>();
        //记录已经加入的节点
        HashSet<Node> set=new HashSet<>();
        //先加入头
        queue.add(head);
        set.add(head);
        while(!queue.isEmpty()){
            //先弹出打印
            Node node=queue.poll();
            System.out.println(node.value);
            //遍历下一个节点添加
            for (int i = 0; i < node.nexts.size(); i++) {
                if (!set.contains(node.nexts.get(i))){
                    //之前没加入过
                    queue.add(node.nexts.get(i));
                    set.add(node.nexts.get(i));
                }
            }
        }
    }

    public void depthPriority(Node head){
        if (head==null){
            return;
        }
        Stack<Node> stack=new Stack<>();
        //记录加入栈的对象
        HashSet<Node> set=new HashSet<>();

        stack.add(head);
        set.add(head);
        System.out.println(head.value);
        while(!stack.isEmpty()){
            Node cur=stack.pop();
            for (int i = 0; i < cur.nexts.size(); i++) {
                if (!set.contains(cur.nexts.get(i))){
                    stack.push(cur);
                    stack.push(cur.nexts.get(i));
                    set.add(cur.nexts.get(i));
                    System.out.println(cur.nexts.get(i).value);
                    break;
                }
            }
        }
    }

    /**
     * 最小生成树：将一个图用权值和最小的一些边使每个点相通
     * 1.先解锁一个点，解锁这个点的边，看最小边的另一个点有没有连通，没有加入在解锁它的边
     * @param graph
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Set<Edge> primMinGeneraTree(Graph graph){
        //存放呗解锁的点
        Set<Node> set=new HashSet<>();
        //存放结果边
        Set<Edge> result=new HashSet<>();
        //存放已经解锁的边
        Set<Edge> edgeSet=new HashSet<>();

        PriorityQueue<Edge> edgePriorityQueue=new PriorityQueue<>(new WeightComparator());
        for (Node value : graph.nodes.values()) {//防森林，防止几个片状图不连通

            if (!set.contains(value)){
                set.add(value);
                for (Edge edge:value.edges){
                    if (!edgeSet.contains(edge)){
                        edgePriorityQueue.add(edge);
                        edgeSet.add(edge);
                    }

                }
                while(!edgePriorityQueue.isEmpty()){
                    Edge edge=edgePriorityQueue.poll();
                    Node toNode=edge.to;
                    if (!set.contains(toNode)){
                        set.add(toNode);
                        result.add(edge);

                        for (Edge edge1:toNode.edges){
                            if (!edgeSet.contains(edge1)){
                                edgePriorityQueue.add(edge1);
                                edgeSet.add(edge1);
                            }
                        }
                    }
                }
            }

        }
        return result;
    }
    /**
     * 最小生成树：将一个图用权值和最小的一些边使每个点相通
     * 1.将所有边从小到大排序
     * 2.依次将边的两点判断是不是属于一个集合，不是合并在一起，是的话就不合并
     * @param graph
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Set<Edge> kruskalMinGeneraTree(Graph graph){
       UnionFind unionFind=new UnionFind();
       unionFind.makeSets(graph.nodes.values());

        PriorityQueue<Edge> edgePriorityQueue=new PriorityQueue<>(new WeightComparator());
        //将所有边加入小根堆
        for (Edge edge : graph.edges) {
            edgePriorityQueue.add(edge);
        }
        Set<Edge> result=new HashSet<>();
        while(!edgePriorityQueue.isEmpty()){
            Edge edge=edgePriorityQueue.poll();
            if (!unionFind.isSameSet(edge.from,edge.to)){
                unionFind.union(edge.from,edge.to);
                result.add(edge);
            }
        }
        return result;
    }

    public class WeightComparator implements Comparator<Edge>{

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight- o2.weight;
        }
    }
    /**
     * 图的最短路径：迪杰斯特拉算法
     * 求从from开始到达所有点的最小路径
     */
    public HashMap<Node,Integer> dijkstra(Node from){
        //从from出发，到大所有的点
        //记录from到每个点的距离，会不断更新
        HashMap<Node,Integer> disMap=new HashMap<>();
        //自己到自己距离是0
        disMap.put(from,0);
        //已经求出距离的点,即已经作为跳转点了
        HashSet<Node> jumpSet=new HashSet<>();
        //找出distanceMap中记录的距离最小且没做过跳转点的跳转点，当前就一个from点
        Node minNode=getMinJumpNodeByDistance(disMap,jumpSet);
        //有跳转点才能继续
        while (minNode!=null){
            //遍历所有从minNode为跳转点的边
            for (Edge edge:minNode.edges){
                //获取当前边的终点
                Node toNode=edge.to;
                if (!disMap.containsKey(toNode)){
                    //说明当前from到该点没有连通，进行连接
                    disMap.put(toNode,disMap.get(minNode)+edge.weight);
                }else {
                    //说明当前之前存在from到该店的距离，如果此时距离更小就更新
                    disMap.put(toNode,Math.min(disMap.get(minNode)+edge.weight,disMap.get(toNode)));
                }
            }
            //minNode做了跳转点后，进行记录
            jumpSet.add(minNode);
            //看看还有没有可以作为跳转点的点
            minNode=getMinJumpNodeByDistance(disMap,jumpSet);
        }
        return disMap;
    }
    /**
     * 找出distanceMap中记录的距离最小且没做过跳转点的跳转点，当前就一个from点
     * @param disMap
     * @param jumpSet
     * @return
     */
    private static Node getMinJumpNodeByDistance(HashMap<Node, Integer> disMap, HashSet<Node> jumpSet) {
        Node min=null;
        //遍历出发点到达所有点的距离点
        for(Node node:disMap.keySet()){
            if (!jumpSet.contains(node)){//说明当前点还没做过跳转点
                if (min==null){
                    min=node;
                }else {
                    min=min.value<=node.value?min:node;
                }
            }
        }
        return min;
    }
    /**
     * 拓扑排序：用于有向无环图，常用于编译环境
     * @param graph
     * @return
     */
    public List<Node> sortedTopology(Graph graph){
        //记录每个Node的入度
        HashMap<Node,Integer> inMap=new HashMap<>();
        //入度为0的Node才能进这个队列
        Queue<Node> zeroQueue=new LinkedList<>();
        for (Node node:graph.nodes.values()){
            inMap.put(node,node.in);
            if (node.in==0){
                zeroQueue.add(node);
            }
        }
        List<Node> result=new ArrayList<>();
        while(!zeroQueue.isEmpty()){
            Node cur=zeroQueue.poll();
            result.add(cur);
            //消除边的依赖
            for (Node next : cur.nexts) {
                inMap.put(next,inMap.get(next)-1);
                if (inMap.get(next)==0){
                    zeroQueue.add(next);
                }
            }
        }
        return result;
    }
    public class Node{
        public int value;
        public int in;//入度：有多少点直接连向自己的
        public int out;//出度：有多少连出去的
        public ArrayList<Node> nexts;
        public ArrayList<Edge> edges;

        public Node(int value) {
            this.value = value;
            in=0;
            out=0;
            nexts=new ArrayList<>();
            edges=new ArrayList<>();
        }
    }
    public class Edge{
        public int weight;
        public Node from;
        public Node to;

        public Edge(int weight, Node from, Node to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }

    public static class UnionFind{
        public HashMap<Node,Node> fatherMap;
        public HashMap<Node,Integer> sizeMap;

        public UnionFind() {
            fatherMap=new HashMap<>();
            sizeMap=new HashMap<>();
        }

        public void makeSets(Collection<Node> nodes){
            fatherMap.clear();
            sizeMap.clear();
            for (Node node:nodes){
                fatherMap.put(node,node);
                sizeMap.put(node,1);
            }
        }
        public Node findFather(Node node){
            Stack<Node> path=new Stack<>();
            while (fatherMap.get(node)!=node){
                path.add(node);
                node=fatherMap.get(node);
            }
            while (!path.isEmpty()){
                fatherMap.put(path.pop(),node);
            }
            return node;
        }

        public boolean isSameSet(Node one,Node two){
            if (one==null||two==null){
                return false;
            }
            return findFather(one)==findFather(two);
        }

        public void union(Node one,Node two){
            if (one==null||two==null){
                return;
            }
            if (!isSameSet(one,two)){
                Node oneF=findFather(one);
                Node twoF=findFather(two);
                int oneSize=sizeMap.get(oneF);
                int twoSize=sizeMap.get(twoF);

                if (oneSize>=twoSize){
                    fatherMap.put(twoF,oneF);
                    sizeMap.put(oneF,oneSize+twoSize);
                    sizeMap.remove(twoF);
                }else {
                    fatherMap.put(oneF,twoF);
                    sizeMap.put(twoF,oneSize+twoSize);
                    sizeMap.remove(oneF);
                }
            }
        }
    }
}
