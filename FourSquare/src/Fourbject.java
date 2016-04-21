import java.util.ArrayList;
import java.awt.Color;

public class Fourbject {
	Point points[]; //all points in 4bject
	int edges[][]; //n×2, pairs of indices of points in points[]
	Threebject threebjects[]; //set of 3D "faces" of 4bject
	Color color;
	
	public Fourbject(Point points[], int edges[][], Threebject threebjects[]){
		this.points = points;
		this.edges = edges;
		this.threebjects = threebjects;
	}
	public Fourbject(Point points[], int edges[][], Threebject threebjects[], Color c){
		this.points = points;
		this.edges = edges;
		this.threebjects = threebjects;
		this.color=c;
	}
	public Fourbject(){
		
	}
	public void setPoints(Point points[]){
		this.points = points;
	}
	public void setEdges(int edges[][]){
		this.edges = edges;
	}
	public void setThreebjects(Threebject threebjects[]){
		this.threebjects = threebjects;
	}
	public void setColor(Color c){
		this.color=c;
	}
	
	public ArrayList<Polygon> draw(Point origin, double viewMatrix[][]){
		Point shiftedPoints[] = new Point[this.points.length]; //points relative to location of rufus
		for(int i=0; i<shiftedPoints.length; i++){
			shiftedPoints[i]=this.points[i].relativeTo(origin);
		}
		
		
		Point intercepts[] = new Point[edges.length];
		Point p0,p1;
		for(int i=0; i<edges.length; i++){ //calculate intercepts with all edges //TODO: optimize?
			
			p0=shiftedPoints[edges[i][0]];
			p1=shiftedPoints[edges[i][1]];
			double[] delta={p1.x-p0.x, p1.y-p0.y, p1.z-p0.z, p1.w-p0.w};
			int v=0; //index of {x,y,z,w} that you use to solve for t
			double dv = delta[0]<=1 ? delta[0] : 1/delta[0]; //temp var
			for(int d=1; d<4 /*delta.length*/; d++){ //make v the index of the item in delta[] closest to 1
				if( (delta[d]<=1 ? delta[d] : 1/delta[d])>dv ){
					dv = delta[d]<=1 ? delta[d] : 1/delta[d];
					v = d;
				}
			}
			double A[][] = new double[3][4];
			double b[][] = new double[3][1];
			//TODO: fill A,b with 0?
			int row=0;
			for(int j=0; j<4 /*delta.length*/; j++){
				if(j==v){
					continue;
				}
				A[row][j]=delta[v];
				A[row][v]=-delta[j];
				b[row][0]=p0.getCoord(j)*delta[v]-p0.getCoord(v)*delta[j];
				row++;
			}
			double mIntercept[][] = Matrix.rref(Matrix.augment(Matrix.multiply(A, viewMatrix),b)); //rref(A*V  b)
						
			if(mIntercept[2][2]<=1.0000000001 && mIntercept[2][2]>=0.9999999999){ //gahddam doubles //TODO: there must be a better way to do this. right?
				Point newIntercept3D = new Point(mIntercept[0][3],mIntercept[1][3],mIntercept[2][3]);
				double newIntercept4DArray[][]=Matrix.multiply(viewMatrix, new double[][]{{newIntercept3D.x},{newIntercept3D.y},{newIntercept3D.z}});
				Point newIntercept4D = new Point(newIntercept4DArray[0][0],newIntercept4DArray[1][0], newIntercept4DArray[2][0], newIntercept4DArray[3][0]);
				if(newIntercept4D.isBetween(p0, p1)){
					intercepts[i]= newIntercept3D;
				}
			}
		}
		
		ArrayList <Polygon> pgons=new ArrayList <Polygon> (0);
		for(Threebject t : threebjects){
			Polygon tbjPolygon = t.draw(intercepts);
			if(tbjPolygon!=null){
				if(this.color!=null){
					tbjPolygon.setColor(this.color);
				}
				pgons.add(tbjPolygon);
			}
		}
		return pgons;
	}
	/*public String toString(){
		String str=""
	}*/

}
