import java.util.ArrayList;

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
 
}
