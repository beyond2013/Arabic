	package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Vertex {
 private int value;
 private int count;
 private String label;
 private ArrayList<Integer> adj;

 public Vertex(int val){
	 value=val;
	 adj=new ArrayList<Integer>();
	 count=0;
	 label=new String(""); 
	 //this.setAdj();
 }
 
 public int getValue(){
	 return value;
 }
 public int getCount(){
	 return count;
 }
 public void setCount(int val){
	 count=val;
 }
 public void setAdj(){
	//System.out.println("Setting adj...for vertex: " + this.value);
	 File file = new File("Edges.txt");
	 BufferedReader reader = null;
	 try {
         reader = new BufferedReader(new FileReader(file));
         String text = null; 
         while ((text = reader.readLine()) != null) {  	  
        	 String str[]= text.split("\t");
        	 if(this.value==Integer.parseInt(str[0])){
        		 this.adj.add(Integer.parseInt(str[1]));
        	 }
	     }
	 }
     catch (FileNotFoundException e) {
         e.printStackTrace();
     } 
     catch (IOException e) {
         e.printStackTrace();
     }
     finally {
         try {
             if (reader != null) {
                 reader.close();
             }
         }
         catch (IOException e) {
             e.printStackTrace();
         }
    }
 }
public Iterator<Integer> getAdj(){
	return adj.iterator();
}
 public boolean isAdjacent(Vertex v){
	 if(this.adj.contains(v.value)){
		 return true;
	 }
	 else{
		 return false;
	 }
 }
 public String toString(){
	 return Integer.toString(value);
 }
 public boolean equals(Object obj){
	 Vertex arg= (Vertex) obj;
	 if(this.value== arg.getValue()){
		 return true;
	 }
	 else{
		 return false;
	 }
 }
 
 public Vertex lastAdjVertex(){
	 Vertex v=new Vertex(0);
	 if(this.adj.size()>=1){
		v= new Vertex(this.adj.get(this.adj.size()-1));	 
	 }
	 return v;
 }
 
 /* public static void main(String[] args){
	 Vertex v1=new Vertex(8);
	 Vertex v=new Vertex(4);
	 System.out.println(v);
	 System.out.println(v1);
	 System.out.println(v.isAdjacent(v1));
	 System.out.println("Last vertex in adj of 8: " + v1.lastAdjVertex());
	 Iterator<Integer> itr = v.getAdj();
	 System.out.println("Adjacency of v: " + v);
	 while(itr.hasNext()){
		 System.out.print(itr.next().toString() + "\t");
	 }
 }*/
}
