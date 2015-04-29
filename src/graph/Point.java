package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
public class Point {
	public static int GraphCols;
	public static int GraphRows;
	private int x,y;
	private ArrayList<Integer> adjacencyList;
	 public Point(){
		 x=0;
		 y=0;
		 adjacencyList= new ArrayList<Integer>();
		 this.setAdjacencyList();
	 }
	 public Point(int x1,int y1){
		 this.x=x1;
		 this.y=y1;
		 adjacencyList= new ArrayList<Integer>();
		 this.setAdjacencyList();
	 }
	 public int getX(){
		 return x;
	 }
	
	 public int getY(){
		 return y;
	 }
	 
	 public void setX(int x){
		  this.x=x;
	 }
	 
	 public void setY(int y){
		  this.y=y;
	}

	public String toString(){
		String str;
		str="(x,y):\t";
		str=str + this.x + ", " + this.y;
		return str;
	}
/* 
 * toPoint(index, gridcols) returns the Point object i.e. x,y co-ordinates
 * gridcols is the number columns on the grid graph, index is the number assigned 
 * to each point on the grid starting from 1
 * integer constant 40 is the distance in pixels both horizontally and vertically
 * between two adjacent points
 */
	public static Point toPoint(int index, int gridcols){
		Point p=new Point();
		if(index<=gridcols){
			p.setY(40);
			p.setX(index*40);
		}
		else{
			if(index%gridcols==0){
				p.setX(gridcols*40);
				p.setY((index/gridcols)*40);
			}
			else{
				p.setX((index%gridcols)*40);
				p.setY(((index/gridcols)+1)*40);
			}
		
		}
		p.adjacencyList.clear();
		p.setAdjacencyList();
		return p;
	}
	
	

	//========================================================================
	/*
	 * toIndex is the counterpart method of toPoint. It returns an integer
	 * corresponding to the point passed as an argument. The return value 
	 * would range from 1 to gridrows x gridcols, 100 in case of a 10 x 10 grid 
	 */
	public int toIndex(int gridcols){
		int index=0, pointy=this.getY()/40, pointx=this.getX()/40;
		
		if(pointy==1){
			index=pointx;
			
		}
		else{
			index=gridcols*(pointy-1)+pointx;
			
		}
		return index;
	}
	
	
	/*
	 * Returns a boolean indicating whether the Point p passed as an argument 
	 * is adjacent (immediately above, below, up or down) to the point on which 
	 * the method is invoked
	 */
	public boolean isAdjacent(Point p){
		boolean result=false;
		int index=this.toIndex(GraphCols);
		int pindex=p.toIndex(GraphRows);
		if(pindex==index+1 || pindex==index-1 || pindex==index+10 || pindex==index-10){
			result=true;
		}
		return result;
	}
	
