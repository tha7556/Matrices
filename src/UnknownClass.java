import java.util.ArrayList;

public class UnknownClass {
	private ArrayList<Point> points;
	public UnknownClass(ArrayList<Point> points) {
		this.points = points;
	}
	public UnknownClass() {
		points = new ArrayList<Point>();
	}
	public ArrayList<Point> getPoints() {
		return points;
	}
	public void addPoint(Point point) {
		points.add(point);
	}
	public Point getMean() {
		double x = 0.0;
		double y = 0.0;
		for(Point p: points) {
			x += p.getX();
			y += p.getY();
		}
		return new Point(x/(double)points.size(), y/(double)points.size());
	}
	public ArrayList<Point> subtractMeanVector() {
		Point mean = getMean();
		ArrayList<Point> result = new ArrayList<Point>(getSize());
		for(int i = 0; i < getSize(); i++) {
			result.add(points.get(i).subtract(mean));
		}
		return result;
	}
	
	public Matrix[] getSquareMatricies() {
		ArrayList<Point> vectors = subtractMeanVector();
		Matrix[] result = new Matrix[vectors.size()];
		for(int i = 0; i < vectors.size(); i++) {
			result[i] = new Matrix(vectors.get(i));
			result[i] = result[i].getTranspose().multiply(result[i]);
		}
		return result;
	}
	public Matrix getCovarianceMatrix() {
		Matrix[] arr = getSquareMatricies();
		Matrix result = new Matrix(arr[0].getWidth(),arr[0].getHeight());
		for(Matrix m : arr) {
			result = result.add(m);
		}
		return result.multiply(1.0/(double)points.size());
		
	}
	public ArrayList<Point> copyPoints() {
		ArrayList<Point> result = new ArrayList<Point>(getSize());
		for(Point p : points)
			result.add(p);
		return result;
	}
	public Matrix getAsMatrix() {
		return getAsMatrix(points);
	}
	public Matrix getAsMatrix(ArrayList<Point> points) {
		Matrix result = new Matrix(points.size(),points.get(0).size());
		for(int y = 0; y < points.size(); y++) {
			for(int x = 0; x < points.get(0).size(); x++) {
				result.set(y, x, points.get(y).getAsArray()[x]);
			}
		}
		return result;
	}
	public int getSize() {
		return points.size();
	}
}
