import java.util.ArrayList;

/**
 * An unclassified class based on a number of Points
 * @author Tyler Atkinson
 */
public class UnknownClass {
	private ArrayList<Point> points;
	/**
	 * Creates a class based off of an ArrayList of Points
	 * @param points The ArrayList of Points
	 */
	public UnknownClass(ArrayList<Point> points) {
		this.points = points;
	}
	/**
	 * Creates an empty class with no Points
	 */
	public UnknownClass() {
		points = new ArrayList<>();
	}
	/**
	 * Gets the Points that are in the class
	 * @return The ArrayList of Points in the class
	 */
	public ArrayList<Point> getPoints() {
		return points;
	}
	/**
	 * Adds a new Point to the class
	 * @param point The new Point
	 */
	public void addPoint(Point point) {
		points.add(point);
	}
	/**
	 * Gets the Mean Vector of the class
	 * @return A Point representing the Mean Vector
	 */
	public Point getMean() {
		double x = 0.0;
		double y = 0.0;
		for(Point p: points) {
			x += p.getX();
			y += p.getY();
		}
		return new Point(x/(double)points.size(), y/(double)points.size());
	}
	/**
	 * Subtracts the Mean Vector from each of the Points in the class
	 * @return An ArrayList containing the differences of each Point and the Mean Vector
	 */
	public ArrayList<Point> subtractMeanVector() {
		Point mean = getMean();
		ArrayList<Point> result = new ArrayList<>(getSize());
		for(int i = 0; i < getSize(); i++) {
			result.add(points.get(i).subtract(mean));
		}
		return result;
	}
	/**
	 * Makes each Point a square Matrix by multiplying each by its Transpose
	 * @return An array of each Point as a square Matrix
	 */
	public Matrix[] getSquareMatrices() {
		ArrayList<Point> vectors = subtractMeanVector();
		Matrix[] result = new Matrix[vectors.size()];
		for(int i = 0; i < vectors.size(); i++) {
			result[i] = new Matrix(vectors.get(i));
			result[i] = result[i].getTranspose().multiply(result[i]);
		}
		return result;
	}
	/**
	 * Calculates the Covariance Matrix of the class
	 * @return The Covariance Matrix of the class
	 */
	public Matrix getCovarianceMatrix() {
		Matrix[] arr = getSquareMatrices();
		Matrix result = new Matrix(arr[0].getWidth(),arr[0].getHeight());
		for(Matrix m : arr) {
			result = result.add(m);
		}
		return result.multiply(1.0/(double)points.size());
		
	}
	/**
	 * Creates a deep copy of the Point array
	 * @return A deep copy of the Point array
	 */
	public ArrayList<Point> copyPoints() {
		ArrayList<Point> result = new ArrayList<>(getSize());
        result.addAll(points);
		return result;
	}
	/**
	 * Gets the number of Points in the class
	 * @return The number of Points in the class
	 */
	public int getSize() {
		return points.size();
	}
	public static void main(String[] args) {
		UnknownClass unknownClass = Point.getPointsFromFile("eigendataS2018.txt",1)[0];
		Matrix m = unknownClass.getCovarianceMatrix();
		System.out.println("Covariance:");
		m.print();
		System.out.println();
		for(double d : m.findEigenValues()) {
			System.out.println("root: " +d);
		}
		System.out.println("determinant: "+m.findDeterminant()+"\n\nEigenVectors:");
		for(double d : m.findEigenValues()) {
			Point vec = m.findEigenVector(d);
			System.out.println("EigenVector: " + vec);
			System.out.println("\tUnit Length: "+ Matrix.findUnitLength(vec));
		}

	}
}
