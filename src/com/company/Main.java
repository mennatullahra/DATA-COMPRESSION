package com.company;
import java.util.Scanner;
import java.util.Vector;
public class Main {
    static String text;    //the text that will be compressed
    static String part=""; //a container that contains the characters which were compressed.
    
    //search function: finds the nearest position for a specific block of characters.
    //return a vector have the position and length if any.
    //parameters: the index of the block and the block
    public static Vector<Integer> search(int index,String sub){         
    	part="";
        for(int i=0;i<index;i++) //for loop to fill the "part" string  
        {
            part+=text.charAt(i);
        }
        Vector<Integer>v=new Vector<Integer>(); //vector of the position and length
        //if the char not found in the previous characters, return -1,-1 
        if(sub.length()==1 && part.lastIndexOf(sub)==-1) 
        {
            v.add(-1);
            v.add(-1);
        }
        //if the char found in the previous characters,but only one char,-2
        else if(sub.length()>1 && part.lastIndexOf(sub)==-1){
            sub = sub.substring(0, sub.length() - 1);
            v.add(index-part.lastIndexOf(sub));
            v.add(-2);

        }
        //if the block of characters found return the position and length
        else{
            v.add(index-part.lastIndexOf(sub));
            v.add(sub.length());

        }
        return v;
    }

    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        text=input.next();
        Vector<Integer> v1=new Vector <Integer>();          //vector for the positions
        Vector<Integer> v2=new Vector <Integer>();          //vector for the length
        Vector<Character> v3=new Vector <Character>();      //vector for the next characters
        String x;                                           //string contain the block i will search for
        for(int i=0;i<text.length();i++){
            x="";
            x+=text.charAt(i);
            Vector<Integer> cnt=search(i,x);
            if (cnt.get(1)==-1){ //if the char didn't repeated before:
                v1.add(0);
                v2.add(0);
                v3.add(text.charAt(i));
            }
            else {
                for(int j=i+1;j<text.length();j++) {
                    x+=text.charAt(j);
                    cnt=search(i,x);
                    if ((cnt.get(1)==x.length())&&(j==text.length()-1)){ //if the whole block repeated before 
                        v1.add(cnt.get(0));                              //so the next character variable will be null
                        v2.add(cnt.get(1));
                        v3.add('0');
                        i+=x.length()-1;
                        break;
                    }
                    else if (cnt.get(1) == -2){ //if i checked for a block, and i didn't find it so i talk the block-1
                        v1.add(cnt.get(0));
                        v2.add(x.length()-1);
                        v3.add(text.charAt(j));
                        i+=x.length()-1;
                        break;

                    }
                }
            }
        }
        System.out.println("Tags After Compression");
        for(int f=0;f<v1.size();f++){
            System.out.println("<"+v1.get(f)+", "+v2.get(f)+", "+v3.get(f)+">");
        }
        
        
        
        //Decompression
        System.out.println("Text After Decompress");
        String w="";
        
        for(int i=0;i<v1.size();i++){
            for(int j=0;j<v2.get(i);j++){
                w+=w.charAt(w.length()-v1.get(i));
            }
            if (v3.get(i)!='0')
                w+=v3.get(i);
        }
        System.out.println(w);
    }
}