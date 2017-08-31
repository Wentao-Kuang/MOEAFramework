package PSO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.lang3.ArrayUtils;

public class LoadData {
	private int a;
	private int u;
	private int l;
	private int v;
	private double[][] latency;
	private int[] Computing;
	private double[] Price;
	private int[] Vram;
	private int[] Vbw;
	private int[] Acpu;
	private int[] Aram;
	private int[] Abw;
	private int[][] Belong;
	private double[][] Task;
	private int[][] Frequency;
	private double[][] Latency;

	public LoadData(int napplications, int nusers, int nlocations, int nVMs) {
		this.a = napplications;
		this.u = nusers;
		this.v = nVMs;
		this.l = nlocations;
		this.latency = readLatency();
		// System.out.println("Latency: "+Arrays.deepToString(this.latency));
		this.Belong = readBelong();
		// System.out.println("Belong: "+Arrays.deepToString(this.Belong));
		this.Task = readTask();
		// System.out.println("Task: "+Arrays.deepToString(this.Task));
		this.Frequency = readFrequency();
		// System.out.println("Frequency: "+Arrays.deepToString(this.Frequency));
		this.Computing = readComputing();
		// System.out.println("Computing: "+Arrays.toString(this.Computing));
		this.Vram = readVram();
		// System.out.println("Vram: "+Arrays.toString(this.Vram));
		this.Vbw = readVbw();
		// System.out.println("Vbw: "+Arrays.toString(this.Vbw));
		this.Price = readPrice();
		// System.out.println("Price: "+Arrays.toString(this.Price));
		this.Acpu = readAcpu();
		// System.out.println("Acpu: "+Arrays.toString(this.Acpu));
		this.Aram = readAram();
		// System.out.println("Aram: "+Arrays.toString(this.Aram));
		this.Abw = readAbw();
		// System.out.println("Abw: "+Arrays.toString(this.Abw));
	}

	public static void main(String[] args) {
		int a = 10;
		int u = 20;
		int l = 20;
		int v = 7;
		LoadData load = new LoadData(a, u, l, v);
		load.minCost();
		load.maxCost();
		load.minResponse();
		load.maxResponse();
	}

	/**
	 * read Belong Matrix
	 * 
	 * @return
	 */
	public int[][] readBelong() {
		return readIntMatrix("CBO/datasets/Belong", l, v * l);
	}

	/**
	 * read Task Matrix
	 * 
	 * @return
	 */
	public double[][] readTask() {
		return readDoubleMatrix("CBO/datasets/tasks", a, u);
	}

	/**
	 * read Frequency Matrix
	 * 
	 * @return
	 */
	public int[][] readFrequency() {
		return readIntMatrix("CBO/datasets/Frequency", a, u);
	}

	/**
	 * read Computing Vector
	 * 
	 * @return
	 */
	public int[] readComputing() {
		return readIntVector("CBO/datasets/Computing", v * l);
	}

	/**
	 * read Vram Vector
	 * 
	 * @return
	 */
	public int[] readVram() {
		return readIntVector("CBO/datasets/Vram", v * l);
	}

	/**
	 * read Vbw Vector
	 * 
	 * @return
	 */
	public int[] readVbw() {
		return readIntVector("CBO/datasets/Vbw", v * l);
	}

	/**
	 * read Price Vector
	 * 
	 * @return
	 */
	public double[] readPrice() {
		return readDoubleVector("CBO/datasets/Price", v * l);
	}

	/**
	 * read Acpu vector
	 * 
	 * @return
	 */
	public int[] readAcpu() {
		return readIntVector("CBO/datasets/Acpu", a);
	}

	/**
	 * read Aram vector
	 * 
	 * @return
	 */
	public int[] readAram() {
		return readIntVector("CBO/datasets/Aram", a);
	}

	/**
	 * read Abw vector
	 * 
	 * @return
	 */
	public int[] readAbw() {
		return readIntVector("CBO/datasets/Abw", a);
	}

	/**
	 * read latency matrix
	 * 
	 * @param filename
	 * @param x
	 * @param y
	 * @return
	 */
	public double[][] readLatency() {
		return readDoubleMatrix("CBO/datasets/Latency", u, l);
	}

