import java.io.File;
import java.util.Scanner;

public class Point {
	private double x, y;
	private int size = 2;
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double[] getAsArray() {
		return new double[] {x,y};
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String toString() {
		return "("+x+","+y+")";
	}
	public Point subtract(Point other) {
		return new Point(x-other.getX(),y-other.getY());
	}
	public int size() {
		return size;
	}
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
		for(UnknownClass unknown : array) {
		
			unknown.getCovarianceMatrix().print();
			System.out.println();
		}
	}
}
