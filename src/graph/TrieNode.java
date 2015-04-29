package graph;

 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class TrieNode {
 int value;
 boolean marker; 
 Collection<TrieNode> child;
 ArrayList<String> label;
 public TrieNode(){
	  child = new LinkedList<TrieNode>();
	  marker = false;
	  value=0;
	  label=new ArrayList<String>();
	 } 
 public TrieNode(int v){
	  child = new LinkedList<TrieNode>();
	  marker = false;
	  value = v;
	  label=new ArrayList<String>();
	 }
 public void addLabel(String arg){
	 this.label.add(arg);
 }
 public ArrayList<String> getLabel(){
	 return this.label;
 }
 public String PrefixPaths(){
	 String st= new String();
	 ListIterator<TrieNode> litr = (ListIterator<TrieNode>) this.child.iterator();
	 while (litr.hasPrevious()){
		 st+=litr.previous();
	 }
	 return st;
 }

 public TrieNode subNode(int val){
	  if(child!=null){
	   for(TrieNode eachChild:child){
	    if(eachChild.value == val){
	     return eachChild;
	    }
	   }
	  }
	  return null;
	 }
 public String toString(){
	 String res="";
	 Iterator<TrieNode> itr = this.child.iterator();
	 while(itr.hasNext()){
		 res+=itr.next().value;
		 res+=" ";
	 }
	 return res;
 }
}