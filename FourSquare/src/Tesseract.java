
public class Tesseract extends Fourbject {
	static int edges[][] = {{0,1},{0,2},{1,3},{2,3},{0,4},{1,5},{4,5},{2,6},{4,6},{3,7},{5,7},{6,7},{0,8},{1,9},{8,9},{2,10},{8,10},{3,11},{9,11},{10,11},{4,12},{8,12},{5,13},{9,13},{12,13},{6,14},{10,14},{12,14},{7,15},{11,15},{13,15},{14,15}};
	static Threebject tbj[]= {new Threebject(new int []{0,1,2,3,4,5,6,7,8,9,10,11}), //x=0
			new Threebject(new int []{14,16,18,19,21,23,24,26,27,29,30,31}), //x=1
			new Threebject(new int []{0,1,2,3,12,13,14,15,16,17,18,19}), //y=0
			new Threebject(new int []{6,8,10,11,20,22,24,25,27,28,30,31}), //y=1
			new Threebject(new int []{0,4,5,6,12,13,14,20,21,22,23,24}), //z=0
			new Threebject(new int []{3,7,9,11,15,17,19,25,26,28,29,31}), //z=1
			new Threebject(new int []{1,4,7,8,12,15,16,20,21,25,26,27}), //w=0
			new Threebject(new int []{2,5,9,10,13,17,18,22,23,28,29,30})}; //w=1
	
	public Tesseract(Point corner, Point farCorner){
		super();
		int index=0;
		Point p[]= new Point[16];
		for(int i=0; i<=1; i++){ 
			for(int j=0; j<=1; j++){
				for(int k=0; k<=1; k++){
					for(int l=0; l<=1; l++){
						p[index]=new Point(corner.x+i*(farCorner.x-corner.x), corner.y+j*(farCorner.y-corner.y), corner.z+k*(farCorner.z-corner.z), corner.w+l*(farCorner.w-corner.w));
						index++;
					}
				}
			}
		}
		
		this.setEdges(edges);
		this.setPoints(p);
		this.setThreebjects(tbj);
		
	}

}
