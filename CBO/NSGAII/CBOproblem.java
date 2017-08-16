package NSGAII;

import org.moeaframework.core.PRNG;
import org.moeaframework.core.Problem;
import org.moeaframework.util.Vector;
import org.moeaframework.util.io.CommentedLineReader;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.BinaryVariable;
import org.moeaframework.problem.AbstractProblem;
import java.util.Arrays;

public class CBOproblem implements Problem {

	public CBOproblem() {
		super();
		// problem size define same with data generator


		int a=40;
		int u=150;
		int l=150;
		int v=7;
		LoadData load=new LoadData(a,u,l,v);
		this.napplications=a;
		this.nusers=u;
		this.nlocations=l;
		this.nVMs=v;
		this.Belong=load.readBelong();
		this.Task=load.readTask();
		this.Frequency=load.readFrequency();
		this.Latency=load.readLatency();
		this.Computing=load.readComputing();
		this.Vram=load.readVram();
		this.Vbw=load.readVbw();
		this.Price=load.readPrice();
		this.Acpu=load.readAcpu();
		this.Aram=load.readAram();
		this.Abw=load.readAbw();
		//createExampleData();
		// TODO Auto-generated constructor stub
	}

	/**
	 * number of application "a"
	 */
	private int napplications;


	/**
	 * number of User groups "u"
	 */
	private int nusers;

	/**
	 * number of location
	 */
	private int nlocations;

	/**
	 * number of VMs "v" in each location
	 */
	private int nVMs;

	/**
	 * Entry {@code computing[v]} is the VM computing capability vector.
	 */
	private int[] Computing;

	/**
	 * Entry {@code price[v]} is the VM price vector.
	 */
	private double[] Price;

	/**
	 * Entry {@code Vram[v]} is the VM memory vector.
	 */
	private int[] Vram;
	/**
	 * Entry {@code Vbw[v]} is the VM bandwidth vector.
	 */
	private int[] Vbw;
	/**
	 * Entry {@code Acpu[v]} is the application CPU requirement vector.
	 */
	private int[] Acpu;
	/**
	 * Entry {@code Aram[v]} is the application memory requirement vector.
	 */
	private int[] Aram;
	/**
	 * Entry {@code Abw[v]} is the application bandwidth requirement vector.
	 */
	private int[] Abw;

	/**
	 * Entry {@code Belong[a][f]} is the functions affiliation matrix B from
	 * including functions {@code f} in application {@code a}.
	 */
	private int[][] Belong;

	/**
	 * Entry {@code Task[u][f]} is the function task size matrix from including
	 * functions {@code f} in application {@code a}.
	 */
	private double[][] Task;

	/**
	 * Entry {@code Frequency[u][f]} is the function invocation frequency matrix
	 * from including functions {@code f} in user groups {@code u}.
	 */
	private int[][] Frequency;

	/**
	 * Entry {@code latency[u][v]} is the latency matrix from including user
	 * groups {@code u} in VMs {@code v}.
	 */
	private double[][] Latency;


	/**
	 * Generate Example data for testing fitness and constraints
	 */
	public void createExampleData() {

		int a = 3;
		int f = 3;
		int u = 3;
		int v = 10;
		napplications = a;
		nusers = u;
		nVMs = v;

		/**
		 * //generator dataset ExampleDataGenerator g=new
		 * ExampleDataGenerator(); workload = g.readIntMatrix("CBO/workload", u,
		 * f); computing = g.readIntMatrix("CBO/computing", v, f); price =
		 * g.readDoubleMatrix("CBO/price", v, f); Latency =
		 * g.readDoubleMatrix("CBO/latency", v, u);
		 */

		// VMs
		Computing = new int[] { 3500, 3000, 2500, 2500, 2000, 1500, 1000, 1000,
				500, 500 };
		Vram = new int[] { 16, 8, 4, 4, 8, 4, 2, 2, 1, 1 };
		Vbw = new int[] { 1000, 1000, 1000, 1000, 1250, 1000, 750, 750, 500,
				500 };
		Price = new double[] { 0.4, 0.3, 0.1, 0.14, 0.13, 0.055, 0.03, 0.04,
				0.011, 0.020 };
		// Applications
		Acpu = new int[] { 2000, 1000, 1500 };
		Aram = new int[] { 4, 1, 2 };
		Abw = new int[] { 600, 500, 1000 };
		// Input matrix
		Belong = new int[][] { { 1, 0, 0 }, { 0, 0, 1 }, { 0, 1, 1 } };
		//Task = new int[][] { { 4500, 0, 0 }, { 0, 0, 2500 }, { 0, 1500, 2500 } };
		Frequency = new int[][] { { 103, 25, 30 }, { 13, 5, 24 },
				{ 67, 19, 75 } };
		Latency = new double[][] { { 3.454, 5.556, 0 , 2.222, 0, 2.500, 1.434, 1.135, 2.044, 2.444}, {2.135, 0, 0, 1.55, 2.944 , 0.434, 5.135, 0.044 , 6.22, 2.33}, {4.044 , 0, 1.15, 2, 1.4, 4.135, 2.144, 0, 2.135, 0.044 }};
	}