	public void setAdjacencyList(){
		int gridcols, gridrows;
		gridcols=GraphCols;
		gridrows=GraphRows;
		int index=this.toIndex(gridcols);
				
		 if (index%gridcols!=0 && index%gridcols!=1 && index>gridcols && index< (gridcols*gridrows)-(gridcols-1)){
			 // for all points not on the border of the grid
			 adjacencyList.add(new Integer(index-gridcols));
			 adjacencyList.add(new Integer(index-1));
			 adjacencyList.add(new Integer(index+1));
			 adjacencyList.add(new Integer(index+gridcols));
			 // diagonal points
			 /*adjacencyList.add(new Integer(index-(gridcols+1))); // Top Left Diagonal Point
			 adjacencyList.add(new Integer((index+1)-gridcols)); // Top Right Diagonal Point
			 adjacencyList.add(new Integer(index+(gridcols-1))); // Bottom Left Diagonal Point
			 adjacencyList.add(new Integer(index+(gridcols))+1); // Bottom Right Diagonal Point
*/			 
			 return;
		 }
		 else
		 if(index>1 && index<=(gridcols-1)){ // Top row excluding 1st and last point *** changed***
			 adjacencyList.add(new Integer(index-1));
			 adjacencyList.add(new Integer(index+1));
			 adjacencyList.add(new Integer(index+gridcols));
			 //diagonal points
			 /*adjacencyList.add(new Integer(index+gridcols-1)); // Bottom Left diagonal
			 adjacencyList.add(new Integer(index+gridcols+1)); // Bottom Right diagonal
*/			 return;
		 }
		 else
		 if(index > (gridcols*gridrows)-(gridcols-1) && index < gridrows*gridcols){
			 //Bottom most row excluding 1st and last point
			 adjacencyList.add(new Integer(index-1));
			 adjacencyList.add(new Integer(index+1));
			 adjacencyList.add(new Integer(index-gridcols));
			 //diagonal points
			 /*adjacencyList.add(new Integer(index-gridcols-1));
			 adjacencyList.add(new Integer(index+1-gridcols));*/
			 return;
		 }
		 else
		 if(index%gridcols==1 && index > 1 && index <(gridcols*gridrows)-(gridcols-1)){
			 // Left most column = first column of the grid excluding 1st and last point
			 adjacencyList.add(new Integer(index-gridcols));
			 adjacencyList.add(new Integer(index+1));
			 adjacencyList.add(new Integer(index+gridcols));
			 //diagonal points
		/*	 adjacencyList.add(new Integer(index-gridcols+1));
			 adjacencyList.add(new Integer(index+gridcols+1));*/
			 return;
		 }
		 else
		 if(index%gridcols==0 && index!= gridcols && index!=gridrows*gridcols){
			 // Right most column= last column of the grid excluding first and last point
			 adjacencyList.add(new Integer(index-gridcols));
			 adjacencyList.add(new Integer(index-1));
			 adjacencyList.add(new Integer(index+gridcols));
			 //diagonal points
			 /*adjacencyList.add(new Integer(index-(gridcols+1)));
			 adjacencyList.add(new Integer(index+gridcols-1));*/
			 return;
		 }	
		 else
		if(index==gridcols-(gridcols-1)){ // gridcols-(gridcols-1) = 1 Left Top corner
			 adjacencyList.add(new Integer(index+1));
			 adjacencyList.add(new Integer(index+gridcols));
			 //adjacencyList.add(new Integer(index+gridcols+1));
			 return;
		 }
		else
		if(index==(gridcols*gridrows-(gridcols-1))){// gridcols*gridrows-(gridcols-1))=10 * 10 - (10-1)=100 - 9=91 
			// Bottom Left corner of the grid
			adjacencyList.add(new Integer(index-gridcols));
			adjacencyList.add(new Integer(index+1));
			//adjacencyList.add(new Integer(index-gridcols+1));
			return;
		 }
		else
		if(index==gridcols){// Top Right corner of grid
			adjacencyList.add(new Integer(index-1));
			adjacencyList.add(new Integer(index+gridcols));
			//adjacencyList.add(new Integer(index+gridcols-1));
			return;
		 }
		else
		 if(index==gridcols*gridrows){ // Bottom Right corner of grid
			 adjacencyList.add(new Integer(index-1));
			 adjacencyList.add(new Integer(index-gridcols));
			 //adjacencyList.add(new Integer(index-gridcols-1));
			 return;
		 }
	}
  public ArrayList<Integer> getAdjacencyList(){
	  this.adjacencySort();
	  return this.adjacencyList;
  }
  public int randomAdjacentPoint(){
	  int result=0, ran;
	  Random rand = new Random();
	  ran=rand.nextInt(this.adjacencyList.size()); // generates a random integer  between 0 and the size of adjacency list 
	  result = this.adjacencyList.get(ran).intValue();
	  return result;
  }
 public boolean contains(Integer index){
	 boolean contains=false;
	 for(int i=0; i< this.adjacencyList.size();i++){
		 if(index.intValue()==this.adjacencyList.get(i).intValue()){
			 contains=true;
			 break;
		 } 	 
	 }
	 return contains; 
 }
 
