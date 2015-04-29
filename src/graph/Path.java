package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Path {
	public ArrayList<String> vertices;
	public int length;
	public int count;
	//public int color;
	public ArrayList<String> location;
	
	public Path(){
		vertices = new ArrayList<String>();
		length=vertices.size();
		count=0;
		location=new ArrayList<String>();
	}
	public Path(ArrayList<String> ary){
		vertices= new ArrayList<String>(ary);
		length=ary.size();
		count=0;
		location=new ArrayList<String>();
	}
	public Path(String str){
		vertices = new ArrayList<String>();
		if(location==null){
			location=new ArrayList<String>();
		}
		String[] ary=str.split(" ");
		for(String i:ary){
			//vertices.add(new Vertex(Integer.parseInt(i)));
			//if(!vertices.contains(i)){
				vertices.add(i);
				length++;	
			//}
		}
		count=0;
		this.sortVertices();
	}
	//dummy boolean argument sort all paths are sorted except the ones written as transactions
	public Path(String str, boolean sort){
		vertices = new ArrayList<String>();
		//location=new ArrayList<Integer>();
		String[] ary=str.split(" ");
		for(String i:ary){
			//vertices.add(new Vertex(Integer.parseInt(i)));
			//if(!vertices.contains(i)){
				vertices.add(i);
				length++;	
			//}
		}
		count=0;
		
	}
	public static Path copyOf(final Path p){
	 Path result=new Path(p.vertices);
	 //result.count=p.count;
	 result.count=0;
	 return result;
	}
	public void setLocation(String val){
		if(location==null){
			location=new ArrayList<String>();
		}
		if(!location.contains(val)){
			location.add(val);	
		}
	}
	public void setLocation(ArrayList<String> arg){
		this.location.addAll(arg);
	}
	public ArrayList<String> getLocation(){
		return this.location;
	}
	public String strLocation(){
		return location.toString();
	}
	public void addVertex(String v){
		//vertices.add(new Vertex(Integer.parseInt(v)));
		vertices.add(v);
		//this.sortVertices();
		length++;
	}
	public boolean isSubpath(Path p){
		boolean result=false;
		result=this.vertices.containsAll(p.vertices);
		return result;
	}
	/*public int getColor(){
		return color;
	}
	public void setColor(int c){
		color=c;
	}*/
	public int getLastVertex(){
		return Integer.parseInt(this.vertices.get(vertices.size()-1));
	}
	public Path kPath(int v){
		Path Q=new Path();
		String st=new String();
		try{
			for(int i=0;i<=v;i++){
				st+= this.vertices.get(i);
				st+=" ";
			}
			st=st.trim();
			Q=new Path(st, false);
						
		}
		catch(IndexOutOfBoundsException e){
			System.err.print("index out of bounds");
		}
		return Q;
    }
	public String toString(){
		String result=new String("");
		result="(";
		int count=0;
		for(String v:this.vertices){
		result+= v.toString();
		count++;
		if(count<this.vertices.size()){
			result+=" ";
		}
		}
		result+=")";
		return result;
	}
	
	public String toString(int a){
		String result=new String("");
		int count=0;
		for(String v:this.vertices){
		result+= v.toString();
		count++;
		if(count<this.vertices.size()){
			result+=" ";
		}
		}
		return result.trim();
	}
	// overriding the equals method of the object class in order to
	// be able to use Collections.contains(path) method
	
	public boolean equals(Object arg){
		boolean result=false;
		Path objo= (Path) arg;
		/*if(this.vertices.containsAll(objo.vertices)){
			result=true;
		}
		else{
			result=false;
		}*/
		
		if(this.vertices.size()==objo.vertices.size()){
			for(int i=0;i<this.vertices.size();i++){
				String strthis, strobjo;
				strthis=this.vertices.get(i).toString().trim();
				strobjo=objo.vertices.get(i).toString().trim();
				if(strthis.compareTo(strobjo)==0){
					result=true;
				}
				else
				{
					result=false;
					break;
				}
			}
		}
			
		return result;
	}
	
	
	public static Path getObj(Path obj, Vector<Path> ary){
		Path p=new Path();
		for(Path pr: ary){
			if(obj.equals(pr)){
				p=pr;
			}
		}
		return p;
	}
	/* In the following method psubpath() p stands for prefix, 
	 * the method when invoked on a path object returns
	 * the prefix subpath, i.e. the path is returned as it is except its
	 * last index. e.g. if the method is invoked on a path like
	 * "1 2 3 4", the following method should return "1 2 3"
	 */
	public Path psubpath(){
		Path res= new Path();
		res=Path.copyOf(this);
		res.vertices.remove(vertices.size()-1);
		return res;
	}
	
	public Path sfxsubpath(){
		Path res= new Path();
		res=Path.copyOf(this);
		if(res.vertices.size()>0){
			res.vertices.remove(0);	
		}
	
		return res;
	}
	
	public void sortVertices(){
		int[] ary=new int[this.vertices.size()];
		int i=0;
		for(String str:this.vertices){
			ary[i++]=Integer.parseInt(str);
		}
		Arrays.sort(ary);
		this.vertices.clear();
		for(int j=0;j<ary.length;j++){
			this.vertices.add(Integer.toString(ary[j]));
		}
	}

	public String getFront(){
		String str= new String();
		if (this.vertices.size()>0){
		  str=this.vertices.get(0);	
		}
		 return str;
	}
}