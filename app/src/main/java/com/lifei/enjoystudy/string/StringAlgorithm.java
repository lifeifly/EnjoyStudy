package com.lifei.enjoystudy.string;

public class StringAlgorithm {
    /**
     * 字符串匹配暴力解法,返回第一个匹配的子串位置
     * 根据sub长度len，可以将str分成若干个长度为len的子串，一一比较字符
     * @param str
     * @param sub
     */
    public static int subMatchVoilent(String str,String sub){
        int len=sub.length();
        for (int i = 0; i <= str.length()-len; i++) {
            String s=str.substring(i,i+len);
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j)!=sub.charAt(j)){
                    break;
                }
                if (j==s.length()-1){
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * KMP算法字符串匹配
     * str长度为m，sub长度为n
     * 时间复杂度O(n+m)空间复杂度O（n）
     */
    public static int subMatchKMP(String str,String sub){
        int[] nexts=getNexts(sub);
        //模式串位置
        int i=0;
        //枚举主串位置，保证一直往后匹配不回退
        for (int j = 0; j < str.length(); j++) {
            while(j>0&&sub.charAt(i)!=sub.charAt(j)){
                j=nexts[j-1];
            }
            if (sub.charAt(i)==sub.charAt(j)){
                i++;
            }
            if (i==sub.length()){
                return j;
            }
        }
        return -1;
    }

    /**
     * 求nexts数组，即sub[i]代表sub[0..i]子串的最长公共前后缀的长度
     * @param sub
     * @return
     */
    public static int[] getNexts(String sub){
        int j=0;//代表前缀0..j
        int[] nexts=new int[sub.length()];
        nexts[0]=-1;
        //枚举后缀串末尾位置
        for(int i=1;i<sub.length();i++){
            if (j>0&&sub.charAt(i)!=sub.charAt(j)){
                //前缀回退到nexts[j-1]位置
                j=nexts[j-1];
            }else {
                j++;
                nexts[i]=j;
            }
        }
        return nexts;
    }

}
