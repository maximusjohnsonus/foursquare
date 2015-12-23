//P17

import java.awt.*; 
import java.awt.event.KeyEvent; 
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.JFrame;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array; 


public class TheLittleEngineThatCould extends JFrame {        
	boolean isRunning = true; 
	int fps = 30; 
	double fov=500;
	//boolean wireframe = true;
	boolean wireframe = false;
	int windowWidth = 500; 
	int windowHeight = 500;
	Rufus rufus;
	ArrayList <Fourbject> objs=new ArrayList<>(0);
	double V[][]={{1,0,0},{0,1,0},{0,0,1},{0,0,0}};
	double vUp=0; //velocity up - used for jumping
	Color[] colors={Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.BLACK, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.PINK};

	BufferedImage backBuffer; 
	Insets insets; 
	InputHandler input; 

	public static void main(String[] args) { 
		TheLittleEngineThatCould game = new TheLittleEngineThatCould(); 
		game.run(); 
		System.exit(0); 
	} 


	public void run() { 
		initialize(); 
		rufus=new Rufus();
		Fourbject f=new Fourbject();
		int index=0;
		Point p[]= new Point[16];
		for(int i=0; i<=1; i++){
			for(int j=0; j<=1; j++){
				for(int k=0; k<=1; k++){
					for(int l=0; l<=1; l++){
						p[index]=new Point(i+3,j+0.5,k+0.5,l-0.5);
						index++;
					}
				}
			}
		}
		int edges[][] = {{0,1},{0,2},{1,3},{2,3},{0,4},{1,5},{4,5},{2,6},{4,6},{3,7},{5,7},{6,7},{0,8},{1,9},{8,9},{2,10},{8,10},{3,11},{9,11},{10,11},{4,12},{8,12},{5,13},{9,13},{12,13},{6,14},{10,14},{12,14},{7,15},{11,15},{13,15},{14,15}};
		
		Threebject tbj[]= new Threebject[8];
		tbj[0]=new Threebject(new int []{0,1,2,3,4,5,6,7,8,9,10,11}); //x=0
		tbj[1]=new Threebject(new int []{14,16,18,19,21,23,24,26,27,29,30,31}); //x=1
		tbj[2]=new Threebject(new int []{0,1,2,3,12,13,14,15,16,17,18,19}); //y=0
		tbj[3]=new Threebject(new int []{6,8,10,11,20,22,24,25,27,28,30,31}); //y=1
		tbj[4]=new Threebject(new int []{0,4,5,6,12,13,14,20,21,22,23,24}); //z=0
		tbj[5]=new Threebject(new int []{3,7,9,11,15,17,19,25,26,28,29,31}); //z=1
		tbj[6]=new Threebject(new int []{1,4,7,8,12,15,16,20,21,25,26,27}); //w=0
		tbj[7]=new Threebject(new int []{2,5,9,10,13,17,18,22,23,28,29,30}); //w=1
		
		f.setEdges(edges);
		f.setPoints(p);
		f.setThreebjects(tbj);
		objs.add(f);
		Fourbject g=new Fourbject();
		index=0;
		Point pg[]= new Point[16];
		for(int i=0; i<=1; i++){
			for(int j=0; j<=1; j++){
				for(int k=0; k<=1; k++){
					for(int l=0; l<=1; l++){
						pg[index]=new Point(i+5,j+0.8,k-1.9,l-0.5);
						index++;
					}
				}
			}
		}
		g.setEdges(edges);
		g.setPoints(pg);
		g.setThreebjects(tbj);
		objs.add(g);

		while(isRunning) { 
			long time = System.currentTimeMillis(); 

			update(); 
			draw(); 

			//  delay for each frame  -   time it took for one frame 
			time = (1000 / fps) - (System.currentTimeMillis() - time); 

			if (time > 0) { 
				try { 
					Thread.sleep(time); 
				} 
				catch(Exception e){} 
			} 
		} 

		setVisible(false); 
	} 

