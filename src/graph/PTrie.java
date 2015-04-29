package graph;

import java.util.ListIterator;

 
public class PTrie{
 private TrieNode root;
 
 public PTrie(){
  root = new TrieNode(-1);
 }
 public TrieNode getRoot(){
	 return this.root;
 }
 
 public void insert(String s){
	  TrieNode current = root; int count=0;
	  String ary[]= s.split(" ");
	  if(ary.length==0) //For an empty character
	   current.marker=true;
	  for(String st:ary){
		  TrieNode child = current.subNode(Integer.parseInt(st));
		  if(child!=null){ 
			    current = child;
			   }
			   else{
			    current.child.add(new TrieNode(Integer.parseInt(st)));
			    current = current.subNode(Integer.parseInt(st));
			   }
	      if(count==ary.length-1){
	    	  current.marker = true;
	    	  current.addLabel(s);
	      }
			  
	      count++;
	  }
}


 public boolean search(String s){
	  TrieNode current = root;
	  String ary[]=s.split(" ");
	  while(current != null){
		  for(String st:ary){
			  if(current.subNode(Integer.parseInt(st))==null)
				  return false;
			  else
				  current = current.subNode(Integer.parseInt(st));
		  }
	   /* 
	    * This means that a string exists, but make sure its
	    * a word by checking its 'marker' flag
	    */
	   if (current.marker == true)
	    return true;
	   else
	    return false;
	  }
	  return false; 
	 }
 public boolean searchPrefix(String s){
	  TrieNode current = root;
	  String ary[]=s.split(" ");
	  while(current != null){
		  for(String st:ary){
			  if(current.subNode(Integer.parseInt(st))==null)
				  return false;
			  else
				  current = current.subNode(Integer.parseInt(st));
		  }
	   /* 
	    * This means that a string exists, but make sure its
	    * a word by checking its 'marker' flag
	    */
	   if (current.marker == false || current.marker==true)
	    return true;
	   else
	    return false;
	  }
	  return false; 
	 }

 public void insert(TrieNode n){
	 TrieNode current=root;
	 current.child.add(n);
 }
 public String toString(){
	 String trie="";
	 TrieNode current=root;
	 ListIterator<TrieNode> litr = (ListIterator<TrieNode>) current.child.iterator();
	  while(litr.hasNext()){
		current=litr.next();
		 String st="node= " + current.value + " node.marker= " + current.marker;
		 trie+=st;
		trie+="\r";
		ListIterator<TrieNode> litr2 = (ListIterator<TrieNode>) current.child.iterator();
		while(litr2.hasNext()){
			current=litr2.next();
			st="node= " + current.value + " node.marker= " + current.marker;
			trie+=st;
			trie+="\r";
		}
		
	}
	return trie;
 }

}