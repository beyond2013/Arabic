package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.jqurantree.arabic.ArabicText;

public class DecodeResult {

	public String [] Tokens;
	
	public void populateArray(String filename){
		File file = new File(filename);
		String res = ReadWriteTextFile.getContents(file);
		String [] lines = res.split("\n");
		Tokens = new String[lines.length];
		int count=0;
		for(String s:lines){
			String [] words= s.split("\t");
			Tokens[count]=words[1];
			count++;
		}	
	}
	
	public void Decode(String filenamein, String filenameout){
		File filein = new File(filenamein);
		File fileout = new File(filenameout);
		 try {
		      //use buffering, reading one line at a time
		      //FileReader always assumes default encoding is OK!
		      BufferedReader input =  new BufferedReader(new FileReader(filein));
		      try {
		        String line = null; 
		        while (( line = input.readLine()) != null){
		        	if(line.length()==0 || !line.startsWith("(")) continue;
		          // verse[0]= verse[1]= verse[2]= verse[3]=
		          String[] verse = line.split("\t");
		          String temp = verse[0].toString().replace("(", "").replace(")", "");
		          String [] tmpary = temp.split(" ");
		          String str="";
		          for(String s:tmpary){
		        	  try{
		        		  ArabicText tmp= ArabicText.fromBuckwalter(s);
		        	  }
		        	  catch(Exception e){
		        		  
		        	  }
		        	  	str+=Tokens[Integer.parseInt(s)-1];
		        	  
		        	  	str+=" ";
		          }
		          //System.out.println(str);
		          
		          //String [] loc = verse[1].replace("[", "").replace("]", "").split(",");
		          ReadWriteTextFile.setContents(fileout, verse[1] +"\t" + verse[2] + "\t" + verse[3]+ "\t" +str + "\n");
		          
		          
		        }
		      }
		      finally {
		        input.close();
		      }
		    }
		    catch (IOException ex){
		      ex.printStackTrace();
		    }
		
		
			
	}
		
		
	public static void main(String[] args) {
		
		DecodeResult obj = new DecodeResult();
		obj.populateArray("DistToken.txt");
		obj.Decode("./AFS.txt", "./DecodedResults.txt");
		System.out.println("Finished decoding results");
	}

}
