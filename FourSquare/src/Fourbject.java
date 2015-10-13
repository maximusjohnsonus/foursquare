import java.awt.*;
import java.util.*;

public class Fourbject {
	double FOV = 250 / Math.sqrt(3); //120° FOV
	double pts[][]; //size [n][4] //change to faces
	
	public Fourbject(double pts[][]){
		this.pts=pts;
	}
	
	public void addPt(double pt[]){
		double[][] newPts=Arrays.copyOf(pts, pts.length+1);
		newPts[newPts.length-1]=pt;
		this.pts=newPts;
	}
	
	public Polygon shiftedView(POV pov){
		//return yaw and pitch as x and y for each pt.
		Polygon p=new Polygon();
		for(int i=0; i<pts.length; i++){
			//drawing: https://drive.google.com/file/d/0B8HuDkP8KDZ9TjFWSkdKc2hTUDQ/
			
			Point pt=new Point((int)(FOV*Math.tan(pov.yaw - Math.atan((pts[i][1] - pov.y)/(pts[i][0] - pov.x)))), 
					(int)(FOV*Math.tan(pov.pitch - Math.atan((pts[i][2] - pov.z)/(pts[i][0] - pov.x)))));

			//Point pt=new Point((int)(FOV*((/*Math.atan*/((pts[i][1]-pov.y)/(pts[i][0]-pov.x)) - pov.yaw)%(2*Math.PI))), -1*(int)(FOV*((/*Math.atan*/((pts[i][2]-pov.z)/(pts[i][0]-pov.x)) - pov.pitch)%(2*Math.PI))));
			/*if(pts[i][0]<pov.x){
				pt.x*=-1;
				pt.y*=-1;
			}*/
			p.addPoint(pt.x,pt.y);
			//System.out.println(pt);
		}
		return p;
	}
	
	
	public double[][] transform(Trans t){
		pts = t.transatlantic(pts);
		return pts;
	}
	public double[][] shift(Shift s){
		for(int i=0; i<pts.length; i++)
			pts[i] = s.shift(pts[i]);
		return pts;
	}
	
	public void print(){
		for(int i=0; i<pts.length; i++){
			for(int j=0; j<pts[i].length; j++)
				System.out.print(pts[i][j]+" ");
			System.out.println();
		}
	}
}
