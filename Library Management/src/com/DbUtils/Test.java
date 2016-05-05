
package com.DbUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
	
	static Map<Integer, List<Integer>> map=new HashMap<Integer, List<Integer>>();
	
	public static void test(){
		List<Integer> list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		map.put(0, list);
	}
	
	public static void main(String[] args){
		int[] a ={-6,-7,-8};
		System.out.println(maxDifference(a));
	}
	
	static int maxDifference(int[] a) {
        int max=0;
        int diff=0;
        boolean found=false;
        if(a.length==1)
            return a[0];
        for(int i=0; i<a.length; i++){
            for(int j=i+1; j<a.length; j++){
                if(a[j]>a[i]){
                    found=true;
                    diff=a[j]-a[i];
                        if(diff>max)
                        max=diff;
                }
              else{
                    i=j;
                    j=j+1;    
                }
            }
        }
        if(!found)
            return -1;
        return max;

    }
	/*
	public static void main(String args[] ) throws Exception {
         Enter your code here. Read input from STDIN. Print output to STDOUT 
        Scanner sc=new Scanner(System.in);
        int[] a= new int[sc.nextInt()];
        int m=sc.nextInt();
        for(int i=0; i<m;i++){
            int initialValue=sc.nextInt();
            int finalValue=sc.nextInt();
            int addition=sc.nextInt();
            for(int j=initialValue; j<=finalValue;j++){
                a[j-1]=a[j-1]+addition;
            }
        }
        Arrays.sort(a);
        System.out.println(a[a.length-1]);
        
    }*/

}
