import java.util.ArrayList;

public class Threebject {
	int edges[]; //indices of edges in Fourbject's edges[][] list
	public Threebject(int edges[]){
		this.edges = edges;
	}
	public ArrayList<Point> draw(Point intercepts[]){
		//System.out.println(intercepts);
		ArrayList<Point> p = new ArrayList<Point>();
		for(int i=0; i<edges.length; i++){
			if(intercepts[edges[i]]!=null){
				p.add(intercepts[edges[i]]); //TODO: make the order correct so it's convex (maybe when you graph?)
			}
		}
		if(p.size()>0){
			return p;
		} else {
			return null;
		}
	}
	public String toString(){
		if(edges.length==0)
			return "{}";
		String s="{"+edges[0];
		for(int i=1; i<edges.length; i++){
			s+=","+edges[i];
		}
		return s+"}";
	}
}