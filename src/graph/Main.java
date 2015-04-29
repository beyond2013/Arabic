package graph;

public class Main {
	public static void main(String[] args) {
		AFS obj=new AFS("transch2.txt", "AFS.txt", 2);
		obj.AFSProcess();
		System.out.println("Finished Executing AFS....");
	}

}
