import java.awt.Color;
import java.util.ArrayList;

public class Threebject {
	int edges[]; //indices of edges in Fourbject's edges[][] list
	Color color;
	public Threebject(int edges[]){
		this.edges = edges;
		this.color=new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
	}
	public Polygon draw(Point intercepts[]){
		Polygon p = new Polygon(this.color);
		for(int i=0; i<edges.length; i++){
			if(intercepts[edges[i]]!=null){
				p.addPoint(intercepts[edges[i]]);
			}
		}
		if(p.numPoints()>0){
			return p;
		} else {
			return null;
		}
	}
	
	public boolean equals(Threebject t){
		if(this.edges.length==t.edges.length){
			for(int edgeIndex=0; edgeIndex<this.edges.length; edgeIndex++){
				if(this.edges[edgeIndex]!=t.edges[edgeIndex]){
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
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
