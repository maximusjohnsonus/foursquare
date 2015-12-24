import java.awt.Point;
import java.util.ArrayList;

public class Matrix { 
	//A.length = rows
	//A[0].length = cols
	
	public static double[][] multiply(double[][] A, double[][] B){ //returns AB //TODO: OPTIMIZE
		int newR=A.length;
		int newC=B[0].length;
		int sharedDim=A[0].length;
		
		if(sharedDim==B.length){ //m×n * n×o
			double[][] C = new double[newR][newC]; //m×o
			for(int r=0; r<newR; r++){
				for(int c=0; c<newC; c++){
					for(int i=0; i<sharedDim; i++){
						C[r][c] += A[r][i]*B[i][c];
					}
				}
			}
			return C;
		} else {
			System.out.println("multiply: dimwit error. "+A.length+"×"+A[0].length+" * "+B.length+"×"+B[0].length);
			return null;
		}
	}
	public static double[][] rref(double[][] A){ //TODO: OPTIMIZE 
		//System.out.println("preRREF:");
		//printMatrix(A);
		int numRows = A.length;
		int numCols = A[0].length;
		int pivotRow=0;
		ArrayList<Point> pivots = new ArrayList<Point>(0);
		//TODO: row swap the row with the biggest value in your column into place to maximize linear stability
		for(int pivotCol=0; pivotRow<numRows && pivotCol<numCols; pivotCol++){ //go through every column
			if(A[pivotRow][pivotCol]==0){ //if position isn't a pivot
				for(int i=pivotRow+1; i<numRows; i++){ //go through every lower row (in same column)
					if(A[i][pivotCol]!=0){ //until you find a pivot
						rowSwap(A,pivotRow,i); //then swap that row into focus
						break; //and stop looking for a pivot
					}
				}
				if(A[pivotRow][pivotCol]==0){ //if position STILL isn't a pivot
					continue; //go to the next column and don't change pivotRow
				}
			}

			multiplyRow(A,pivotRow, 1/A[pivotRow][pivotCol]); //make the pivot a 1 by dividing the row by pivot's value
			for(int i=pivotRow+1; i<numRows; i++){ //go through all lower rows
				if(A[i][pivotCol]!=0){ //if said lower row isn't already clear //TODO: would it be more efficient to not have this?
					multiplyRowAddToRow(A, pivotRow, -1*A[i][pivotCol], i); //kill entries below pivot by adding their negation
				} //TODO: set the entry you're killing to 0 because you know you're killing it
			}
			pivots.add(0,new Point(pivotRow, pivotCol));
			pivotRow++; //we can go down a row now
		}
		//now it's in REF
		for(Point p:pivots){ //iterate through the pivots (we added them in reverse order)
			for(int i=p.x-1; i>=0; i--){ //go through all the rows above each pivot
				multiplyRowAddToRow(A, p.x, -1*A[i][p.y], i); //kill entries above pivot by adding their negation
			}
		}
		//System.out.println("postRREF:");
		//printMatrix(A);
		return A;
	}
	public static double[][] rowSwap(double[][] A, int row1, int row2){
		double[] swap=A[row1];
		A[row1]=A[row2];
		A[row2]=swap;
		return A;
	}
	public static double[][] multiplyRow(double[][] A, int row, double factor){
		for(int i=0; i<A[row].length; i++){
			A[row][i]=factor*A[row][i];
		}
		return A;
	}
	public static double[][] multiplyRowAddToRow(double[][] A, int row, double factor, int addToRow){
		for(int i=0; i<A[row].length; i++){
			A[addToRow][i]+=factor*A[row][i];
			if(Math.abs(A[addToRow][i])<0.0000000001){ //rounding because doubles are a pain. This should be the only place it matters
				A[addToRow][i]=0;
			}
		}
		return A;
	}
	public static double[][] augment(double[][] A, double[][] B){ //returns [A B] //TODO: OPTIMIZE
		int rows=A.length;		
		int Acols = A[0].length;
		int Bcols = B[0].length;
		
		if(rows==B.length){ //if both have same number of rows, continue
			double[][] C = new double[rows][Acols+Bcols];
			for(int i=0; i<Acols; i++){
				for(int j=0; j<rows; j++){
					C[j][i]=A[j][i];
				}
			}
			for(int i=0; i<Bcols; i++){
				for(int j=0; j<rows; j++){
					C[j][i+Acols]=B[j][i];
				}
			}
			return C;
		} else {
			return null;
		}
	}
	public static void printMatrix(double[][] A){
		for(int i=0; i<A.length; i++){
			for(int j=0; j<A[0].length; j++){
				System.out.print(A[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
