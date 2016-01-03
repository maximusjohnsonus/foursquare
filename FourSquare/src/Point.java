
public class Point {
	public int dim; //p.length
	public double x; //p[0] //Careful! These are hella convenient, but you have to make sure to update them when you do things like get shifted point
	public double y; //p[1]
	public double z; //p[2]
	public double w; //p[3]
	public double p[];
	public Point(double x, double y){
		dim = 2;
		p=new double[2];
		p[0]=x;
		p[1]=y;
		this.x=x;
		this.y=y;
	}
	public Point(double x, double y, double z){
		dim = 3;
		p=new double[3];
		p[0]=x;
		p[1]=y;
		p[2]=z;
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public Point(double x, double y, double z, double w){
		dim = 4;
		p=new double[4];
		p[0]=x;
		p[1]=y;
		p[2]=z;
		p[3]=w;
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
	}
	public double getCoord(int c){
		return p[c];
	}
	public Point(double[]coords){
		this.p=coords;
		this.dim=coords.length;
		switch(this.dim){ //populate x,y,z,w
			case 0:
				break;
			case 1:
				x=this.p[0];
				break;
			case 2:
				x=this.p[0];
				y=this.p[1];
				break;
			case 3:
				x=this.p[0];
				y=this.p[1];
				z=this.p[2];
				break;
			default:
				x=this.p[0];
				y=this.p[1];
				z=this.p[2];
				w=this.p[3];
				break;
		}
	}
	public void changeCoord(int coord, double delta){
		p[coord]+=delta;
		switch(coord){
			case 0:
				x=p[0];
				break;
			case 1:
				y=p[1];
				break;
			case 2:
				z=p[2];
				break;
			case 3:
				w=p[3];
				break;
		}
	}
	
	public Point relativeTo(Point origin){
		double newCoords[] = new double[this.p.length];
		for(int i=0; i<newCoords.length; i++){
			newCoords[i]=this.p[i]-origin.getCoord(i);
		}
		return new Point(newCoords);
	}
	public boolean isClockwiseOf(Point b, Point center){ //Note: only uses x and y
		//returns true if b is clockwise of this about center, 12:00 (x=0, y>0) is the dividing line
		if (this.x - center.x >= 0 && b.x - center.x < 0)
			return true;
		if (this.x - center.x < 0 && b.x - center.x >= 0)
			return false;
		if (this.x - center.x == 0 && b.x - center.x == 0) {
			if (this.y - center.y >= 0 || b.y - center.y >= 0)
				return this.y > b.y;
				return b.y > this.y;
		}

		// compute the cross product of vectors (center -> a) x (center -> b)
		double det = (this.x - center.x) * (b.y - center.y) - (b.x - center.x) * (this.y - center.y);
		if (det < 0)
			return true;
		if (det > 0)
			return false;

		// this and b are on the same line from the center
		// check which point is closer to the center
		double d1 = (this.x - center.x) * (this.x - center.x) + (this.y - center.y) * (this.y - center.y);
		double d2 = (b.x - center.x) * (b.x - center.x) + (b.y - center.y) * (b.y - center.y);
		return d1 > d2;
	}
	public boolean isBetween(Point a, Point b){ //TODO: optimize?
		/* i.x<=p0.x
		 * p1.x<=i.x
		 * |p0.x-i.x| + |i.x-p1.x| ≈ |p0.x-p1.x|
		 */
		for(int coord=0; coord<this.dim; coord++){
			if(! (Math.abs(a.getCoord(coord)-this.getCoord(coord))+Math.abs(b.getCoord(coord)-this.getCoord(coord))<=Math.abs(a.getCoord(coord)-b.getCoord(coord))+0.0000000001)){ //0.0...01 is because doubles are the worst. You could find min/max and do some more comparisons, might be faster
				return false;
			}
		}
		return true;
	}
	
	public boolean equals(Point p){
		if(p.dim!=this.dim){
			return false;
		}
		for(int coord=0; coord<this.dim; coord++){
			if(p.getCoord(coord)!=this.getCoord(coord)){
				return false;
			}
		}
		return true;
	}
	public String toString(){
		if(p.length==0)
			return "{}";
		String s="{"+p[0];
		for(int i=1; i<p.length; i++){
			s+=","+p[i];
		}
		s+="}";
		
		return s;
	}
}
