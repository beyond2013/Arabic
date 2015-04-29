package graph;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
public class AFS {
	int minSup;
	Vector<Path> Ck, Lk, L0;
	private String infile;
	private String outfile;
	Vector<String> Y;
	TrieNode parent, child;
	static boolean compGraph=false;
	long lStart, lDiff; int totalcandidates=0;
	StringBuffer result;
	public AFS(String transactionfile, String outputfile, int minSupportCount){
		minSup=minSupportCount;
		infile=new String(transactionfile);
		outfile = new String(outputfile);
		Ck=new Vector<Path>();
		Lk=new Vector<Path>();
		L0=new Vector<Path>();
		Y=new Vector<String>();
		
	}
	
	public void AFSProcess(){
		int n=0;
		lStart=System.currentTimeMillis();

		do
		{
			result=new StringBuffer();
			//if(n > 0 && Ck.isEmpty()) break;
			AFSpruneExtend(n);
			//result.append("Iteration: "+ (n+1) + " No of Candidate paths: " + Ck.size());
			//result.append("\n");
			totalcandidates+=Ck.size();
			//printCk();
			//result.append("Ck: ");
			//result.append(getCk());
			//result.append("\n");
			AFScheckSupport(n);
			printLk();
			//result.append("Frequent subpaths");
			result.append(getLk());
			result.append("\n");
			ReadWriteTextFile.setContents(new File(outfile), result.toString());
			result=null;
			n++;
		}while(Lk.size()>0);
		lDiff=System.currentTimeMillis()-lStart;
		String str = new String("The AFS Algorithm took: " + lDiff + " Milli Seconds");
		str += "\n";
		str += "Total Candidates: " + totalcandidates;
		ReadWriteTextFile.setContents(new File(outfile), str);
		//writeResult(result.toString());
	}
	/*private void writeResult(String res){
		try
        {
        	FileWriter fw=new FileWriter(outfile);
        	BufferedWriter file_out;
        	file_out = new BufferedWriter(fw);
        	file_out.write(res);
			file_out.close();
        	
        }catch(IOException e)
        {
        	e.printStackTrace();
        }
	}*/
	public void AFSpruneExtend(int arg){
		System.out.println("Entered AFSpruneExtend.....");
		Vector<Path> tempC=new Vector<Path>();
		if (arg==0){
			getC0();
		}
		else{
			for(Path p:Lk){
				//System.out.println("Number format exception here with p: " + p.toString());
				Vertex v = new Vertex(p.getLastVertex());
				v.setAdj();
				Iterator<Integer> itr=v.getAdj(); 
				while(itr.hasNext()){
					Integer last=itr.next();
					Path pPrime =new Path();
					pPrime=Path.copyOf(p);
					pPrime.addVertex(last.toString());
					System.out.println("Generated candidate: " + pPrime);
					String tmpstr=pPrime.sfxsubpath().toString(1);
					if(isSubPath(pathToString(Lk),tmpstr)){
						if(!tempC.contains(pPrime)){
							tempC.add(pPrime);
						}
					}
				}
			}
				
			Ck.clear();
			for(Path p:tempC){
				Ck.add(p);
			}
			tempC.clear();
		}
	}
	
