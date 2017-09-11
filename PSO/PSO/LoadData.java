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


public class LoadData {
	private int a;
	private int u;
	private int l;
	private int v;

	public LoadData(int napplications, int nusers, int nlocations, int nVMs) {
		this.a = napplications;
		this.u = nusers;
		this.v = nVMs;
		this.l = nlocations;
//		this.latency = readLatency();
//		System.out.println("Latency: "+Arrays.deepToString(this.latency));
//		this.Belong = readBelong();
//		System.out.println("Belong: "+Arrays.deepToString(this.Belong));
//		this.Task = readTask();
//		System.out.println("Task: "+Arrays.deepToString(this.Task));
//		this.Frequency = readFrequency();
//		System.out.println("Frequency: "+Arrays.deepToString(this.Frequency));
//		this.Computing = readComputing();
//		System.out.println("Computing: "+Arrays.toString(this.Computing));
//		this.Vram = readVram();
//		System.out.println("Vram: "+Arrays.toString(this.Vram));
//		this.Vbw = readVbw();
//		System.out.println("Vbw: "+Arrays.toString(this.Vbw));
//		this.Price = readPrice();
//		System.out.println("Price: "+Arrays.toString(this.Price));
//		this.Acpu = readAcpu();
//		System.out.println("Acpu: "+Arrays.toString(this.Acpu));
//		this.Aram = readAram();
//		System.out.println("Aram: "+Arrays.toString(this.Aram));
//		this.Abw = readAbw();
//		System.out.println("Abw: "+Arrays.toString(this.Abw));

	}

	public static void main(String[] args) {
		int a=20;
		int u=50;
		int l=50;
		int v=7;
		LoadData load = new LoadData(a, u, l, v);
		load.minCost();
		load.maxCost();
		load.minResponse();
		load.maxResponse();
		System.out.println("minCost: "+load.readMinCost());
		System.out.println("maxCost: "+load.readMaxCost());
		System.out.println("minResponse: "+load.readMinResponse());
		System.out.println("maxResponse: "+load.readMaxResponse());
	}

	/**
	 * read Belong Matrix
	 *
	 * @return
	 */
	public int[][] readBelong() {
		return readIntMatrix("PSO/datasets/Belong", l, v * l);
	}

	/**
	 * read Task Matrix
	 *
	 * @return
	 */
	public double[][] readTask() {
		return readDoubleMatrix("PSO/datasets/tasks", a, u);
	}

	/**
	 * read Frequency Matrix
	 *
	 * @return
	 */
	public int[][] readFrequency() {
		return readIntMatrix("PSO/datasets/Frequency", a, u);
	}

	/**
	 * read Computing Vector
	 *
	 * @return
	 */
	public int[] readComputing() {
		return readIntVector("PSO/datasets/Computing", v * l);
	}

	/**
	 * read Vram Vector
	 *
	 * @return
	 */
	public int[] readVram() {
		return readIntVector("PSO/datasets/Vram", v * l);
	}

	/**
	 * read Vbw Vector
	 *
	 * @return
	 */
	public int[] readVbw() {
		return readIntVector("PSO/datasets/Vbw", v * l);
	}

	/**
	 * read Price Vector
	 *
	 * @return
	 */
	public double[] readPrice() {
		return readDoubleVector("PSO/datasets/Price", v * l);
	}

	/**
	 * read Acpu vector
	 *
	 * @return
	 */
	public int[] readAcpu() {
		return readIntVector("PSO/datasets/Acpu", a);
	}

	/**
	 * read Aram vector
	 *
	 * @return
	 */
	public int[] readAram() {
		return readIntVector("PSO/datasets/Aram", a);
	}

	/**
	 * read Abw vector
	 *
	 * @return
	 */
	public int[] readAbw() {
		return readIntVector("PSO/datasets/Abw", a);
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
		return readDoubleMatrix("PSO/datasets/Latency", u, l);
	}

	public double readMinCost() {
		return readDouble("PSO/datasets/minCost");
	}
	
	

	public double readMaxCost() {
		return readDouble("PSO/datasets/maxCost");
	}

	public double readMaxResponse() {
		return readDouble("PSO/datasets/maxResponse");
	}

	public double readMinResponse() {
		return readDouble("PSO/datasets/minResponse");
	}

