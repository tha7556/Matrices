import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Matrix {
	private ArrayList<ArrayList<Double>> matrix;
	private int width, height;
	/**
	 * Creates a Matrix Object from an ArrayList<ArrayList<Double>>
	 * @param matrix ArrayList<ArrayList<Double>> containing the data for the Matrix
	 */
	public Matrix(ArrayList<ArrayList<Double>> matrix) {
		this.matrix = matrix;
		height = matrix.size();
		width = matrix.get(0).size();
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
	 * Gets two Matricies from the given filename, with tabs as delimiters
	 * @param fileName The name of the File with the data
	 * @return Two Matricies in an array generated from the file
	 */
	public static Matrix[] getMatriciesFromFile(String fileName) {
		File file = new File(fileName);
		Scanner scanner;
		ArrayList<ArrayList<Double>> matrixOne = new ArrayList<ArrayList<Double>>(); 
		ArrayList<ArrayList<Double>> matrixTwo = new ArrayList<ArrayList<Double>>();
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.contains("x"))
					continue;
				String[] arr = line.split("\t");
				if(arr.length < 4)
					continue;
				ArrayList<Double> arrOne = new ArrayList<Double>(2);
				ArrayList<Double> arrTwo = new ArrayList<Double>(2);
				arrOne.add(Double.parseDouble(arr[0]));
				arrOne.add(Double.parseDouble(arr[1]));
				arrTwo.add(Double.parseDouble(arr[2]));
				arrTwo.add(Double.parseDouble(arr[3]));
				
				matrixOne.add(arrOne);
				matrixTwo.add(arrTwo);
			}
			scanner.close();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		Matrix a = new Matrix(matrixOne);
		Matrix b = new Matrix(matrixTwo);
		return new Matrix[] {a,b};
	}
	public static void main(String[] args) {
		Matrix matrix = Matrix.getMatriciesFromFile("Test.txt")[0];
		Matrix matrix2 = Matrix.getMatriciesFromFile("Test.txt")[1];
		matrix.print();
		System.out.println();
		Matrix product = matrix.multiply(matrix2);
		product.print();
	}
 
}
