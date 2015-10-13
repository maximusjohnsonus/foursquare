
public class POV {
	double x;
	double y;
	double z; //vertical (gravity)
	double w; //fourth D
	double yaw; //θ in xy plane
	double pitch; //θ in xz plane
	double yacht; //θ in xw plane
	public POV(){
		x=0;
		y=0;
		z=0;
		w=0;
		yaw=0;
		pitch=0;
		yacht=0;
	}
	public POV(double x, double y, double z, double w, double yaw, double pitch, double yacht){
		this.x=x;
		this.y=y;
		this.z=z;
		this.w=w;
		this.yaw=yaw;
		this.pitch=pitch;
		this.yacht=yacht;
	}
	public double changeX(double deltaX){
		x+=deltaX;
		return x;
	}
	public double changeY(double deltaY){
		y+=deltaY;
		return y;
	}
	public double changeZ(double deltaZ){
		z+=deltaZ;
		return z;
	}
	public double changeW(double deltaW){
		w+=deltaW;
		return w;
	}
	public double changeYaw(double deltaYaw){
		yaw+=deltaYaw;
		return yaw;
	}
	public double changePitch(double deltaPitch){
		pitch+=deltaPitch;
		return pitch;
	}
	public double changeYacht(double deltaYacht){
		yacht+=deltaYacht;
		return yacht;
	}
	
	public String toString(){
		return "("+x+","+y+","+z+","+w+"), ("+yaw+","+pitch+","+yacht+")";
	}

}
