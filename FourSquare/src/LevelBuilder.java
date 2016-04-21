import java.awt.Color;
import java.util.ArrayList;

public class LevelBuilder {
	public static ArrayList <Fourbject> buildMaze(boolean[][][][] map, double scale, double wallWidth){ //fill out the walls of a maze
		return buildMaze(map, new Point(scale*map.length/2.0, scale*map[0].length/2.0, scale*map[0][0].length/2.0, scale*map[0][0][0].length/2.0), scale, wallWidth);
	}
	public static ArrayList <Fourbject> buildMaze(boolean[][][][] map, Point refPoint, double scale, double wallWidth){ //fill out the walls of a maze
		//TODO: look at combining fbjs that form a convex region when combined (adjacent walls that are parallel, I think)
		int maxColor=180; //Adjust to change max brightness of color. If this is lower, the goal stands out better
		ArrayList <Fourbject> objs=new ArrayList <Fourbject>(0);
		for(int i=0; i<map.length; i++){
			for(int j=0; j<map[0].length; j++){
				for(int k=0; k<map[0][0].length; k++){
					for(int l=0; l<map[0][0][0].length; l++){
						if(map[i][j][k][l]){ //only look at adding walls if this is a path fourxel (a true in map) (a fourxel being the 4D analogue of a pixel or voxel, naturally)
							//only add a wall in a given direction if the fourxel in that direction isn't a path fourxel
							//Color unitColor=new Color((int)(Math.random()*maxColor),(int)(Math.random()*maxColor),(int)(Math.random()*maxColor));
							if(i==0 || !map[i-1][j][k][l]){ // -x wall
								objs.add(new Tesseract(new Point(scale*i-refPoint.x-wallWidth, scale*j-refPoint.y, scale*k-refPoint.z, scale*l-refPoint.w), new Point(scale*i-refPoint.x, scale*(j+1)-refPoint.y, scale*(k+1)-refPoint.z, scale*(l+1)-refPoint.w), new Color((int)(Math.random()*maxColor),(int)(Math.random()*maxColor),(int)(Math.random()*maxColor))));
							}
							if(i==map.length-1 || !map[i+1][j][k][l]){ // +x wall
								objs.add(new Tesseract(new Point(scale*(i+1)-refPoint.x, scale*j-refPoint.y, scale*k-refPoint.z, scale*l-refPoint.w), new Point(scale*(i+1)-refPoint.x+wallWidth, scale*(j+1)-refPoint.y, scale*(k+1)-refPoint.z, scale*(l+1)-refPoint.w), new Color((int)(Math.random()*maxColor),(int)(Math.random()*maxColor),(int)(Math.random()*maxColor))));
							}
							if(j==0 || !map[i][j-1][k][l]){ // -y wall
								objs.add(new Tesseract(new Point(scale*i-refPoint.x, scale*j-refPoint.y-wallWidth, scale*k-refPoint.z, scale*l-refPoint.w), new Point(scale*(i+1)-refPoint.x, scale*j-refPoint.y, scale*(k+1)-refPoint.z, scale*(l+1)-refPoint.w), new Color((int)(Math.random()*maxColor),(int)(Math.random()*maxColor),(int)(Math.random()*maxColor))));
							}
							if(j==map[0].length-1 || !map[i][j+1][k][l]){ // +y wall
								objs.add(new Tesseract(new Point(scale*i-refPoint.x, scale*(j+1)-refPoint.y, scale*k-refPoint.z, scale*l-refPoint.w), new Point(scale*(i+1)-refPoint.x, scale*(j+1)-refPoint.y+wallWidth, scale*(k+1)-refPoint.z, scale*(l+1)-refPoint.w), new Color((int)(Math.random()*maxColor),(int)(Math.random()*maxColor),(int)(Math.random()*maxColor))));
							}
							if(k==0 || !map[i][j][k-1][l]){ // -z wall
								objs.add(new Tesseract(new Point(scale*i-refPoint.x, scale*j-refPoint.y, scale*k-refPoint.z-wallWidth, scale*l-refPoint.w), new Point(scale*(i+1)-refPoint.x, scale*(j+1)-refPoint.y, scale*k-refPoint.z, scale*(l+1)-refPoint.w), new Color((int)(Math.random()*maxColor),(int)(Math.random()*maxColor),(int)(Math.random()*maxColor))));
							}
							if(k==map[0][0].length-1 || !map[i][j][k+1][l]){ // +z wall
								objs.add(new Tesseract(new Point(scale*i-refPoint.x, scale*j-refPoint.y, scale*(k+1)-refPoint.z, scale*l-refPoint.w), new Point(scale*(i+1)-refPoint.x, scale*(j+1)-refPoint.y, scale*(k+1)-refPoint.z+wallWidth, scale*(l+1)-refPoint.w), new Color((int)(Math.random()*maxColor),(int)(Math.random()*maxColor),(int)(Math.random()*maxColor))));
							}
							if(l==0 || !map[i][j][k][l-1]){ // -w wall
								objs.add(new Tesseract(new Point(scale*i-refPoint.x, scale*j-refPoint.y, scale*k-refPoint.z, scale*l-refPoint.w-wallWidth), new Point(scale*(i+1)-refPoint.x, scale*(j+1)-refPoint.y, scale*(k+1)-refPoint.z, scale*l-refPoint.w), new Color((int)(Math.random()*maxColor),(int)(Math.random()*maxColor),(int)(Math.random()*maxColor))));
							}
							if(l==map[0][0][0].length-1 || !map[i][j][k][l+1]){ // +w wall
								objs.add(new Tesseract(new Point(scale*i-refPoint.x, scale*j-refPoint.y, scale*k-refPoint.z, scale*(l+1)-refPoint.w), new Point(scale*(i+1)-refPoint.x, scale*(j+1)-refPoint.y, scale*(k+1)-refPoint.z, scale*(l+1)-refPoint.w+wallWidth), new Color((int)(Math.random()*maxColor),(int)(Math.random()*maxColor),(int)(Math.random()*maxColor))));
							}
						}
					}
				}
			}
		}
		return objs;
	}
	public static ArrayList <Fourbject> GOOOOOOOL(Point point, double scale){ //all the space encapsulated by the two points will be empty
		ArrayList <Fourbject> objs=new ArrayList <Fourbject>(0);
		objs.add(new Tesseract(new Point(-scale/2+point.x, -scale/4+point.y, -scale/4+point.z, -scale/4+point.w), new Point(scale/2+point.x, scale/4+point.y, scale/4+point.z, scale/4+point.w), Color.GREEN));
		objs.add(new Tesseract(new Point(-scale/4+point.x, -scale/2+point.y, -scale/4+point.z, -scale/4+point.w), new Point(scale/4+point.x, scale/2+point.y, scale/4+point.z, scale/4+point.w), Color.ORANGE));
		objs.add(new Tesseract(new Point(-scale/4+point.x, -scale/4+point.y, -scale/2+point.z, -scale/4+point.w), new Point(scale/4+point.x, scale/4+point.y, scale/2+point.z, scale/4+point.w), Color.RED));
		objs.add(new Tesseract(new Point(-scale/4+point.x, -scale/4+point.y, -scale/4+point.z, -scale/2+point.w), new Point(scale/4+point.x, scale/4+point.y, scale/4+point.z, scale/2+point.w), Color.BLUE));

		return objs;
	}
	public static ArrayList <Fourbject> genFourSquareShellFourxels(Point lowPoint, Point highPoint){ //all the space encapsulated by the two points will be empty
		ArrayList <Fourbject> objs=new ArrayList <Fourbject>(0);
		for(double x=lowPoint.x-1; x<=highPoint.x; x++){
			for(double y=lowPoint.y-1; y<=highPoint.y; y++){
				for(double z=lowPoint.z-1; z<=highPoint.z; z++){
					objs.add(new Tesseract(new Point(x,y,z,lowPoint.w-1), new Point(x+1,y+1,z+1,lowPoint.w)));
					objs.add(new Tesseract(new Point(x,y,z,highPoint.w), new Point(x+1,y+1,z+1,highPoint.w+1)));
				}
			}
		}
		for(double x=lowPoint.x-1; x<=highPoint.x; x++){
			for(double y=lowPoint.y-1; y<=highPoint.y; y++){
				for(double w=lowPoint.w-1; w<=highPoint.w; w++){
					objs.add(new Tesseract(new Point(x,y,lowPoint.z-1,w), new Point(x+1,y+1,lowPoint.z,w+1)));
					objs.add(new Tesseract(new Point(x,y,highPoint.z,w), new Point(x+1,y+1,highPoint.z+1,w+1)));
				}
			}
		}
		for(double x=lowPoint.x-1; x<=highPoint.x; x++){
			for(double z=lowPoint.z-1; z<=highPoint.z; z++){
				for(double w=lowPoint.w-1; w<=highPoint.w; w++){
					objs.add(new Tesseract(new Point(x,lowPoint.y-1,z,w), new Point(x+1,lowPoint.y,z+1,w+1)));
					objs.add(new Tesseract(new Point(x,highPoint.y,z,w), new Point(x+1,highPoint.y+1,z+1,w+1)));
				}
			}
		}
		for(double y=lowPoint.y-1; y<=highPoint.y; y++){
			for(double z=lowPoint.z-1; z<=highPoint.z; z++){
				for(double w=lowPoint.w-1; w<=highPoint.w; w++){
					objs.add(new Tesseract(new Point(lowPoint.x-1,y,z,w), new Point(lowPoint.x,y+1,z+1,w+1)));
					objs.add(new Tesseract(new Point(highPoint.x,y,z,w), new Point(highPoint.x+1,y+1,z+1,w+1)));
				}
			}
		}
		
		return objs;
	}
}