	public void AFScheckSupport(int arg){
		System.out.println("Entered AFScheckSupport.....");
		Lk= new Vector<Path>();
		if(arg==0){
			for(Path p:Ck){
		    	   if(p.count>=minSup){
		    		   Lk.add(p);
		    	   }
		       }
		}
		else{
			for(Path Q:Ck){
				Q.count=0;
			}
			//File file = new File(infile);
		    Reader rdr;BufferedReader reader = null;
		       try {
		    	   System.out.println("Scanning DB.....");
		    	   rdr = new InputStreamReader(new FileInputStream(infile), "utf-8");
		   		   reader = new BufferedReader(rdr);
		    	   //reader = new BufferedReader(new FileReader(file));
		           String text = null; 
		           while ((text = reader.readLine()) != null) { 
		        	  String[] trans= text.trim().split(","); 
		        	  Vector<String> temp= new Vector<String>();
		        	  Vector<Path> Cp= new Vector<Path>();
		        	  temp=subPath(Ck,trans[1].toString().trim());
		        	  Y=new Vector<String>();
		        	  //System.out.println("Transaction: "+ text);
		        	  if(temp.size()>0){
		        		  for(int i=0;i<temp.size();i++){
		        			  Path p=new Path(temp.get(i).toString().trim(),false);
		        			  p.setLocation(trans[0]);
		        			  //System.out.println("added " + lineNo + " to path " + p);
		        			  //System.out.println("p.locations= " + p.strLocation());
		        			  //System.out.println(p.toString() + " : " + p.count);
		        			  Cp.add(p);
		        		  }
		        	  }
		        	  for(Path Q:Cp){
		        		for(Path p:Ck){
		        		  if(Q.equals(p)){
		        			  p.setLocation(Q.getLocation());
		        			  p.count++;
		        		  }
		        		}
		        	  }
		        	  Cp.clear();
		        		
		           }
		           for(Path Q:Ck){
		        	   if(Q.count>= minSup){
		        		   Lk.add(Q);  
		        	   }
		           }
		           
			       Ck.clear();
			       for(Path L:Lk){
			    	   Ck.add(L);
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
		
	}
	
	private  Vector<String> subPath(Vector<Path> c,String T){
		System.out.println("Inside subPath()");
		Vector<String> Y = new Vector<String>();
		PTrie trie = new PTrie();
    	for(Path p:c)
    	{    
    	String st = p.toString(1);
    	 trie.insert(st);
    	}
    	Path P=new Path(T,false);
    	Y=doSubPaths(trie.getRoot(), P);
		return Y;
	}
	
	public Vector<String> doSubPaths(TrieNode node, Path P){
		Vector<String> Y=new Vector<String>();
		while(P.vertices.size()>0){
			Y=doPrefixSubpaths(node,P);
			P.vertices.remove(0);
		}
		
		return Y;
	}
	public Vector<String> doPrefixSubpaths(TrieNode node, Path Q){
		String firstQ=Q.getFront(); 
		//System.out.println("Q: " + Q);
		if(node.marker==true){
			//System.out.println("inside node.marker==true");
			for(String st:node.label)
				{
				if(!Y.contains(st))	
					Y.add(st);
				}
		}
		else if(Q.vertices.size()==0){
			return Y;
		}
		else if(node.subNode(Integer.parseInt(firstQ))!=null){
			child=node.subNode(Integer.parseInt(firstQ));
			Q=Q.sfxsubpath();
			if(Q.vertices.size()>=0)
				Y=doPrefixSubpaths(child,Q);
		}
	
		return Y;
	}

	public void getC0(){
		System.out.println("inside getc0()....");
		Vector<Path> tempC = new Vector<Path>();
		File file = new File(infile);
		BufferedReader reader=null;
	       try {
	    	   System.out.println("inside try block of getc0()...."); 
	    	   reader = new BufferedReader(new FileReader(file));
	           String text = null; 
	           while ((text = reader.readLine()) != null) {  	 
	    		   //System.out.println("text: " + text);
	        	   	   String[] line=text.split(",");
	        	   	   String[] trans=line[1].split(" ");
	    			   for(String st:trans){
	    				   Path p=new Path(st,false);
		    			   if(tempC.size()==0){
	    					   p.count++;
	    					   p.setLocation(line[0].toString());
	    					   tempC.add(p);
	    				   }
	    				   else
	    				   if(!tempC.contains(p)){
	    					   p.count++;
	    					   p.setLocation(line[0].toString());
	    					   tempC.add(p);
	    				   }
	    				   else{
	    					   Path.getObj(p, tempC).count++;
	    					   Path.getObj(p, tempC).setLocation(line[0].toString());
	    				   }
	    			   }
	           }
	  
		       Ck.clear();
		       for(Path p: tempC){
		    	   Ck.add(p);
		       }
		       tempC.clear();    
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
	private  boolean isSubPath(Vector<String> c,String a)
    {
		String rev="";
		PTrie trie = new PTrie();
    	String st=""; 
    	for(int i=0;i<c.size();i++)
    	{
    		st=c.elementAt(i);
    		trie.insert(st);	    		
    	}
    	st = a;
    	String[] ary=a.split(" ");
    	for(int ind=ary.length-1;ind>=0;ind--){
    		rev+= ary[ind];
    		rev+=" ";
    	}
    	rev=rev.trim();
    /*	System.out.println("st= " + st + " st2= " + st2);
    	System.out.println("trie.search(st)= " + trie.search(st) + " trie.search(st2)= " + trie.search(st2));*/
    	if(trie.search(st))
    		return true;
    	else if(trie.search(rev))
    		return true;
    	else 
    		return false;       	
    }
	
	public static String sortTrans(String txt){
		String result=new String("");
		int[] ary;
		String[] strary= txt.split(" ");
		ary=new int[strary.length];
		for(int i=0;i<strary.length;i++){
			ary[i]=Integer.parseInt(strary[i]);
		}
		Arrays.sort(ary);
		for(int i=0;i<ary.length;i++){
		result += Integer.toString(ary[i]);
		result += " ";
		}
		result=result.trim();
		return result;
	}
	
	
	private Vector<String> pathToString(Vector<Path> V){
		Vector<String> res=new Vector<String>();
		String temp;
		for(Path p1:V){
			temp="";
			for(String s:p1.vertices){
				temp+=s;
				temp+=" ";
			}
			temp=temp.trim();
			res.add(temp);
		}
		
		return res;
	}

	public String getCk(){
		String str=new String("");
		for(Path p:Ck){
			str+= p.toString();
			str+= "\t";
			str+= p.count;
			str+="\n";
		}
		return str;
	}
	
	public String getLk(){
		String str=new String("");
		for(Path p:Lk){
			str+= p.toString();
			str+= "\t";
			str+=p.length;
			str+= "\t";
			str+=p.count;
			str+= "\t";
			str+= p.strLocation();
			str+="\n";
		}
		return str;
	}
	public void printCk(){
		int i=1;
	/*	if(Ck.isEmpty()){
			System.out.println("Ck is empty");
		}
		else{*/
			System.out.print("Ck: ");
			for(Path p:Ck){
				System.out.print(p + " : " + p.count);
				if(i<Ck.size())
					System.out.print(", ");
				i++;
			}
		//}
		System.out.println();
		System.out.println("------------------------------------------------------------");
	}
	
	public void printLk(){
		int i=1;
		if(Lk.isEmpty()){
			System.out.print("Lk is empty");
		}
		else{
			System.out.println("Lk: Count");
			for(Path p:Lk){
				System.out.print(p + " : " + p.count);
				if(i< Lk.size())
					System.out.print(", ");
				i++;
			}
		}
			System.out.println();
			System.out.println("------------------------------------------------------------");
	}
}
