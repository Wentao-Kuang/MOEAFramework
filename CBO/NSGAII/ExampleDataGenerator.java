package NSGAII;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.ThreadLocalRandom;
public class ExampleDataGenerator {
	
	public int[][] randomWorkload(int u, int f){
		int[][] workload = new int[u][f];
		for(int i=0;i<u;i++){
			for(int j=0;j<f;j++){
				workload[i][j] = ThreadLocalRandom.current().nextInt(10000, 500000);
			}
		}
		return workload;
	}
	
	public int[][] randomComputing(int v, int f){
		int[][] computing = new int[v][f];
		for(int i=0;i<v;i++){
			for(int j=0;j<f;j++){
				computing[i][j] = ThreadLocalRandom.current().nextInt(1000, 5000);
			}
		}
		return computing;
	}
	
	public double[][] randomPrice(int v, int f){
		double[][] price = new double[v][f];
		for(int i=0;i<v;i++){
			for(int j=0;j<f;j++){
				price[i][j] = ThreadLocalRandom.current().nextDouble(0.001, 0.020);
			}
		}
		return price;
	}
	
	public double[][] randomLatency(int v, int u){
		double[][] latency = new double[v][u];
		for(int i=0;i<v;i++){
			for(int j=0;j<u;j++){
				latency[i][j] = ThreadLocalRandom.current().nextDouble(0, 5);
			}
		}
		return latency;
	}
	
	public void writeIntMatrix(String filename, int[][] matrix) {
	    try {
	    	OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filename),"UTF-8");
	        BufferedWriter bw = new BufferedWriter(write);
	        for (int i = 0; i < matrix.length; i++) {
	            for (int j = 0; j < matrix[i].length; j++) {
	                   
	                    bw.write(matrix[i][j]+"\n");
	            }}
	        bw.flush();
	    } catch (IOException e) {
	        //why does the catch need its own curly?
	    }
	}
	
	public void writeDoubleMatrix(String filename, double[][] matrix) {
	    try {
	    	OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filename),"UTF-8");
	        BufferedWriter bw = new BufferedWriter(write);
	        for (int i = 0; i < matrix.length; i++) {
	            for (int j = 0; j < matrix[i].length; j++) {  
	                    bw.write(((Double)matrix[i][j]).toString() + "\n");
	        }
	        }
	        bw.flush();
	    } catch (IOException e) {
	        //why does the catch need its own curly?
	    }
	}
	
	public int[][] readIntMatrix(String filename, int x, int y) {
		int[][] matrix=new int[x][y];
	    try {
	    	InputStreamReader read = new InputStreamReader(new FileInputStream(filename),"UTF-8");
	    	BufferedReader br = new BufferedReader(read);
	    	for(int i=0;i<x;i++){
	    		for(int j=0;j<y;j++){
	    			matrix[i][j]=Integer.parseInt(br.readLine());
	    		}
	    	}
	        read.close();  
	    } catch (IOException e) {
	        //why does the catch need its own curly?
	    }
	    return matrix;
	}
	
	public double[][] readDoubleMatrix(String filename, int x, int y) {
		double[][] matrix=new double[x][y];
	    try {
	    	InputStreamReader read = new InputStreamReader(new FileInputStream(filename),"UTF-8");
	    	BufferedReader br = new BufferedReader(read);
	    	for(int i=0;i<x;i++){
	    		for(int j=0;j<y;j++){
	    			matrix[i][j]=Double.parseDouble(br.readLine());
	    		}
	    	}
	        read.close();  
	    } catch (IOException e) {
	        //why does the catch need its own curly?
	    }
	    return matrix;
	}

	
	public static void main(String[] args) {

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive

		ExampleDataGenerator g = new ExampleDataGenerator();
		//Generate and write workload data
		int f=10;
		int u=30;
		int v=100;
		g.writeIntMatrix("CBO/workload", g.randomWorkload(u, f));
		g.writeIntMatrix("CBO/computing", g.randomComputing(v, f));
		g.writeDoubleMatrix("CBO/price", g.randomPrice(v, f));
		g.writeDoubleMatrix("CBO/latency", g.randomLatency(v, u));
		g.readIntMatrix("CBO/workload", u, f);
		g.readIntMatrix("CBO/computing", v, f);
		g.readDoubleMatrix("CBO/price", v, f);
		g.readDoubleMatrix("CBO/latency", v, u);
	}

	
}
