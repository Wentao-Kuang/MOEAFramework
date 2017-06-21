package NSGAII;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

	private static int[][] Belong;
	private static int[][] Task;
	private static int[][] Frequency;

	/**
	 * Function Task size and belong generator
	 * @param napplications
	 * @param nfunctions
	 */
	
	public void applicationGen(int napplications, int nfunctions) {
		Belong = new int[napplications][nfunctions];
		Task = new int[napplications][nfunctions];
		while(true){
		for (int a = 0; a < napplications; a++) {
			// share function rate
			boolean rate = ThreadLocalRandom.current().nextBoolean();
			int sumf = 0;// number of f belong to a
			do {
				for (int f = 0; f < nfunctions; f++) {
					if (rate) {
						Belong[a][f] = 1;
						Task[a][f] = ThreadLocalRandom.current().nextInt(2000,40000);
						sumf++;
					} else {
						Belong[a][f] = 0;
						Task[a][f] = 0;
					}
					rate = ThreadLocalRandom.current().nextBoolean();
				}
			} while (sumf == 0);//constraint each application have at least one function
		}
		if(sumCol(Belong)) break;//constraint each function have belongings.
		}
		System.out.println("Belong: "+Arrays.deepToString(Belong));
		System.out.println("Task: "+Arrays.deepToString(Task));
	}
	
	
	/**
	 * function invocation frequency generator
	 * @param nusers
	 * @param nfunctions
	 */
	public void frequencyGen(int nusers,int nfunctions){
		Frequency = new int[nusers][nfunctions];
		for(int u=0;u<nusers;u++){
			for(int f=0;f<nfunctions;f++){
				Frequency[u][f]=ThreadLocalRandom.current().nextInt(0,100);
			}
		}
		System.out.println("Frequency: "+Arrays.deepToString(Frequency));
	}
	
	/**
	 * sum the column of a matrix to make sure each col have non-null value.
	 * @return
	 */
	public boolean sumCol(int[][] m){
		boolean b =true;
		for(int r=0;r<m.length;r++){
			int sum = 0;
			for(int c=0;c<m[r].length;c++){
				sum+=m[r][c];
			}
			if(sum==0){
				b=false;
			}
		}
		return b;
	}
	
	/**
	 * write int matrix into file to store generated data
	 * @param filename
	 * @param matrix
	 */
	
	public void writeIntMatrix(String filename, int[][] matrix) {
		try {
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(filename), "UTF-8");
			BufferedWriter bw = new BufferedWriter(write);
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {

					bw.write(matrix[i][j] + "\n");
				}
			}
			bw.flush();
		} catch (IOException e) {
			// why does the catch need its own curly?
		}
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
			// why does the catch need its own curly?
		}
		return matrix;
	}
	
	//test
	
	public static void main(String[] args) {
		DataGenerator d = new DataGenerator();
		int a=10;
		int f=30;
		int u=30;
		
		//testing function belonging and task size generator
		d.applicationGen(a, f);
		
		//testing frequency generator
		d.frequencyGen(u, f);
		
		//testing write and read matrix
		//d.writeIntMatrix("CBO/Belong", Belong);
		//d.writeIntMatrix("CBO/Task", Task);
		//d.writeIntMatrix("CBO/Frequency", Frequency);
		//System.out.println(Arrays.deepToString(d.readIntMatrix("CBO/Belong", a, f)));
		//System.out.println(Arrays.deepToString(d.readIntMatrix("CBO/Task", a, f)));
		//System.out.println(Arrays.deepToString(d.readIntMatrix("CBO/Frequency", a, f)));
		
	}

	public int[][] randomWorkload(int u, int f) {
		int[][] workload = new int[u][f];
		for (int i = 0; i < u; i++) {
			for (int j = 0; j < f; j++) {
				workload[i][j] = ThreadLocalRandom.current().nextInt(10000,
						500000);
			}
		}
		return workload;
	}

	public int[][] randomComputing(int v, int f) {
		int[][] computing = new int[v][f];
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < f; j++) {
				computing[i][j] = ThreadLocalRandom.current().nextInt(1000,
						5000);
			}
		}
		return computing;
	}

	public double[][] randomPrice(int v, int f) {
		double[][] price = new double[v][f];
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < f; j++) {
				price[i][j] = ThreadLocalRandom.current().nextDouble(0.001,
						0.020);
			}
		}
		return price;
	}

	public double[][] randomLatency(int v, int u) {
		double[][] latency = new double[v][u];
		for (int i = 0; i < v; i++) {
			for (int j = 0; j < u; j++) {
				latency[i][j] = ThreadLocalRandom.current().nextDouble(0, 5);
			}
		}
		return latency;
	}

	
	public void writeDoubleMatrix(String filename, double[][] matrix) {
		try {
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(filename), "UTF-8");
			BufferedWriter bw = new BufferedWriter(write);
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					bw.write(((Double) matrix[i][j]).toString() + "\n");
				}
			}
			bw.flush();
		} catch (IOException e) {
			// why does the catch need its own curly?
		}
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
			// why does the catch need its own curly?
		}
		return matrix;
	}

	/**
	 * public static void main(String[] args) {
	 * 
	 * // nextInt is normally exclusive of the top value, // so add 1 to make it
	 * inclusive
	 * 
	 * DataGenerator g = new DataGenerator(); //Generate and write workload data
	 * int f=10; int u=30; int v=100; g.writeIntMatrix("CBO/workload",
	 * g.randomWorkload(u, f)); g.writeIntMatrix("CBO/computing",
	 * g.randomComputing(v, f)); g.writeDoubleMatrix("CBO/price",
	 * g.randomPrice(v, f)); g.writeDoubleMatrix("CBO/latency",
	 * g.randomLatency(v, u)); g.readIntMatrix("CBO/workload", u, f);
	 * g.readIntMatrix("CBO/computing", v, f); g.readDoubleMatrix("CBO/price",
	 * v, f); g.readDoubleMatrix("CBO/latency", v, u); }
	 */

}
