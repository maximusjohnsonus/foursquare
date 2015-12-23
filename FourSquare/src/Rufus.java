
public class Rufus {
	Point location;
	double viewStep = 0.03;
	double step = 3;
	
	public Rufus(){
		//location=new Point()
	}
	public Rufus(Point location){
		this.location=location;
	}
	/*public void moveForward(double numSteps){
		pov.changeX(numSteps*step*Math.cos(pov.yaw));
		pov.changeY(numSteps*step*Math.sin(pov.yaw));
	}
	public void moveSideways(double numSteps){
		pov.changeX(-1*numSteps*step*Math.sin(pov.yaw));
		pov.changeY(numSteps*step*Math.cos(pov.yaw));
	}
	public void changeYaw(double numSteps){
		pov.changeYaw(numSteps*viewStep);
	}	
	public void changePitch(double numSteps){
		pov.changePitch(numSteps*viewStep);
	}*/	

}
