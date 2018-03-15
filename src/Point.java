import java.io.File;
import java.util.Scanner;

/**
 * A Point class used for representing (x,y) coordinates
 * @author Tyler Atkinson
 */
public class Point {

	private double x, y;
	private int size;
	/**
	 * Creates a point based on (x,y) coordinates
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
		this.size = 2;
	}
	/**
	 * Gets the X coordinate
	 * @return The X coordinate
	 */
	public double getX() {
		return x;
	}
	/**
	 * Gets the Y coordinate
	 * @return The Y coordinate
	 */
	public double getY() {
		return y;
	}
	/**
	 * Returns the (x,y) coordinates as an array: [x,y]
	 * @return The coordinates as an array
	 */
	public double[] getAsArray() {
		return new double[] {x,y};
	}
	/**
	 * Changes the X value to a new one
	 * @param x The new X value
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * Changes the Y value to a new one
	 * @param y The new Y value
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * Returns this Point as a String in the form: (x,y)
	 */
	public String toString() {
		return "("+x+","+y+")";
	}
	/**
	 * Subtracts another Point from this one
	 * @param other The Point to subtract
	 * @return A new Point based on the difference between the two Points
	 */
	public Point subtract(Point other) {
		return new Point(x-other.getX(),y-other.getY());
	}
	/**
	 * Gets the size of this Point/Vector
	 * @return The Size of this Point
	 */
	public int size() {
		return size;
	}
	/**
	 * Creates an UnknownClass[] composed of Points based on a file
	 * @param fileName The file name containing the data for the Points
	 * @return An array of UnknownClasses, each containing many Points
	 */
	public static UnknownClass[] getPointsFromFile(String fileName, int numOfClasses) {
		File file = new File(fileName);
		Scanner scanner;
		UnknownClass[] array = new UnknownClass[numOfClasses];
		for(int i = 0; i < numOfClasses; i++)
			array[i] = new UnknownClass();
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.toLowerCase().contains("x")||line.contains("Eigendata"))
					continue;
				String[] arr = line.split("\t");
				for(int i = 0; i < numOfClasses; i++) {
					array[i].addPoint(new Point(Double.parseDouble(arr[i*2]),Double.parseDouble(arr[(i*2)+1])));
				}
			}
			scanner.close();
			return array;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Matrix m = Matrix.getFromFile("Test2.txt");
		System.out.println("Original Matrix:");
		m.print();
		System.out.println("Inverse:");
		m.getInverse().print();
		System.out.println("Determinant: "+m.findDeterminant());
		m.getTranspose().print();

	}
}
