
public class Point {
	public int dim; //p.length
	public double x; //p[0]
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
	/*public Point(double[]coords){
		this.p=coords;
		dim=coords.length;
	}*/
	
	boolean isClockwiseOf(Point b, Point center){ //Note: only uses x and y
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