	public void minCost() {
		Integer[] priceRanks = new Integer[Price.length];
		for (int i = 0; i < priceRanks.length; ++i) {
			priceRanks[i] = i;
		}
		Comparator<Integer> gc = new ArrayIndexComparator(Price);
		Arrays.sort(priceRanks, gc);

		Arrays.sort(Computing);
		double[] aTask = new double[a];
		for (int i = 0; i < a; i++) {
			aTask[i] = 0;
			for (int j = 0; j < u; j++) {
				aTask[i] += Task[i][j];
			}
		}
		Integer[] aTaskRanks = new Integer[aTask.length];
		for (int i = 0; i < aTaskRanks.length; ++i) {
			aTaskRanks[i] = i;
		}
		Comparator<Integer> gc1 = new ArrayIndexComparator(aTask);
		Arrays.sort(aTaskRanks, gc1);
		double minCost = 0;
		for (int i = 0; i < a; i++) {
			minCost += ((aTask[aTaskRanks[i]] / Computing[i]) * Price[priceRanks[Price.length
					- i - 1]]) / 3600;
		}
		System.out.println("minCost= " + minCost);
		
		
	}

	public void maxCost() {
		double[] p = readPrice();
		int[] c = readComputing();
		double[] priceRate = new double[p.length];
		for (int i = 0; i < p.length; i++) {
			priceRate[i] = p[i] / c[i];
		}

		Integer[] priceRanks = new Integer[priceRate.length];
		for (int i = 0; i < priceRanks.length; ++i) {
			priceRanks[i] = i;
		}
		Comparator<Integer> gc = new ArrayIndexComparator(priceRate);
		Arrays.sort(priceRanks, gc);

		double[] aTask = new double[a];
		for (int i = 0; i < a; i++) {
			aTask[i] = 0;
			for (int j = 0; j < u; j++) {
				aTask[i] += Task[i][j];
			}
		}
		Integer[] aTaskRanks = new Integer[aTask.length];
		for (int i = 0; i < aTaskRanks.length; ++i) {
			aTaskRanks[i] = i;
		}
		Comparator<Integer> gc1 = new ArrayIndexComparator(aTask);
		Arrays.sort(aTaskRanks, gc1);
		double maxCost = 0;
		for (int i = 1; i < a; i++) {
			// System.out.println(aTask[aTaskRanks[i]] );
			// System.out.println(priceRate[priceRanks[priceRate.length - i]]);
			maxCost += (aTask[aTaskRanks[i]] * priceRate[priceRanks[priceRate.length
					- i]]) / 3600;
		}
		for (int i = 0; i < priceRate.length - a; i++) {
			// System.out.println(aTask[aTaskRanks[0]] );
			// System.out.println(priceRate[priceRanks[priceRate.length - a -
			// i]]);
			maxCost += aTask[aTaskRanks[0]]
					* priceRate[priceRanks[priceRate.length - a - i - 1]]
					/ 3600;
		}
		System.out.println("maxCost=: " + maxCost);
		writeDouble("CBO/datasets/maxCost",maxCost);
	}

	public void minResponse() {	
		double minResponse=0;
		System.out.println("minResponse=: " + 0);
		writeDouble("CBO/datasets/maxCost",minResponse);
	}

	public void maxResponse() {
		//System.out.println(maxExecution);
		double[][] t=readTask();
		double[][] l=readLatency();
		double maxExecution = 0;
		double maxLatency = 0;
		for(int i=0;i<a;i++){
			for(int j=0;j<u;j++){
				maxExecution+=Task[i][j]/Computing[i];
				Arrays.sort(l[j]);
				maxLatency+=l[j][l[0].length-1]*Frequency[i][j];
			}
		}
		maxExecution=maxExecution/3600;
		maxLatency=maxLatency/3600;
		double maxResponse=maxLatency+maxExecution;
		System.out.println("maxResponse=: " + maxResponse);
	}

	/**
	 * read int vector to file
	 * 
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
	 * 
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
	 * 
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
	
	public void writeDouble(String filename, double d) {
		try {
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(filename), "UTF-8");
			BufferedWriter bw = new BufferedWriter(write);
					bw.write(d+"");
			bw.flush();
		} catch (IOException e) {
			System.err.println("Writing data error");
			// why does the catch need its own curly?
		}
	}
}