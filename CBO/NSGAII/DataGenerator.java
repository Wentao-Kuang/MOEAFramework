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

	
	//test
	public static void main(String[] args) {
		
		int a=25;
		int f=60;
		int u=60;
		int l=60;
		int v=7;
		new DataGenerator(a,f,u,l,v);
		
	}
	
	/**
	 * initialization
	 * @param napplications
	 * @param nfunction
	 * @param nusers
	 */
	public DataGenerator(int napplications, int nfunctions, int nusers,int nlocations, int nVMs){
		
		System.out.println("Initializing data generator");
		System.out.println("Application Num: "+napplications+" ; Functions Num: "+nfunctions+" ; Usergroups Num: "+nusers+"Locations Num: "+nlocations+" ; VMs Num: "+nVMs);
		//generate belonging and task size matrix
		applicationGen(napplications, nfunctions);
		//generate frequency matrix
		frequencyGen(nusers, nfunctions);
		//generate VM information
		VMGen(nVMs*nlocations,nVMs);
		//generate application requirement
		AGen(napplications);
		VLGen(nlocations,nVMs);
		//resize latency data
		latencyResize(nusers,nlocations);
	}
	

	//predefined VM types
	private int[][] VMtypes = new int[][]{{3500,16,1250},{3000,8,1000},{2500,4,1000},{2000,8,1250},{1500,4,1000},{1000,2,750},{500,1,500}};
	private double[][] VMprice = new double[][]{{0.398,0.618},{0.199,0.309},{0.100,0.155},{0.094,0.162},{0.047,0.081},{0.023,0.041},{0.012,0.020}};
	
	//predefined application requirements
	private int[] CPU = new int[]{3500,3250,3000,2750,2500,2250,2000,1750,1500,1250,1000,750,500};
	private int[] ram = new int[]{1,2,4,8,16};
	private int[] bw = new int[]{500,750,1000,1250};
	
	/**
	 * predefined application generator
	 * @param napplications
	 */
	public void AGen(int napplications){
		int[] Acpu= new int[napplications];
		int[] Aram = new int[napplications];
		int[] Abw = new int[napplications];
		for(int a=0;a<napplications;a++){
			Acpu[a]=CPU[ThreadLocalRandom.current().nextInt(0, CPU.length)];
			Aram[a]=ram[ThreadLocalRandom.current().nextInt(0,ram.length)];
			Abw[a]=bw[ThreadLocalRandom.current().nextInt(0,bw.length)];
		}
		System.out.println("Acpu: "+Arrays.toString(Acpu));
		System.out.println("Aram: "+Arrays.toString(Aram));
		System.out.println("Abw: "+Arrays.toString(Abw));
		//write data to file
		writeIntVector("CBO/datasets/Acpu", Acpu);
		writeIntVector("CBO/datasets/Aram", Aram);
		writeIntVector("CBO/datasets/Abw", Abw);
	}
	
	/**
	 * VM location generator
	 * @param nVMs,nLocations
	 */
	public void VLGen(int nlocations, int nVMs){
		int[][] Belong = new int[nlocations][nVMs*nlocations];
		int v=0;
			for (int l = 0; l < nlocations; l++) {
				Belong[l][v]=1;
				Belong[l][v+1]=1;
				Belong[l][v+2]=1;
				Belong[l][v+3]=1;
				Belong[l][v+4]=1;
				Belong[l][v+5]=1;
				Belong[l][v+6]=1;
				v=v+nVMs;
			}
		
		System.out.println("Belong: "+Arrays.deepToString(Belong));
		//write data to file
		writeIntMatrix("CBO/datasets/Belong", Belong);
	}
	
	
	/**
	 * predefined VM data generator
	 * @param nVMs
	 */
	public void VMGen(int nVMs,int types){
		int[] Computing = new int[nVMs];
		int[] Vram = new int[nVMs];
		int[] Vbw = new int[nVMs];
		double[] Price = new double[nVMs];
		for(int v=0;v<nVMs;v=v+types){
			for(int t=0;t<types;t++){
			Computing[v+t]=VMtypes[t][0];
			Vram[v+t]=VMtypes[t][1];
			Vbw[v+t]=VMtypes[t][2];
			Price[v+t]=ThreadLocalRandom.current().nextDouble(VMprice[t][0],VMprice[t][1]);
			}
		}
		System.out.println("VMcpu:"+Arrays.toString(Computing));
		System.out.println("VMram: "+Arrays.toString(Vram));
		System.out.println("VMbw: "+Arrays.toString(Vbw));
		System.out.println("VMprice: "+Arrays.toString(Price));
		//write data to file
		writeIntVector("CBO/datasets/Computing", Computing);
		writeIntVector("CBO/datasets/Vram", Vram);
		writeIntVector("CBO/datasets/Vbw", Vbw);
		writeDoubleVector("CBO/datasets/Price", Price);
	}
	
	/**
	 * Function Task size and belong generator
	 * @param napplications
	 * @param nfunctions
	 */
	
	public void applicationGen(int napplications, int nfunctions) {
		int[][] Belong = new int[napplications][nfunctions];
		int[][] Task = new int[napplications][nfunctions];
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
		//write data to file
		//writeIntMatrix("CBO/datasets/Belong", Belong);
		writeIntMatrix("CBO/datasets/Task", Task);
	}
	
	/**
	 * function invocation frequency generator
	 * @param nusers
	 * @param nfunctions
	 */
	public void frequencyGen(int nusers,int nfunctions){
		int[][] Frequency = new int[nusers][nfunctions];
		for(int u=0;u<nusers;u++){
			for(int f=0;f<nfunctions;f++){
				Frequency[u][f]=ThreadLocalRandom.current().nextInt(0,100);
			}
		}
		System.out.println("Frequency: "+Arrays.deepToString(Frequency));
		writeIntMatrix("CBO/datasets/Frequency", Frequency);
	}
	
	
	/**
	 * resize the rtMatrix based on u and v, than generate new latency matrix
	 * @param filename
	 * @param x
	 * @param y
	 * @return
	 */
	public double[][] latencyResize(int u,int v) {
		double[][] latency = new double[u][v];
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					"CBO/datasets/rtMatrix.txt"), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			for (int i = 0; i < u; i++) {
				String line =br.readLine();
				for (int j = 0; j < v; j++) {
					String[] splited = line.split("\\s+");
					if(Double.parseDouble(splited[j])==-1.0){
						latency[i][j] = 0;
					}else{
						latency[i][j] = Double.parseDouble(splited[j]);
					}
				}
			}
			read.close();
		} catch (IOException e) {
			// why does the catch need its own curly?
		}
		System.out.println("Latency: "+Arrays.deepToString(latency));
		writeDoubleMatrix("CBO/datasets/Latency",latency);
		return latency;
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
	 * Write vector to file
	 * @param filename
	 * @param vector
	 */
	public void writeIntVector(String filename, int[] vector) {
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

	/**
	 * Write double vector to file
	 * @param filename
	 * @param vector
	 */
	public void writeDoubleVector(String filename, double[] vector) {
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
			System.err.println("Writing data error");
			// why does the catch need its own curly?
		}
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





}
