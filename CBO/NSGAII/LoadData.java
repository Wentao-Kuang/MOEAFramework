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
	private int l;
	private int v;
	public LoadData(int napplications, int nfunctions, int nusers, int nlocations, int nVMs){
		this.a=napplications;
		this.f=nfunctions;
		this.u=nusers;
		this.v=nVMs;
		this.l=nlocations;
		//System.out.println(Arrays.deepToString(readIntMatrix("CBO/Belong", napplications, nfunctions)));
		//System.out.println(Arrays.deepToString(readIntMatrix("CBO/Task", napplications, nfunctions)));
		//System.out.println(Arrays.deepToString(readIntMatrix("CBO/Frequency", u, a)));
		//System.out.println(Arrays.toString(readDoubleVector("CBO/Price",nVMs)));
	}
	
	public static void main(String[] args) {
		int a=5;
		int f=10;
		int u=10;
		int l=10;
		int v=3;
		LoadData load=new LoadData(a,f,u,l,v);
		System.out.println("Latency: "+Arrays.deepToString(load.readLatency()));
		System.out.println("Belong: "+Arrays.deepToString(load.readBelong()));
		System.out.println("Task: "+Arrays.deepToString(load.readTask()));
		System.out.println("Frequency: "+Arrays.deepToString(load.readFrequency()));
		System.out.println("Computing: "+Arrays.toString(load.readComputing()));
		System.out.println("Vram: "+Arrays.toString(load.readVram()));
		System.out.println("Vbw: "+Arrays.toString(load.readVbw()));
		System.out.println("Price: "+Arrays.toString(load.readPrice()));
		System.out.println("Acpu: "+Arrays.toString(load.readAcpu()));
		System.out.println("Aram: "+Arrays.toString(load.readAram()));
		System.out.println("Abw: "+Arrays.toString(load.readAbw()));
	}
	
	/**
	 * read Belong Matrix
	 * @return
	 */
	public int[][] readBelong(){
		return readIntMatrix("CBO/datasets/Belong",l,v*l);
	}
	
	/**
	 * read Task Matrix
	 * @return
	 */
	public int[][] readTask(){
		return readIntMatrix("CBO/datasets/Task",a,f);
	}
	
	/**
	 * read Frequency Matrix
	 * @return
	 */
	public int[][] readFrequency(){
		return readIntMatrix("CBO/datasets/Frequency",u,f);
	}
	
	/**
	 * read Computing Vector
	 * @return
	 */
	public int[] readComputing(){
		return readIntVector("CBO/datasets/Computing",v*l);
	}
	
	/**
	 * read Vram Vector
	 * @return
	 */
	public int[] readVram(){
		return readIntVector("CBO/datasets/Vram",v*l);
	}
	
	/**
	 * read Vbw Vector
	 * @return
	 */
	public int[] readVbw(){
		return readIntVector("CBO/datasets/Vbw",v*l);
	}
	
	/**
	 * read Price Vector
	 * @return
	 */
	public double[] readPrice(){
		return readDoubleVector("CBO/datasets/Price",v*l);
	}
	
	/**
	 * read Acpu vector
	 * @return
	 */
	public int[] readAcpu(){
		return readIntVector("CBO/datasets/Acpu",a);
	}
	
	/**
	 * read Aram vector
	 * @return
	 */
	public int[] readAram(){
		return readIntVector("CBO/datasets/Aram",a);
	}
	
	/**
	 * read Abw vector
	 * @return
	 */
	public int[] readAbw(){
		return readIntVector("CBO/datasets/Abw",a);
	}
	
	
	/**
	 * read latency matrix
	 * @param filename
	 * @param x
	 * @param y
	 * @return
	 */
	public double[][] readLatency() {
		return readDoubleMatrix("CBO/datasets/Latency",u,l);
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