	/**
	 * calculate the cost fitness
	 *
	 * @param a
	 * @return
	 */
	public double caculateCost(boolean[] s) {
		double cost = 0.0;
		for (int a = 0; a < napplications; a++) {//application loop
				for (int v = 0; v < nVMs*nlocations; v++) {// VMs loop
					if (s[a * nVMs*nlocations + v] == false)
						continue;
					else {
						for (int u = 0; u < nusers; u++) {
							double e = 0;// execution time
							e = Task[a][u]
									/ (double) Computing[v];
							cost += e * Price[v];
							/**
							 System.out.println("a: "+a+" f: "+f+" u: "+u+" v: "+v);
							 System.out.println("Belong:"+ Belong[a][f]);
							 System.out.println("Frequency:" + Frequency[u][f]);
							 System.out.println("Task:" + Task[a][f]);
							 System.out.println("Computing:" + Computing[v]);
							 System.out.println("Execution:" +Computing[v]);
							 System.out.println("Price:" + Price[v]);
							 System.out.println(cost);
							 */
						}
					}
				}
		}
		return cost / 3600;
	}

	/**
	 * calculate the response time fitness
	 *
	 * @param a
	 * @return
	 */
	public double caculateResponse(boolean[] s) {
		double response = 0.0;
		for (int a = 0; a < napplications; a++) {//application loop
				for (int v = 0; v < nVMs*nlocations; v++) {// VMs loop
					if (s[(a) * nVMs*nlocations + v] == false)
						continue;
					else {
						for (int u = 0; u < nusers; u++) {
							double e = 0;// execution time
							double la = 0;// latency
							e = (double) Task[a][u]
									/ (double) Computing[v];
							for(int l=0;l<nlocations;l++){
							la=Latency[u][l]*Frequency[a][u];
							}
							response += la+e;
							/**
							 System.out.println("a: "+a+" f: "+f+" u: "+u+" v: "+v);
							 System.out.println("Belong:"+ Belong[a][f]);
							 System.out.println("Frequency:" + Frequency[u][f]);
							 System.out.println("Task:" + Task[a][f]);
							 System.out.println("Computing:" + Computing[v]);
							 System.out.println("Latency:" + Latency[v][u]);
							 System.out.println("Price:" + Price[v]);
							 System.out.println("Execution:"+e);
							 System.out.println("TotalLatency:"+l);
							 System.out.println("Response:"+response);
							 */
						}
				}
			}
		}
		return response/3600;
	}

	/**
	 * constraint 1, application deployment constraint: each function must be allocate once
	 */
	public boolean constraint1(boolean[] s) {
		boolean b = true;
		for (int a = 0; a < napplications; a++) {
			int sum = 0;
			for (int v = 0; v < nVMs; v++) {
				sum += s[(a) * nVMs + v] ? 1 : 0;
			}
			if (sum < 1)
				b = false;
			//System.out.println("a:"+a);
			//System.out.println("number of deployment:"+sum);
		}

		return b;
	}


	/**
	 * constraint 2, VM constraint: each VM can be deployed at most once.
	 */
	public boolean constraint2(boolean[] s){
		boolean b =true;
		for(int v = 0; v<nVMs;v++){
			int sum = 0;
			for(int a = 0;a<napplications; a++){
				sum += s[a*nVMs+v]?1:0;
			}
			if(sum > 1) b =false;
		}

		return b;
	}
	/**
	 * constraint 3, deployment requirement constraint.
	 */
	public boolean constraint3(boolean[] s){
		boolean b = true;
		for(int a = 0;a<napplications;a++){
			for(int v = 0;v<nVMs;v++){
				if(s[a*nVMs+v]){
					if((Acpu[a]<=Computing[v])&&(Aram[a]<=Vram[v])&&(Abw[a]<=Vbw[v])){
						continue;
					}else{
						b=false;
						break;
					}
				}
			}
		}
		return b;
	}


	@Override
	public void evaluate(Solution solution) {
		boolean[] s = EncodingUtils.getBinary(solution.getVariable(0));
		double cost = caculateCost(s);
		double response = caculateResponse(s);
		boolean c1 = constraint1(s);
		boolean c2 = constraint2(s);
		boolean c3 = constraint3(s);
		solution.setConstraint(0, c1 ? 0.0 : 1.1);
		solution.setConstraint(1, c2?0.0:1.1);
		solution.setConstraint(2, c3?0.0:1.1);
		solution.setObjective(0, cost);
		solution.setObjective(1, response);
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 2, 3);
		solution.setVariable(0, EncodingUtils.newBinary(napplications * nVMs*nlocations));
		return solution;
	}

	/*
	 * Unit test
	 */
	public static void main(String[] args) {
		CBOproblem c = new CBOproblem();
		c.createExampleData();
		boolean[] s = new boolean[] { true, true, false, false, false, false,
				false, false, false,false, false, false, false, false, false, false,
				false, false, false,false,false, false, false, false, false, false,
				false, false, false,false };

		//testing the Cost fitness function
		//System.out.println(c.caculateCost(s));

		//testing the response time fitness function
		//System.out.println(c.caculateResponse(s));

		//testing the application deployment constraint
		//System.out.println(c.constraint1(s));

		//testing the VM constraint
		//System.out.println(c.constraint2(s));

		//testing application deployment requirement constraint
		//System.out.println(c.constraint3(s));

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "CBO";
	}

	@Override
	public int getNumberOfConstraints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfObjectives() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getNumberOfVariables() {
		// TODO Auto-generated method stub
		return 1;
	}

//	public Solution generate() {
//		Solution solution = newSolution();
//
//		for (int i = 0; i < nfunctions * nVMs; i++) {
//			((BinaryVariable) solution.getVariable(0)).set(i,
//					PRNG.nextBoolean());
//		}
//		evaluate(solution);
//		return solution;
//	}

}