	void initialize() { 
		setTitle("4Square"); 
		setSize(windowWidth, windowHeight); 
		setResizable(false); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setVisible(true); 

		insets = getInsets(); 
		setSize(insets.left + windowWidth + insets.right, 
				insets.top + windowHeight + insets.bottom); 

		backBuffer = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB); 
		input = new InputHandler(this); 
	} 

	void update() { 
		/*if(rufus.pov.z!=0 || vUp!=0){
			rufus.pov.changeZ(vUp);
			vUp-=.5;
			if(rufus.pov.z<0){
				rufus.pov.z=0;
				vUp=0;
			}
		}*/
		/*if (input.isKeyDown(KeyEvent.VK_W)) { 
			rufus.moveForward(1); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_S)) { 
			rufus.moveForward(-1); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_A)) { 
			rufus.moveSideways(-1); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_D)) { 
			rufus.moveSideways(1); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_LEFT)) { 
			rufus.changeYaw(-1); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_RIGHT)) { 
			rufus.changeYaw(1); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_UP)) { 
			rufus.changePitch(1); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_DOWN)) { 
			rufus.changePitch(-1); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_SPACE)) { 
			vUp=5;
			System.out.println(rufus.pov);
		}*/
		
	} 

	void draw() {
		Graphics g = getGraphics(); 

		Graphics bbg = backBuffer.getGraphics(); 

		bbg.setColor(Color.WHITE); 
		bbg.fillRect(0, 0, windowWidth, windowHeight); 
		
		bbg.translate(windowWidth/2, windowHeight/2); //Fixes the coord system
		//bbg.setColor(Color.BLACK); 
		//bbg.drawOval((int)rufus.pov.x, 10, 20, 20);
		ArrayList<ArrayList<Point>> fbjFaces = new ArrayList<ArrayList<Point>>(0);
		for(int i=0; i<objs.size(); i++){
			//System.out.println("fbjFaces for fbj "+i+":"+fbjFaces);
			fbjFaces.addAll(objs.get(i).draw(V));
		}
		int color=0;
		for(int i=0; i<fbjFaces.size(); i++){
			for(ArrayList<Point> polygon:orderFaces(fbjFaces)){ //TODO: order so that the closest objects are drawn first (HOW????)
				bbg.setColor(colors[color%colors.length]); 
				color++;
				//System.out.println("drawing:"+polygon);
				ArrayList<Point> dispPts=new ArrayList<Point>(0);
				double xTotal=0; //for center
				double yTotal=0;
				for(Point point:polygon){
					dispPts.add(new Point(point.y/point.x,point.z/point.x)); //y,z are sidewaysness and upness to pt, and x is dist to pt
					xTotal+=point.y/point.x;
					yTotal+=point.z/point.x;
				}
				Point center = new Point(xTotal/dispPts.size(), yTotal/dispPts.size());
				for(int sortCount=1; sortCount<dispPts.size(); sortCount++){ //insertion sort by clockwise //TODO: optimize?
					Point hold=dispPts.get(sortCount);
					int backCount=sortCount;
					while(backCount>0 && dispPts.get(backCount-1).isClockwiseOf(hold, center)){
						dispPts.set(backCount, dispPts.get(backCount-1));
						backCount--;
					}
					dispPts.set(backCount, hold);
				}
				/*for i = 1 to length(A) - 1
					    x = A[i]
					    j = i
					    while j > 0 and A[j-1] > x
					        A[j] = A[j-1]
					        j = j - 1
					    end while
					    A[j] = x
					 end for*/
				//make arraylist of 2Dpoints (p/p.x)
				//sort into order
				//fill x and yPoints[]
				
				
				int xPoints[]=new int[polygon.size()];
				int yPoints[]=new int[polygon.size()];
				for(int j=0; j<dispPts.size(); j++){ 
					Point p=dispPts.get(j);
					xPoints[j]=(int)(fov*p.x);
					yPoints[j]=(int)(fov*p.y);
				}
				if(wireframe)
					bbg.drawPolygon(xPoints, yPoints, polygon.size());
				else
					bbg.fillPolygon(xPoints, yPoints, polygon.size());
			}
		}
		g.drawImage(backBuffer, insets.left, insets.top, this); 
		//isRunning=false;
	} 
	ArrayList<ArrayList<Point>> orderFaces(ArrayList<ArrayList<Point>> faces){
		for(int sortCount=1; sortCount<faces.size(); sortCount++){ //insertion sort, behind to in front of //TODO: optimize?
			ArrayList<Point> hold=faces.get(sortCount);
			int backCount=sortCount;
			while(backCount>0 && isBehind(hold, faces.get(backCount-1))){ //TODO: what you should really do is draw the closest first and not paint over any pixels
																		  //Or figure out z-buffering
				faces.set(backCount, faces.get(backCount-1));
				backCount--;
			}
			faces.set(backCount, hold);
		}
		return faces;
	}
	boolean isBehind(ArrayList<Point> a, ArrayList<Point> b){ //Painter's algorithm / depth sort. Is a behind b?
		double aDmax=0; //D is the equivalent (I think?) of Z in traditional painter's algorithm 
		double aDmin=0;
		double bDmax=0;
		double bDmin=0;
		for(Point p:a){
			double d=p.x*p.x+p.y*p.y+p.z*p.z; //distance squared to point
			if(d>aDmax){
				aDmax=d;
			}
			if(d<aDmin){
				aDmin=d;
			}
		}
		for(Point p:b){
			double d=p.x*p.x+p.y*p.y+p.z*p.z; //distance squared to point
			if(d>bDmax){
				bDmax=d;
			}
			if(d<bDmin){
				bDmin=d;
			}
		}
		if(aDmin>bDmax){ //if the closest point of a is further than the farthest point of b
			return true; //then a is behind b
		}
		if(bDmin>aDmax){ //if the closest point of b is further than the farthest point of a
			return false;//then b is behind a
		}
		//TODO: actually implement the majority of the algorithm
		if(aDmax==bDmax){ //this is just a stopgap until the above is completed
			return aDmin>bDmin;
		} else {
			return aDmax>bDmax;
		}
	}
}
	


