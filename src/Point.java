import java.io.File;
import java.util.Scanner;

/**
 * A Point class used for representing (x,y) coordinates
 * @author Tyler Atkinson
 */
public class Point {
	private double x, y;
	private int size = 2;
	/**
	 * Creates a point based on (x,y) coordinates
	 * @param x The x coordinate
	 * @param y The y coordinate
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
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
	public static UnknownClass[] getPointsFromFile(String fileName) {
		File file = new File(fileName);
		Scanner scanner;
		UnknownClass[] array = new UnknownClass[2];
		array[0] = new UnknownClass();
		array[1] = new UnknownClass();
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if(line.contains("x"))
					continue;
				String[] arr = line.split("\t");
				if(arr.length < 4)
					continue;
				array[0].addPoint(new Point(Double.parseDouble(arr[0]),Double.parseDouble(arr[1])));
				array[1].addPoint(new Point(Double.parseDouble(arr[2]),Double.parseDouble(arr[3])));	
			}
			scanner.close();
			return array;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		UnknownClass[] array = getPointsFromFile("2018 Spring Project 1 data.txt");
		int i = 0;
		for(UnknownClass unknown : array) {
			i++;
			System.out.println("\t\tClass: "+i);
			System.out.println("Mean Vector: "+unknown.getMean());
			unknown.getCovarianceMatrix().printToFile("data\\class"+i+" Covariance.csv");
			unknown.getCovarianceMatrix().print();
			System.out.println("\ndeterminint: "+unknown.getCovarianceMatrix().findDeterminant()+"\n");
			unknown.getCovarianceMatrix().getInverse().print();
		}
	}
}
