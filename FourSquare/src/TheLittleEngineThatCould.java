//P17

import java.awt.*; 
import java.awt.event.KeyEvent; 
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.JFrame; 


public class TheLittleEngineThatCould extends JFrame {        
	boolean isRunning = true; 
	int fps = 30; 
	int windowWidth = 500; 
	int windowHeight = 500;
	Rufus rufus;
	ArrayList <Fourbject> objs=new ArrayList<>(0);
	double step=5;
	double viewStep=0.03;
	double vUp=0; //velocity up - used for jumping
	Color[] colors={Color.BLACK};//{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.BLACK, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.PINK};

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
		/*Fourbject f=new Fourbject(new double[0][0]);
		for(int i=-100; i<100; i+=50){
			for(int j=-100; j<100; j+=50){
				for(int k=-100; k<100; k+=50){
					objs.add(new Fourbject(new double[][] {{i,j,k},{i+10,j,k},{i+10,j+10,k},{i,j+10,k}}));
					objs.add(new Fourbject(new double[][] {{i,j,k+10},{i+10,j,k+10},{i+10,j+10,k+10},{i,j+10,k+10}}));
					objs.add(new Fourbject(new double[][] {{i,j,k},{i,j,k+10},{i,j+10,k+10},{i,j+10,k}}));
					objs.add(new Fourbject(new double[][] {{i+10,j,k},{i+10,j,k+10},{i+10,j+10,k+10},{i+10,j+10,k}}));					
				}
			}
		}*/
		objs.add(new Fourbject(new double[][] {{0,0,0}, {0,0,20}}));
		/*objs.add(new Fourbject(new double[][] {{100,-100,-100},{100,100,-100},{100,100,100},{100,-100,100}}));
		objs.add(new Fourbject(new double[][] {{-100,-100,-100},{-100,100,-100},{-100,100,100},{-100,-100,100}}));
		objs.add(new Fourbject(new double[][] {{-100,-100,-100},{100,-100,-100},{100,100,-100},{-100,100,-100}}));
		objs.add(new Fourbject(new double[][] {{-100,-100,100},{100,-100,100},{100,100,100},{-100,100,100}}));
		objs.add(new Fourbject(new double[][] {{1,-1,-1},{1,1,-1},{1,1,1},{1,-1,1}}));
		objs.add(new Fourbject(new double[][] {{-1,-1,-1},{-1,1,-1},{-1,1,1},{-1,-1,1}}));
		objs.add(new Fourbject(new double[][] {{-1,-1,-1},{1,-1,-1},{1,1,-1},{-1,1,-1}}));
		objs.add(new Fourbject(new double[][] {{-1,-1,1},{1,-1,1},{1,1,1},{-1,1,1}}));*/

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
		if(rufus.pov.z!=0 || vUp!=0){
			rufus.pov.changeZ(vUp);
			vUp-=.5;
			if(rufus.pov.z<0){
				rufus.pov.z=0;
				vUp=0;
			}
		}
		if (input.isKeyDown(KeyEvent.VK_RIGHT)) { 
			rufus.pov.changeY(-1*step); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_LEFT)) { 
			rufus.pov.changeY(step); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_UP)) { 
			rufus.pov.changeX(step); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_DOWN)) { 
			rufus.pov.changeX(-1*step); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_D)) { 
			rufus.pov.changeYaw(-1*viewStep); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_A)) { 
			rufus.pov.changeYaw(viewStep); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_W)) { 
			rufus.pov.changePitch(viewStep); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_S)) { 
			rufus.pov.changePitch(-1*viewStep); 
			System.out.println(rufus.pov);
		}
		if (input.isKeyDown(KeyEvent.VK_SPACE)) { 
			vUp=5;
			System.out.println(rufus.pov);
		}
		
	} 

	void draw() {
		Graphics g = getGraphics(); 

		Graphics bbg = backBuffer.getGraphics(); 

		bbg.setColor(Color.WHITE); 
		bbg.fillRect(0, 0, windowWidth, windowHeight); 
		
		bbg.translate(windowWidth/2, windowHeight/2); //Fixes the coord system
		bbg.setColor(Color.BLACK); 
		//bbg.drawOval((int)rufus.pov.x, 10, 20, 20); 
		for(int i=0; i<objs.size(); i++){
			bbg.setColor(colors[i%colors.length]); 
			bbg.drawPolygon(objs.get(i).shiftedView(rufus.pov));
		}
		g.drawImage(backBuffer, insets.left, insets.top, this); 
	} 
}