//P17

import java.awt.*; 
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.JFrame;



public class TheLittleEngineThatCould extends JFrame {        
	boolean isRunning = true; 
	boolean paused=false;
	int fps = 30; 
	int windowWidth = 500; 
	int windowHeight = 500;
	double fov=500;
	Robot wallE;
	Rufus rufus;
	ArrayList <Fourbject> objs=new ArrayList<>(0);
	Color background = Color.WHITE;
	double[][] zBuffer = new double[windowHeight][windowWidth]; //holds the depth of each pixel
	Color[][] colors = new Color[windowHeight][windowWidth]; //holds the color of each pixel
	private static final double INFINITY = Double.MAX_VALUE; //infinite depth
	
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
		try {
			wallE=new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		wallE.mouseMove(this.getLocationOnScreen().x+this.windowWidth/2, this.getLocationOnScreen().y+this.windowHeight/2); //move cursor to center
		this.setCursor(this.getToolkit().createCustomCursor( new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ), new java.awt.Point(), null ) ); //hide cursor
		
		//objs.add(new Fourbject(new Point[]{new Point(3,-1,-1,-1),new Point(3,-1,1,-1),new Point(3,1,1,-1),new Point(3,1,-1,-1),new Point(3,-1,-1,1),new Point(3,-1,1,1),new Point(3,1,1,1),new Point(3,1,-1,1)},new int[][]{{0,1},{1,2},{2,3},{3,0},{4,5},{5,6},{6,7},{7,4},{0,4},{1,5},{2,6},{3,7}},new Threebject[]{new Threebject(new int[]{0,1,2,3,4,5,6,7,8,9,10,11})}));
		objs.add(new Tesseract(new Point(3,-1,-1,-1),new Point(5,1,2,3)));
		objs.add(new Tesseract(new Point(-3,2,1,-0.5),new Point(-6,5,0.5,1)));
		Point p[]= {new Point(1/Math.sqrt(6),4+1/Math.sqrt(3),1,1/Math.sqrt(10)),
					new Point(1/Math.sqrt(6),4+1/Math.sqrt(3),-1,1/Math.sqrt(10)),
					new Point(1/Math.sqrt(6),4-2/Math.sqrt(3),0,1/Math.sqrt(10)),
					new Point(-Math.sqrt(3/2),4+0,0,1/Math.sqrt(10)),
					new Point(0,4+0,0,-2*Math.sqrt(2/5))};
		int edg[][] = {{0,1},{0,2},{0,3},{0,4},{1,2},{1,3},{1,4},{2,3},{2,4},{3,4}}; //all combos
		Threebject t[]={new Threebject(new int[]{4,5,6,7,8,9}), //all w/o point 0
						new Threebject(new int[]{1,2,3,7,8,9}), //all w/o point 1
						new Threebject(new int[]{0,2,3,5,6,9}), //all w/o point 2
						new Threebject(new int[]{0,1,3,4,6,8}), //all w/o point 3
						new Threebject(new int[]{0,1,2,4,5,7})};//all w/o point 4
		
		objs.add(new Fourbject(p,edg,t)); //5-cell

