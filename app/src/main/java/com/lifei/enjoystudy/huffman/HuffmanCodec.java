package com.lifei.enjoystudy.huffman;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class HuffmanCodec {
    private HuffmanTree.Node<Integer> root;
    //权值
    private int[] weights=new int[256];
    //记录编码
    private String[] codes=new String[256];
    //记录所所有节点
    private LinkedList<HuffmanTree.Node<Integer>> nodes=new LinkedList<>();

    private String inputPath;

    private String outputPath;

    public HuffmanCodec(String inputPath, String outputPath) throws IOException {
        this.inputPath = inputPath;
        this.outputPath = outputPath;

        //记录权重
        computeWeights();
    }

    private void computeWeights() throws IOException {
        FileInputStream fis=new FileInputStream(inputPath);
        int data;
        while((data=fis.read())!=-1){
            //记录每个字节出现的次数
            weights[data]++;
        }
        fis.close();
    }
    public void prepare(){
        //构造haffman树
        root=createTree();
        //设置huffman编码
        initHuffmanCode(root,"");

    }

    private void initHuffmanCode(HuffmanTree.Node<Integer> node, String code) {
        //往左走，哈夫曼编码+0
        if (node.left!=null){
            initHuffmanCode(node.left,code+"0");
        }
        //往右走，哈夫曼编码+1
        if (node.right!=null){
            initHuffmanCode(node.right,code+"1");
        }

        //如果是叶子节点，返回该叶子节点的哈夫曼编码
        if (node.left==null&&node.right==null){
            node.code=code;
            codes[node.data]=code;
        }
    }

    private HuffmanTree.Node<Integer> createTree() {
        //生成所有Node节点
        for (int i = 0; i < weights.length; i++) {
            int weight=weights[i];
            if (weight!=0){
                HuffmanTree.Node<Integer> node=new HuffmanTree.Node<Integer>(i,weight);
                nodes.add(node);
            }
        }
        return HuffmanTree.createHuffman(nodes);
    }

    public void encode() throws IOException{
        FileInputStream fis=new FileInputStream(inputPath);
        FileOutputStream fos=new FileOutputStream(outputPath);
        //获取二进制数据
        StringBuilder sb=new StringBuilder();
        //读取文件
        int data;
        int char_tmp;
        while((data=fis.read())!=-1){
            sb.append(codes[data]);
            //8位一个字节
            while(sb.length()>=8){}
            char_tmp=0;
            for (int i = 0; i < 8; i++) {
                char_tmp<<=1;
                if (sb.charAt(i)=='1'){
                    char_tmp|=1;
                }
            }
            fos.write(char_tmp);
            sb.delete(0,8);
        }
        fis.close();

        //剩下不足8位或已经写完
        int last=sb.length();
        if (last>0){
            //后面补0
            char_tmp=0;
            for (int i = 0; i < last; i++) {
                char_tmp<<=1;
                if (sb.charAt(i)=='1'){
                    char_tmp|=1;
                }
            }
            char_tmp<<=(8-last);
            fos.write(char_tmp);
        }

        fos.close();
    }


    public void decode(String decodePath)throws IOException{
        FileInputStream fis=new FileInputStream(outputPath);
        FileOutputStream fos=new FileOutputStream(decodePath);

        StringBuilder sb=new StringBuilder();
        int data;
        int lastData=0;
        while((data=fis.read())!=-1){
            lastData=data;
            String binary=Integer.toBinaryString(data);
            binary=add2Byte(binary,true);
            sb.append(binary);
        }
        //删除多余内容
        if (lastData==0){
            sb.delete(sb.length()-8,sb.length());
        }else {
            sb.delete(sb.length()-8-(8-lastData),sb.length());
        }

        HuffmanTree.Node<Integer> node;
        int num;
        while(sb.length()>0){
            node=root;
            num=0;
            while(num<sb.length()){
                char c=sb.charAt(num);
                //右子树
                if (c=='1'){
                    HuffmanTree.Node<Integer> right=node.right;
                    if (right==null){
                        break;
                    }
                    node=right;
                }else {
                    //左子树
                    HuffmanTree.Node<Integer> left=node.left;
                    if (left==null){
                        break;
                    }
                    node=left;
                }
                num++;
            }
            sb.delete(0,num);
            fos.write(node.data);
        }
        fos.close();
    }

    private String add2Byte(String binary, boolean isFront) {
        int strLen=binary.length();
        if (strLen==8){
            return binary;
        }
        int add=8-strLen;
        StringBuilder sb=new StringBuilder();
        sb.append("%0");
        sb.append(add);
        sb.append("d");
        String format=sb.toString();

        return "";
    }
}
