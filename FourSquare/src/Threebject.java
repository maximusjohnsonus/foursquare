import java.awt.Color;

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