		while(isRunning) { 
			long time = System.currentTimeMillis(); 

			update();
			draw();

			if(Lumberman.timeLogLevel>=1){
				System.out.println("frame time: "+( System.currentTimeMillis() - time));
			}
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
		if (input.isKeyDown(KeyEvent.VK_W)) { 
			rufus.moveForwards(1); 
		}
		if (input.isKeyDown(KeyEvent.VK_S)) { 
			rufus.moveForwards(-1); 
		}
		if (input.isKeyDown(KeyEvent.VK_D)) { 
			rufus.moveSideways(1); 
		}
		if (input.isKeyDown(KeyEvent.VK_A)) { 
			rufus.moveSideways(-1); 
		}
		if (input.isKeyDown(KeyEvent.VK_SPACE)) { 
			rufus.moveUpwards(1);
			//rufus.stepZ(1); 
		}
		if (input.isKeyDown(KeyEvent.VK_SHIFT)) { 
			rufus.moveUpwards(-1);
			//rufus.stepZ(-1); 
		}
		if (input.isKeyDown(KeyEvent.VK_R)) { 
			rufus.stepW(1); 
		}
		if (input.isKeyDown(KeyEvent.VK_F)) { 
			rufus.stepW(-1); 
		}
		
		
		if (input.isKeyDown(KeyEvent.VK_LEFT)) { 
			rufus.rotateXWViewPlane(-1); 
		}
		if (input.isKeyDown(KeyEvent.VK_RIGHT)) { 
			rufus.rotateXWViewPlane(1); 
		}
		if (input.isKeyDown(KeyEvent.VK_UP)) { 
			rufus.rotateZWViewPlane(1); 
		}
		if (input.isKeyDown(KeyEvent.VK_DOWN)) { 
			rufus.rotateZWViewPlane(-1); 
		}
		if(input.isKeyDown(KeyEvent.VK_ESCAPE)){
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			while(input.isKeyDown(KeyEvent.VK_ESCAPE)){
				try { 
					Thread.sleep(1000/fps); 
				} 
				catch(Exception e){}
			}
			while(!input.isKeyDown(KeyEvent.VK_ESCAPE)){
				try { 
					Thread.sleep(1000/fps); 
				} 
				catch(Exception e){}
			}
			while(input.isKeyDown(KeyEvent.VK_ESCAPE)){
				try { 
					Thread.sleep(1000/fps); 
				} 
				catch(Exception e){}
			}
			this.setCursor(this.getToolkit().createCustomCursor( new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ), new java.awt.Point(), null ) ); //hide cursor
			wallE.mouseMove(this.getLocationOnScreen().x+this.windowWidth/2, this.getLocationOnScreen().y+this.windowHeight/2);

		}
		if((input.mouseX!=this.windowWidth/2 || input.mouseY!=this.windowHeight/2) && !paused){
			rufus.rotateXYViewPlane(input.mouseX-this.windowWidth/2);
			rufus.rotateForwardUpViewPlane(this.windowHeight/2-input.mouseY);
			wallE.mouseMove(this.getLocationOnScreen().x+this.windowWidth/2, this.getLocationOnScreen().y+this.windowHeight/2);
		}
		/*if (input.isKeyDown(KeyEvent.VK_SPACE)) { 
			vUp=5;
		}*/
		
	} 

	void draw() {
		Graphics g = getGraphics(); 

		/*Graphics bbg = backBuffer.getGraphics(); 

		bbg.setColor(Color.WHITE); 
		bbg.fillRect(0, 0, windowWidth, windowHeight); 
		
		bbg.translate(windowWidth/2, windowHeight/2); //Fixes the coord system //TODO: optimize*/
		
		//draw everything using z-buffer
		for(int x=0; x<windowWidth; x++){
			for(int y=0; y<windowHeight; y++){
				colors[x][y] = this.background;
				zBuffer[x][y] = INFINITY;
			}
		}
		long objTime = System.currentTimeMillis();
		for(int i=0; i<objs.size(); i++){
			if(Lumberman.timeLogLevel>=2){
				System.out.println("Times for obj #"+i);
			}
			addAllToZBuffer(objs.get(i).draw(rufus.location, rufus.viewMatrix));
			if(Lumberman.timeLogLevel>=2){
				System.out.println("total: "+(System.currentTimeMillis()-objTime));
				objTime = System.currentTimeMillis();
			}
		}
		
		for(int x=0; x<windowWidth; x++){
			for(int y=0; y<windowHeight; y++){
				backBuffer.setRGB(x, y, colors[x][y].getRGB());
			}
		}
		g.drawImage(backBuffer, insets.left, insets.top, this);
		if(Lumberman.timeLogLevel>=2){
			System.out.println("Time for drawing: "+(System.currentTimeMillis()-objTime));
		}
		//TODO: add a hypercube that shows the direction you're looking
		
		//draw everything using painter's algorithm and sorting faces
		/*ArrayList<ArrayList<Point>> fbjFaces = new ArrayList<ArrayList<Point>>(0);
		for(int i=0; i<objs.size(); i++){
			fbjFaces.addAll(objs.get(i).draw(rufus.location, rufus.viewMatrix));
		}
		
		int color=0; 
		for(ArrayList<Point> polygon:orderFaces(fbjFaces)){
			bbg.setColor(colors[color%colors.length]); 
			color++;
			ArrayList<Point> dispPts=new ArrayList<Point>(0);
			double xTotal=0; //for center
			double yTotal=0;
			for(Point point:polygon){
				Point newPoint=new Point(point.y/point.x,point.z/point.x);
				if(point.x<=0){
					newPoint=new Point(point.y/(1/420),point.z/(1/420)); //420 for luck //TODO: deal with close object rendering intelligently
				}
				dispPts.add(newPoint); //y,z are sidewaysness and upness to pt, and x is dist to pt
				xTotal+=newPoint.x;
				yTotal+=newPoint.y;
			}
			Point center = new Point(xTotal/dispPts.size(), yTotal/dispPts.size());
			for(int sortCount=1; sortCount<dispPts.size(); sortCount++){ //insertion sort points by clockwise order //TODO: optimize?
				Point hold=dispPts.get(sortCount);						 //so that they make a nice convex polygon
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
					 end for* /
			//make arraylist of 2Dpoints (p/p.x)
			//sort into order
			//fill x and yPoints[]


			int xPoints[]=new int[polygon.size()];
			int yPoints[]=new int[polygon.size()];
			for(int j=0; j<dispPts.size(); j++){ 
				Point p=dispPts.get(j);
				xPoints[j]=(int)(fov*p.x);
				yPoints[j]=-(int)(fov*p.y); //the - makes the signs work out
			}
			bbg.fillPolygon(xPoints, yPoints, polygon.size());
			bbg.setColor(Color.BLACK);
			bbg.drawPolygon(xPoints, yPoints, polygon.size());
			bbg.drawChars(new char[]{Character.forDigit(color, 10)}, 0, 1, (int)(fov*center.x), -(int)(fov*center.y));
			
		}
		g.drawImage(backBuffer, insets.left, insets.top, this); */
		//isRunning=false;
	} 
	
	//Z-buffer rendering
	//I took a lot of ideas from https://github.com/FlightOfGrey/3D-z-buffer/tree/master/src
	private void addAllToZBuffer(ArrayList<Polygon> polygons) {
		long polygonTime=System.currentTimeMillis();
		for(Polygon polygon:polygons){
			long taskTime=System.currentTimeMillis();
			Color c = polygon.getColor();
			polygon.convertToScreenCoords(fov);	//make the vertices of the polygon (which have normal coords) into screen coords (fov*y/x, fov*z/x) with depth (x) data 
			if(Lumberman.timeLogLevel>=4){
				System.out.println("convert: "+(System.currentTimeMillis()-taskTime));
				taskTime = System.currentTimeMillis();
			}
			polygon.orderPoints(); //order so the polygon is convex
			if(Lumberman.timeLogLevel>=4){
				System.out.println("order: "+(System.currentTimeMillis()-taskTime));
				taskTime = System.currentTimeMillis();
			}
			double[][] stripes = polygon.getStripes(); //make the polygon into a series of horizontal stripes 1 unit tall, with endpoints with same data as above (screenX, screenY, depth)
			if(Lumberman.timeLogLevel>=4){
				System.out.println("getStripes: "+(System.currentTimeMillis()-taskTime));
				taskTime = System.currentTimeMillis();
			}
			int minY=(int)polygon.getMinY();
			
			for(int stripeCount = (minY>-windowHeight/2 ? 0 : -windowHeight/2-minY+1) /*this is to not go through stripes above the screen*/
					; stripeCount<stripes.length && stripeCount+minY<=windowHeight/2 /*this is to not go through stripes below the screen*/
					; stripeCount++){
				int x, maxX, y;
				double z;
				double dz=(stripes[stripeCount][3]-stripes[stripeCount][1])/(stripes[stripeCount][2]-stripes[stripeCount][0]);

				if((int)stripes[stripeCount][0]<=(int)stripes[stripeCount][2]){
					x=(int)stripes[stripeCount][0];
					maxX=(int)stripes[stripeCount][2];
					z=stripes[stripeCount][1];
				} else {
					x=(int)stripes[stripeCount][2];
					maxX=(int)stripes[stripeCount][0];
					z=stripes[stripeCount][3];
				}
				if(x<-windowWidth/2){
					z+=dz*(-windowWidth/2-x);
					x=-windowWidth/2;
				}
				while(x<=maxX && x<windowWidth/2){ //iterate through each line by screenX, changing your depth as you go along
					y=-(stripeCount+minY)+windowHeight/2;
					if(z>=0 && x>=-windowWidth/2 && z<zBuffer[x+windowWidth/2][y]){ //if point is closer than other one at same point on screen, put it in the z-buffer and put its color in the color array
						zBuffer[x+windowWidth/2][y]=z; //TODO: deal with very close objects better (1/z), use shorts instead of doubles for zBuffer
						colors[x+windowWidth/2][y]=c; //TODO: test that x and y are never off the screen
					}
					x++;
					z+=dz;
				}
			}
			if(Lumberman.timeLogLevel>=4){
				System.out.println("Zbuffer: "+(System.currentTimeMillis()-taskTime));
				taskTime = System.currentTimeMillis();
			}
			if(Lumberman.timeLogLevel>=3){
				System.out.println("total polygon: "+(System.currentTimeMillis()-polygonTime));
				polygonTime = System.currentTimeMillis();
			}
		}
	}


	private ArrayList<ArrayList<Point>> orderFaces(ArrayList<ArrayList<Point>> faces){ //returns the faces in the correct order for displaying, back to front
		for(int sortCount=1; sortCount<faces.size(); sortCount++){ //the problem with doing a sort and not comparing every face to every other face is that order isn't very transitive. e.g. these: _/- (the / extends beyong the _). _ is in front of -, but in the ordering should be the furthest behind
			ArrayList<Point> hold=faces.get(sortCount); //TODO: optimize?
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
	private boolean isBehind(ArrayList<Point> a, ArrayList<Point> b){ //Painter's algorithm / depth sort. Is a behind b?
		double aXmax=0;
		double aXmin=0;
		double bXmax=0;
		double bXmin=0;
		for(Point p:a){
			if(p.x>aXmax){
				aXmax=p.x;
			}
			if(p.x<aXmin){
				aXmin=p.x;
			}
		}
		for(Point p:b){
			if(p.x>bXmax){
				bXmax=p.x;
			}
			if(p.x<bXmin){
				bXmin=p.x;
			}
		}
		if(aXmin>=bXmax){ //if the closest point of a is further than the farthest point of b
			return true;  //then a is behind b
		}
		if(bXmin>=aXmax){ //if the closest point of b is further than the farthest point of a
			return false; //then b is behind a
		}
		
		/*if(aDmin>=bDmax){ //if the closest point of a is further than the farthest point of b
			return true;  //then a is behind b
		}
		if(bDmin>=aDmax){ //if the closest point of b is further than the farthest point of a
			return false; //then b is behind a
		}*/
		//TODO: actually implement the majority of the algorithm
		if(aXmax==bXmax){ //this is just a stopgap until the above is completed
			return aXmin>bXmin;
		} else {
			return aXmax>bXmax;
		}
	}
}
	


