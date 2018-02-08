import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class Matrix {
	private ArrayList<ArrayList<Double>> matrix;
	private int width, height;
	/**
	 * Creates a Matrix Object from an ArrayList<Point>
	 * @param matrix ArrayList<Point> containing the data for the Matrix
	 */
	public Matrix(ArrayList<Point> matrix) {
		width = matrix.get(0).size();
		height = matrix.size();
		this.matrix = new ArrayList<ArrayList<Double>>(height);
		for(int i = 0; i < height; i++) {
			this.matrix.add(new ArrayList<Double>(width));
			for(int j = 0; j < width; j++) {
				this.matrix.get(i).add(matrix.get(i).getAsArray()[j]);
			}
		}
	}
	/**
	 * Creates a blank Matrix with null values of the designated size
	 * @param rows The number of rows in the new Matrix
	 * @param columns The number of columns in the new Matrix
	 */
	public Matrix(int rows, int columns) {
		width = columns;
		height = rows;
		matrix = new ArrayList<ArrayList<Double>>(rows);
		for(int i = 0; i < rows; i++) {
			matrix.add(new ArrayList<Double>(columns));
			for(int j = 0; j < columns; j++) {
				matrix.get(i).add(0.0);
			}
		}
	}
	/**
	 * Gets an array of the desired row
	 * @param index The index of the row
	 * @return An array containing the values of the given row
	 */
	public double[] getRow(int index) {
		double[] row = new double[width];
		for(int i = 0; i < matrix.get(index).size(); i++) {
			row[i] = get(index,i);
		}
		return row;
	}
	/**
	 * Gets an array of the desired column
	 * @param index The index of the column
	 * @return An array containing the values of the given column
	 */
	public double[] getColumn(int index) {
		double column[] = new double[height];
		for(int i = 0; i < matrix.size(); i++) {
			column[i] = get(i,index);
		}
		return column;
	}
	/**
	 * @return An int value representing the height of the Matrix
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @return An int value representing the width of the Matrix
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Gets the value at the given location
	 * @param row The row containing the value
	 * @param column The column containing the value
	 * @return The value at the given location
	 */
	public double get(int row, int column) {
		return matrix.get(row).get(column);
	}
	/**
	 * Sets the given position to the given value
	 * @param row The desired row
	 * @param column The desired column
	 * @param value The value to insert
	 */
	public void set(int row, int column, double value) {
		this.matrix.get(row).set(column, value);
	}
	/**
	 * Prints the Matrix to the console
	 */
	public void print() {
		for(ArrayList<Double> arr : matrix) {
			for(int i = 0; i < arr.size(); i++) {
				if(i < arr.size()-1)
					System.out.print(arr.get(i)+", ");
				else
					System.out.println(arr.get(i));
			}
		}
	}
	public void printToFile(String fileName) {
		File file = new File(fileName);
		FileWriter fWriter = null;
		PrintWriter pWriter = null;
		try {
			fWriter = new FileWriter(file);
			pWriter = new PrintWriter(fWriter);
		} catch(Exception e) {
			e.printStackTrace();
		}
		for(ArrayList<Double> arr : matrix) {
			for(int i = 0; i < arr.size(); i++) {
				pWriter.print(arr.get(i)+",");
				
			}
			pWriter.println();
		}
		try {
			pWriter.close();
			fWriter.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Makes a copy of the Matrix
	 * @return A copy of the Matrix
	 */
	public Matrix getCopy() {
		Matrix result = new Matrix(height,width);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				result.set(i, j, get(i,j));
			}
		}
		return result;
	}
	/**
	 * Multiplies the Matrix by a given scalar
	 * @param scalar The scalar to multiply the Matrix by
	 * @return The resulting Matrix of the multiplication
	 */
	public Matrix multiply(double scalar) { 
		Matrix matrix = new Matrix(height, width);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				matrix.set(y,x,get(y,x) * scalar);
			}
		}
		return matrix;
	}
	/**
	 * Multiplies this Matrix by another Matrix
	 * @param other The Matrix to multiply this one by
	 * @return The resulting Matrix of the multiplication
	 */
	public Matrix multiply(Matrix other) { 
		if(width == other.getHeight()) {
		Matrix matrix = new Matrix(height,other.getWidth());
			for(int i = 0; i < height; i++) { //each row
				for(int j = 0; j < other.getWidth(); j++) { //each column
					for(int y = 0; y < width; y++) {
						double val = matrix.get(i, j) + (get(i,y) * other.get(y,j));
						matrix.set(i, j, val);
					}
				}
				
			}
			return matrix;
		}
		else {
			System.out.println("ERROR! A\'s width must equal B\'s Height!");
			return null;
		}
	}
	/**
	 * Adds this Matrix to another
	 * @param other The Matrix to add to this one
	 * @return The resulting Matrix of the addition
	 */
	public Matrix add(Matrix other) {
		if(width == other.getWidth() && height == other.getHeight()) {
			Matrix matrix = new Matrix(height, width);
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					matrix.set(y, x, get(y,x)+other.get(y, x));
				}
			}
			return matrix;
		}
		else {
			System.out.println("ERROR! Sizes of the two Matricies must be equal!");
			return null;
		}
		
	}
	/**
	 * Subtracts another Matrix from this one
	 * @param other The Matrix to subtract from this one
	 * @return The resulting Matrix of the subtraction
	 */
	public Matrix subtract(Matrix other) {
		if(width == other.getWidth() && height == other.getHeight()) {
			Matrix matrix = new Matrix(height, width);
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					matrix.set(y, x, get(y,x)-other.get(y, x));
				}
			}
			return matrix;
		}
		else {
			System.out.println("ERROR! Sizes of the two Matricies must be equal!");
			return null;
		}
		
	}
	/**
	 * Used to determine if it is a square Matrix
	 * @return True if it is Square
	 */
	public boolean isSquare() {
		return width == height;
	}
	/**
	 * Gets the identity Matrix of the same size as the current Matrix
	 * @return The identity Matrix
	 */
	public Matrix getIdentityMatrix() {
		if(isSquare()) {
			Matrix matrix = new Matrix(height,width);
			for(int i = 0; i < width; i++) {
				matrix.set(i, i, 1);
			}
			return matrix;
		}
		else {
			System.out.println("Matrix must be square!");
			return null;
		}
		
	}
	public Matrix getTranspose() {
		Matrix result = new Matrix(width, height);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				result.set(x, y,matrix.get(y).get(x));
			}
		}
		return result;
	}
	public void swapRows(int rowA, int rowB) {
		ArrayList<Double> temp = matrix.get(rowA);
		matrix.set(rowA, matrix.get(rowB));
		matrix.set(rowB, temp);
	}
	public BigDecimal findDeterminint(int digits) {
		Matrix m = getCopy();
		int r = 0;
 		for(int j = 0; j < width-1; j++) {
			int p = m.findPivot(j);
			if(m.get(p,j) == 0.0) {
				return BigDecimal.ZERO;
			}
			if(p > j) {
				m.swapRows(p,j);
				r++;
			}
			for(int i = j+1; i < height; i++) {
				double d = m.get(i,j)/m.get(j,j);
				for(int k = j; k < width; k++) {
					double num = m.get(i,k) - (d * m.get(j,k));
					m.set(i,k,num);
				}
			}
		}
 		BigDecimal val = BigDecimal.ONE;
 		for(int n = 0; n < width; n++) {
 			val = val.multiply(BigDecimal.valueOf(m.get(n,n)));
 		}
 		BigDecimal result = BigDecimal.valueOf(Math.pow(-1, r)).multiply(val,new MathContext(digits,RoundingMode.HALF_EVEN));
 		return result;
	}
	public BigDecimal findDeterminint() {
		return findDeterminint(5);
	}
	public Matrix getInverse() {
		Matrix c = combineWith(getIdentityMatrix());
		for(int j = 0; j < c.getHeight(); j++) {
			int p = c.findPivot(j);
			if(c.get(p, j) == 0.0) 
				return null;
			if(p > j) {
				c.swapRows(p, j);
			}
			double cJJ = c.get(j, j);
			for(int k = 0; k < c.getWidth(); k++) {
				c.set(j, k, (c.get(j, k)/cJJ));
			}
			for(int i = 0; i < c.getHeight(); i++) { 
				if(i != j) {
					double cIJ = c.get(i, j);
					for(int k = 0; k < c.getWidth(); k++) {
						double val = c.get(i, k) - cIJ*c.get(j, k);
						c.set(i, k, val);
					}
				}
			}
		}
		Matrix result = new Matrix(height,width);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				result.set(y, x, c.get(y, x+width));
			}
		}
		return result;
	}
	public Matrix combineWith(Matrix other) {
		if(height != other.getHeight()) {
			throw new RuntimeException("Heights do not match!");
		}
		int newWidth = width + other.getWidth();
		ArrayList<ArrayList<Double>> newMatrix = new ArrayList<ArrayList<Double>>();
		for(int y = 0; y < height; y++) {
			ArrayList<Double> row = new ArrayList<Double>(newWidth);
			for(double d : matrix.get(y)) {
				row.add(d);
			}
			for(double d : other.getRow(y)) {
				row.add(d);
			}
			newMatrix.add(row);
		}
		Matrix result = new Matrix(height,newWidth);
		for(int y = 0; y < result.getHeight(); y++) {
			for(int x = 0; x < result.getWidth(); x++) {
				result.set(y, x, newMatrix.get(y).get(x));
			}
		}
		return result;
	}
	private int findPivot(int index) {
		//System.out.println(index);
		int result = -1;
		double max = -1.0;
		for(int i = 0; i < height; i++) {
			if(Math.abs(get(i,index)) > max) {
				max = Math.abs(get(i,index));
				result = i;
			}
		}
		return result;
	}
	public static Matrix getFromFile(String fileName) {
		ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
			while(scanner.hasNextLine()) {
				ArrayList<Double> array = new ArrayList<Double>();
				String line = scanner.nextLine();
				String[] arr = line.split("\t");
				for(int i = 0; i < arr.length; i++) {
					array.add(Double.parseDouble(arr[i].trim()));
				}
				matrix.add(array);
			}
			scanner.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		Matrix result = new Matrix(matrix.size(),matrix.get(0).size());
		for(int y = 0; y < result.getHeight(); y++) {
			for(int x = 0; x < result.getWidth(); x++) {
				result.set(y, x, matrix.get(y).get(x));
			}
		}
		return result;
	}
	public PrecisionMatrix getPrecise() {
		PrecisionMatrix result = new PrecisionMatrix(height,width);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				result.set(y, x, BigDecimal.valueOf(get(y,x)));
			}
		}
		return result;
	}
	public PrecisionMatrix getPrecise(int digits) {
		PrecisionMatrix result = new PrecisionMatrix(height,width,digits);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				result.set(y, x, BigDecimal.valueOf(get(y,x)));
			}
		}
		return result;
	}
	public static void main(String[] args) {
		Matrix matrix = Matrix.getFromFile("TestMatrix.txt");
		System.out.println(matrix.findDeterminint());
	}
 
}
