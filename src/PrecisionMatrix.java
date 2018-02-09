import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class PrecisionMatrix {
	private int width, height, digits;
	private RoundingMode roundingMode = RoundingMode.HALF_EVEN;
	private MathContext context;
	private ArrayList<ArrayList<BigDecimal>> matrix;
	
	public PrecisionMatrix(ArrayList<Point> matrix) {
		width = matrix.get(0).size();
		height = matrix.size();
		digits = 10;
		context = new MathContext(digits,roundingMode);
		this.matrix = new ArrayList<ArrayList<BigDecimal>>(height);
		for(int i = 0; i < height; i++) {
			this.matrix.add(new ArrayList<BigDecimal>(width));
			for(int j = 0; j < width; j++) {
				this.matrix.get(i).add(BigDecimal.valueOf(matrix.get(i).getAsArray()[j]));
			}
		}
	}
	public PrecisionMatrix(ArrayList<Point> matrix,int digits) {
		this(matrix);
		this.digits = digits;
		context = new MathContext(digits,roundingMode);
	}
	public PrecisionMatrix(int rows, int columns) {
		width = columns;
		height = rows;
		digits = 10;
		context = new MathContext(digits,roundingMode);
		matrix = new ArrayList<ArrayList<BigDecimal>>(rows);
		for(int i = 0; i < rows; i++) {
			matrix.add(new ArrayList<BigDecimal>(columns));
			for(int j = 0; j < columns; j++) {
				matrix.get(i).add(BigDecimal.ZERO);
			}
		}
	}
	public PrecisionMatrix(int rows, int columns, int digits) {
		this(rows,columns);
		this.digits = digits;
		context = new MathContext(digits,roundingMode);
	}
	public BigDecimal get(int row, int column) {
		return matrix.get(row).get(column);
	}
	public void set(int row, int column, BigDecimal value) {
		if(value.compareTo(BigDecimal.ZERO) == 0) {
			this.matrix.get(row).set(column, value);
			return;
		}
		this.matrix.get(row).set(column, value);
	}
	public BigDecimal[] getRow(int index) {
		BigDecimal[] row = new BigDecimal[width];
		for(int i = 0; i < matrix.get(index).size(); i++) {
			row[i] = get(index,i);
		}
		return row;
	}
	public BigDecimal[] getColumn(int index) {
		BigDecimal column[] = new BigDecimal[height];
		for(int i = 0; i < matrix.size(); i++) {
			column[i] = get(i,index);
		}
		return column;
	}
	public int getHeight() {
		return height;
	}
	public int getWidth() {
		return width;
	}
	public void print() {
		for(ArrayList<BigDecimal> arr : matrix) {
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
		for(ArrayList<BigDecimal> arr : matrix) {
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
	public PrecisionMatrix getCopy() {
		PrecisionMatrix result = new PrecisionMatrix(height,width);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				result.set(i, j, get(i,j));
			}
		}
		return result;
	}
	public PrecisionMatrix multiply(double scalar) { 
		PrecisionMatrix matrix = new PrecisionMatrix(height, width);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				matrix.set(y,x,get(y,x).multiply(BigDecimal.valueOf(scalar),context));
			}
		}
		return matrix;
	}
	public PrecisionMatrix multiply(PrecisionMatrix other) { 
		if(width == other.getHeight()) {
			PrecisionMatrix matrix = new PrecisionMatrix(height,other.getWidth());
			for(int i = 0; i < height; i++) { //each row
				for(int j = 0; j < other.getWidth(); j++) { //each column
					for(int y = 0; y < width; y++) {
						BigDecimal val = matrix.get(i, j).add((get(i,y).multiply(other.get(y,j),context)),context);
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
	public PrecisionMatrix add(PrecisionMatrix other) {
		if(width == other.getWidth() && height == other.getHeight()) {
			PrecisionMatrix matrix = new PrecisionMatrix(height, width);
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					matrix.set(y, x, get(y,x).add(other.get(y, x),context));
				}
			}
			return matrix;
		}
		else {
			System.out.println("ERROR! Sizes of the two Matricies must be equal!");
			return null;
		}
		
	}
	public PrecisionMatrix subtract(PrecisionMatrix other) {
		if(width == other.getWidth() && height == other.getHeight()) {
			PrecisionMatrix matrix = new PrecisionMatrix(height, width);
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					matrix.set(y, x, get(y,x).subtract(other.get(y, x),context));
				}
			}
			return matrix;
		}
		else {
			System.out.println("ERROR! Sizes of the two Matricies must be equal!");
			return null;
		}
		
	}
	public boolean isSquare() {
		return width == height;
	}
	public PrecisionMatrix getIdentityMatrix() {
		if(isSquare()) {
			PrecisionMatrix matrix = new PrecisionMatrix(height,width);
			for(int i = 0; i < width; i++) {
				matrix.set(i, i, BigDecimal.ONE);
			}
			return matrix;
		}
		else {
			System.out.println("Matrix must be square!");
			return null;
		}
		
	}
	public PrecisionMatrix getTranspose() {
		PrecisionMatrix result = new PrecisionMatrix(width, height);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				result.set(x, y,matrix.get(y).get(x));
			}
		}
		return result;
	}
	public void swapRows(int rowA, int rowB) {
		ArrayList<BigDecimal> temp = matrix.get(rowA);
		matrix.set(rowA, matrix.get(rowB));
		matrix.set(rowB, temp);
	}
	public BigDecimal findDeterminant() {
		PrecisionMatrix m = getCopy();
		int r = 0;
 		for(int j = 0; j < width-1; j++) { //maybe n-1
			int p = m.findPivot(j);
			if(m.get(p,j).doubleValue() == 0.0) {
				return BigDecimal.ZERO;
			}
			if(p > j) {
				m.swapRows(p,j);
				r++;
			}
			for(int i = j+1; i < height; i++) {
				BigDecimal d = m.get(i,j).divide(m.get(j,j),context);
				for(int k = j; k < width; k++) {
					BigDecimal num = m.get(i,k).subtract((d.multiply(m.get(j,k),context)),context);
					m.set(i,k,num);
				}
			}
		}
 		BigDecimal val = BigDecimal.ONE;
 		for(int n = 0; n < width; n++) {
 			val = val.multiply(m.get(n,n),context);
 		}
 		BigDecimal result = BigDecimal.valueOf(Math.pow(-1, r)).multiply(val,context);
 		return result;
	}
	public PrecisionMatrix getInverse() {
		PrecisionMatrix c = combineWith(getIdentityMatrix());
		for(int j = 0; j < c.getHeight(); j++) {
			int p = c.findPivot(j);
			if(c.get(p, j).compareTo(BigDecimal.ZERO) == 0) 
				return null;
			if(p > j) {
				c.swapRows(p, j);
			}
			BigDecimal cJJ = c.get(j, j);
			for(int k = 0; k < c.getWidth(); k++) {
				c.set(j, k, (c.get(j, k).divide(cJJ,context)));
			}
			for(int i = 0; i < c.getHeight(); i++) { 
				if(i != j) {
					BigDecimal cIJ = c.get(i, j);
					for(int k = 0; k < c.getWidth(); k++) {
						BigDecimal val = c.get(i, k).subtract(cIJ.multiply(c.get(j, k),context),context);
						c.set(i, k, val);
					}
				}
			}
		}
		PrecisionMatrix result = new PrecisionMatrix(height,width);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				result.set(y, x, c.get(y, x+width));
			}
		}
		return result;
	}
	public PrecisionMatrix combineWith(PrecisionMatrix other) {
		if(height != other.getHeight()) {
			throw new RuntimeException("Heights do not match!");
		}
		int newWidth = width + other.getWidth();
		ArrayList<ArrayList<BigDecimal>> newMatrix = new ArrayList<ArrayList<BigDecimal>>();
		for(int y = 0; y < height; y++) {
			ArrayList<BigDecimal> row = new ArrayList<BigDecimal>(newWidth);
			for(BigDecimal d : matrix.get(y)) {
				row.add(d);
			}
			for(BigDecimal d : other.getRow(y)) {
				row.add(d);
			}
			newMatrix.add(row);
		}
		PrecisionMatrix result = new PrecisionMatrix(height,newWidth);
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
		BigDecimal max = BigDecimal.valueOf(-1);
		for(int i = 0; i < height; i++) {
			//System.out.println(get(i,index));
			if(get(i,index).abs().compareTo(max) > 0) {
				max = get(i,index).abs();
				result = i;
			}
		}
		//System.out.println(result);
		return result;
	}
	public static PrecisionMatrix getFromFile(String fileName) {
		ArrayList<ArrayList<BigDecimal>> matrix = new ArrayList<ArrayList<BigDecimal>>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));
			while(scanner.hasNextLine()) {
				ArrayList<BigDecimal> array = new ArrayList<BigDecimal>();
				String line = scanner.nextLine();
				String[] arr = line.split("\t");
				for(int i = 0; i < arr.length; i++) {
					array.add(BigDecimal.valueOf(Double.parseDouble(arr[i].trim())));
				}
				matrix.add(array);
			}
			scanner.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		PrecisionMatrix result = new PrecisionMatrix(matrix.size(),matrix.get(0).size());
		for(int y = 0; y < result.getHeight(); y++) {
			for(int x = 0; x < result.getWidth(); x++) {
				result.set(y, x, matrix.get(y).get(x));
			}
		}
		return result;
	}
	public static void main(String[] args) {
		PrecisionMatrix matrix = PrecisionMatrix.getFromFile("TestMatrix.txt");
		PrecisionMatrix inverse = matrix.getInverse();
		inverse.printToFile("inverse.csv");
	}

}
