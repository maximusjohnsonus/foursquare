import java.util.*;

public class POV {
	Matrix pos; //position vector
	Matrix normal; //unit vector normal to view plane, defines forward, +z
	Matrix left;  //unit vector in view plane, defines left, +x
	Matrix up;   //unit vector in view plane, defines up, +y
				//normal ⟂ left ⟂ up
	
	public POV(){
		pos=new Matrix({0,0,0,0});
	}
	public POV(double x, double y, double z, double w, double nx, double ny, double nz, double nw){
		p=new KindVector(x,y,z,w);
		p=new KindVector(nx,ny,nz,nw);
	}
	public void changeX(double deltaX){
		p.set(0, p.e0+deltaX);
	}
	public void changeY(double deltaY){
		p.set(1, p.e1+deltaY);
	}
	public void changeZ(double deltaZ){
		p.set(2, p.e2+deltaZ);
	}
	public void changeW(double deltaW){
		p.set(3, p.e3+deltaW);
	}

}