 public boolean equals(Object obj){
	 boolean result=false;
	 Point p= (Point) obj;
	 if(this.x==p.x && this.y== p.y && this.adjacencyList.size()==p.adjacencyList.size()){
		result=true; 
	 }
	 return result;
 }
 private void adjacencySort(){
     Collections.sort(this.adjacencyList, new Comparator<Integer>(){
    	public int compare(Integer i1, Integer i2){
    		return i1.compareTo(i2);
    	}
     });
	 
 }
 
 public static ArrayList<Integer> generateSeed(int length){
	 ArrayList<Integer> ary = new ArrayList<Integer>();
	 int counter=0;
	 Random rnd = new Random();
	 int index= (GraphCols * GraphRows)/2; //1+ rnd.nextInt(GraphCols * GraphRows); // generates a random number between 0 and total number of points
	 ary.add(index); // adds the randomly generated point to the array ary
	 counter++; 
	 Point p= Point.toPoint(index, GraphCols); // converts the randomly generated number to point so that its adjacency list could be obtained
	 while(counter<length){
		 index=p.randomAdjacentPoint();  // obtains a random neighbour of point p  
		 if(!ary.contains(index)){ // do not add the random neighbour if its already in the array ary
			 ary.add(index);
			 counter++;
			 p= Point.toPoint(index, GraphCols); // reset the point p to the random neighbour
		 }
	 }
	 return ary;
 }
 
 
 public static ArrayList<Integer> generateRP(int length){
	 ArrayList<Integer> ary = new ArrayList<Integer>();
	 int counter=0;
	 Random rnd = new Random();
	 int index= 1+ rnd.nextInt(GraphCols * GraphRows); // generates a random number between 0 and total number of points
	 ary.add(index); // adds the randomly generated point to the array ary
	 counter++; 
	 Point p= Point.toPoint(index, GraphCols); // converts the randomly generated number to point so that its adjacency list could be obtained
	 while(counter<length){
		 index=p.randomAdjacentPoint();  // obtains a random neighbour of point p  
		 if(!ary.contains(index)){ // do not add the random neighbour if its already in the array ary
			 ary.add(index);
			 counter++;
			 p= Point.toPoint(index, GraphCols); // reset the point p to the random neighbour
		 }
	 }
	 return ary;
 }
 /*public static ArrayList<Integer> generatePath(){
	 Point p, temp;
	 ArrayList<Integer> res= new ArrayList<Integer>();
	 int start= Graph.Seed.get(0);
	 int end= Graph.Seed.get(Graph.Seed.size()-1);
	 ArrayList<Integer> prefixp = new ArrayList<Integer>();
	 do{
		 prefixp=generateRP(Graph.Seed.size()+1);
		 temp = Point.toPoint(prefixp.get(prefixp.size()-1), GraphCols);
	 }while(temp.toIndex(GraphCols)!=start);
	 prefixp.remove(prefixp.size()-1);
	 res.addAll(prefixp);
	 res.addAll(Graph.Seed);	 
	 ArrayList<Integer> suffixp = new ArrayList<Integer>();
	 do{
		 suffixp=generateRP(Graph.Seed.size()+1);
		 temp=Point.toPoint(suffixp.get(0), GraphCols);
	 }while(temp.toIndex(GraphCols)!=end);
	 suffixp.remove(0);
	 res.addAll(suffixp);
	 p=null;
	 return res;
 }*/
 /*
	public static void main(String[] args){
		 
		for(int i=1; i<=25;i++){
			Point p= Point.toPoint(i, 5);
			System.out.println("point p: " + p.toIndex(5));
			System.out.println("Adjacent points of p");
			for(Integer j: p.adjacencyList){
				System.out.print(j + "\t");	
				}
			System.out.println();
		} 	
	 }*/
}