	public void minCost() {
		double[] p=readPrice();
		int[] c=readComputing();
		double[][] Task=readTask();
		double[] priceRate = new double[p.length];
		for (int i = 0; i < p.length; i++) {
			priceRate[i] = p[i] / c[i];
		}
		Integer[] priceRank = new Integer[priceRate.length];
		for (int i = 0; i < priceRank.length; ++i) {
			priceRank[i] = i;
		}
		Comparator<Integer> gc = new ArrayIndexComparator(priceRate);
		Arrays.sort(priceRank, gc);
		
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
		
		boolean[] solution = new boolean[a*l*v];
		double minCost = 0;
		for (int i = 0; i < a; i++) {
			minCost += (aTask[aTaskRanks[i]]* priceRate[priceRank[priceRate.length- i - 1]]) / 3600;
			solution[aTaskRanks[i]*v * l+priceRank[priceRate.length - i - 1]]=true;
		}
		//System.out.println("minCost= " + minCost);
		writeDouble("PSO/datasets/minCost",minCost);
		//System.out.println(Arrays.toString(solution));
		writeBooleanVector("PSO/datasets/minCostSolution",solution);
	}
	

	

	public void maxCost() {
		double[] p = readPrice();
		int[] c = readComputing();
		double[][] Task =readTask();
		double[] priceRate = new double[p.length];

		for (int i = 0; i < p.length; i++) {
			priceRate[i] = p[i] / c[i];
		}
		Arrays.sort(priceRate);
		double[] aTask = new double[a];
		for (int i = 0; i < a; i++) {
			aTask[i] = 0;
			for (int j = 0; j < u; j++) {
				aTask[i] += Task[i][j];
			}
		}
		Arrays.sort(aTask);
		//System.out.println(Arrays.toString(aTask));
		double maxCost = 0;
		for (int i = 0; i < a; i++) {
			for(int j= 0;j<priceRate.length;j++){
				maxCost += (aTask[i] * priceRate[j]) / 3600;
			}
			//System.out.println(aTask[aTaskRanks[i]] );
			//System.out.println(priceRate[priceRanks[priceRate.length - i]]);
		}
		//System.out.println("maxCost=: " + maxCost);
		writeDouble("PSO/datasets/maxCost",maxCost);
	}


	public void preMaxCost() {
		double[] p = readPrice();
		int[] c = readComputing();
		double[][] Task =readTask();
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
			//System.out.println(aTask[aTaskRanks[i]] );
			//System.out.println(priceRate[priceRanks[priceRate.length - i]]);
			maxCost += (aTask[aTaskRanks[i]] * priceRate[priceRanks[priceRate.length
					- i]]) / 3600;
		}
		for (int i = 0; i < priceRate.length - a; i++) {
			// System.out.println(aTask[aTaskRanks[0]] );
			// System.out.println(priceRate[priceRanks[priceRate.length - a -
			// i]]);
			maxCost += (aTask[aTaskRanks[0]]
					* priceRate[priceRanks[i]])
					/ 3600;
		}
		//System.out.println("maxCost=: " + maxCost);
		writeDouble("PSO/datasets/maxCost",maxCost);
	}

	public void minResponse() {
		double minResponse=0;
		//System.out.println("minResponse=: " + 0);
		writeDouble("PSO/datasets/minResponse",minResponse);
	}

	public void maxResponse() {
		//System.out.println(maxExecution);
		int[][] Frequency=readFrequency();
		int[] Computing = readComputing();
		double[][] Task =readTask();
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
		//System.out.println("maxResponse=: " + maxResponse);
		writeDouble("PSO/datasets/maxResponse",maxResponse);
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

	public double readDouble(String filename) {
		double d = 0;
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					filename), "UTF-8");
			BufferedReader br = new BufferedReader(read);

					d = Double.parseDouble(br.readLine());

			read.close();
		} catch (IOException e) {
			System.err.println("Reading data error");
		}
		return d;
	}
	
	public void writeBooleanVector(String filename, boolean[] vector) {
		try {
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(filename), "UTF-8");
			BufferedWriter bw = new BufferedWriter(write);
			for (int i = 0; i < vector.length; i++) {
					bw.write(vector[i] + "\n");
			}
			bw.flush();
		} catch (IOException e) {
			System.err.println("Writing data error");
			// why does the catch need its own curly?
		}
	}
	public boolean[] readBooleanVector(String filename, int x) {
		boolean[] vector = new boolean[x];
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					filename), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			for (int i = 0; i < x; i++) {
				vector[i] = Boolean.parseBoolean(br.readLine());
			}
			read.close();
		} catch (IOException e) {
			System.err.println("Reading data error");
		}
		return vector;
	}

}
