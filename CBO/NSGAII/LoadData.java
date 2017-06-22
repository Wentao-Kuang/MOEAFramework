package NSGAII;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
public class LoadData {
	private int a;
	private int f;
	private int u;
	private int v;
	public LoadData(int napplications, int nfunctions, int nusers, int nVMs){
		this.a=napplications;
		this.f=nfunctions;
		this.u=nusers;
		this.v=nVMs;
		//System.out.println(Arrays.deepToString(readIntMatrix("CBO/Belong", napplications, nfunctions)));
		//System.out.println(Arrays.deepToString(readIntMatrix("CBO/Task", napplications, nfunctions)));
		//System.out.println(Arrays.deepToString(readIntMatrix("CBO/Frequency", u, a)));
		//System.out.println(Arrays.toString(readDoubleVector("CBO/Price",nVMs)));
	}
	
	public static void main(String[] args) {
		int a=10;
		int f=30;
		int u=30;
		int v=50;
//		LoadData l=new LoadData(a,f,u,v);
//		System.out.println(Arrays.deepToString(l.readLatency()));
//		System.out.println(Arrays.deepToString(l.readBelong()));
//		System.out.println(Arrays.deepToString(l.readTask()));
//		System.out.println(Arrays.deepToString(l.readFrequency()));
//		System.out.println(Arrays.toString(l.readComputing()));
//		System.out.println(Arrays.toString(l.readVram()));
//		System.out.println(Arrays.toString(l.readVbw()));
//		System.out.println(Arrays.toString(l.readPrice()));
//		System.out.println(Arrays.toString(l.readAcpu()));
//		System.out.println(Arrays.toString(l.readAram()));
//		System.out.println(Arrays.toString(l.readAbw()));
	}
	
	/**
	 * read Belong Matrix
	 * @return
	 */
	public int[][] readBelong(){
		return readIntMatrix("CBO/Belong",a,f);
	}
	
	/**
	 * read Task Matrix
	 * @return
	 */
	public int[][] readTask(){
		return readIntMatrix("CBO/Task",a,f);
	}
	
	/**
	 * read Frequency Matrix
	 * @return
	 */
	public int[][] readFrequency(){
		return readIntMatrix("CBO/Frequency",u,f);
	}
	
	/**
	 * read Computing Vector
	 * @return
	 */
	public int[] readComputing(){
		return readIntVector("CBO/Computing",v);
	}
	
	/**
	 * read Vram Vector
	 * @return
	 */
	public int[] readVram(){
		return readIntVector("CBO/Vram",v);
	}
	
	/**
	 * read Vbw Vector
	 * @return
	 */
	public int[] readVbw(){
		return readIntVector("CBO/Vbw",v);
	}
	
	/**
	 * read Price Vector
	 * @return
	 */
	public double[] readPrice(){
		return readDoubleVector("CBO/Price",v);
	}
	
	/**
	 * read Acpu vector
	 * @return
	 */
	public int[] readAcpu(){
		return readIntVector("CBO/Acpu",a);
	}
	
	/**
	 * read Aram vector
	 * @return
	 */
	public int[] readAram(){
		return readIntVector("CBO/Aram",a);
	}
	
	/**
	 * read Abw vector
	 * @return
	 */
	public int[] readAbw(){
		return readIntVector("CBO/Abw",a);
	}
	
	
	/**
	 * read latency matrix
	 * @param filename
	 * @param x
	 * @param y
	 * @return
	 */
	public double[][] readLatency() {
		return readDoubleMatrix("CBO/Latency",u,v);
	}
	
	
	
	/**
	 * read int vector to file
	 * @param filename
	 * @param x
	 * @return
	 */
	public int[] readIntVector(String filename, int x) {
		int[] vector = new int[x];
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					filename), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			for (int i = 0; i < x; i++) {
				vector[i] = Integer.parseInt(br.readLine());
			}
			read.close();
		} catch (IOException e) {
			System.err.println("Reading data error");
		}
		return vector;
	}
	
	/**
	 * read double vector
	 * @param filename
	 * @param x
	 * @return
	 */
	public double[] readDoubleVector(String filename, int x) {
		double[] vector = new double[x];
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					filename), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			for (int i = 0; i < x; i++) {
				vector[i] = Double.parseDouble(br.readLine());
			}
			read.close();
		} catch (IOException e) {
			System.err.println("Reading data error");
		}
		return vector;
	}
	
	
	/**
	 * read int matrix into file to store generated data
	 * @param filename
	 * @param x
	 * @param y
	 * @return
	 */
	public int[][] readIntMatrix(String filename, int x, int y) {
		int[][] matrix = new int[x][y];
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					filename), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					matrix[i][j] = Integer.parseInt(br.readLine());
				}
			}
			read.close();
		} catch (IOException e) {
			System.err.println("Reading data error");
		}
		return matrix;
	}
	
	public double[][] readDoubleMatrix(String filename, int x, int y) {
		double[][] matrix = new double[x][y];
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					filename), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					matrix[i][j] = Double.parseDouble(br.readLine());
				}
			}
			read.close();
		} catch (IOException e) {
			System.err.println("Reading data error");
		}
		return matrix;
	}
}
