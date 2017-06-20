package NSGAII;

import org.moeaframework.core.PRNG;
import org.moeaframework.core.Problem;
import org.moeaframework.util.Vector;
import org.moeaframework.util.io.CommentedLineReader;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.BinaryVariable;
import org.moeaframework.problem.AbstractProblem;

public class CBOproblem implements Problem {

	public CBOproblem() {
		super();
		createExampleData();
		// TODO Auto-generated constructor stub
	}

	/**
	 * number of application "a"
	 */
	private int napplications;

	/**
	 * number of functions "f"
	 */
	private int nfunctions;

	/**
	 * number of User groups "u"
	 */
	private int nusers;

	/**
	 * number of VMs "v"
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
	private int[][] Task;

	/**
	 * Entry {@code Frequency[u][f]} is the function invocation frequency matrix
	 * from including functions {@code f} in user groups {@code u}.
	 */
	private int[][] Frequency;

	/**
	 * Entry {@code latency[v][u]} is the latency matrix from including user
	 * groups {@code u} in VMs {@code v}.
	 */
	private double[][] Latency;

	/**
	 * Entry {@code Task[u][f]} is the function workload matrix from including
	 * functions {@code f} in user groups {@code u}.
	 */
	private int[][] workload;

	/**
	 * Entry {@code computing[v][f]} is the VM computing capability matrix from
	 * including functions {@code f} in VMs {@code v}.
	 */
	private int[][] computing;

	/**
	 * Entry {@code price[v][f]} is the VM price matrix from including functions
	 * {@code f} in VMs {@code v}.
	 */
	private double[][] price;

	/**
	 * Generate Example data for testing fitness and constraints
	 */
	public void createExampleData() {

		int a = 3;
		int f = 3;
		int u = 3;
		int v = 10;
		napplications = a;
		nfunctions = f;
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
		Task = new int[][] { { 4500, 0, 0 }, { 0, 0, 2500 }, { 0, 1500, 2500 } };
		Frequency = new int[][] { { 103, 25, 30 }, { 13, 5, 24 },
				{ 67, 19, 75 } };
		Latency = new double[][] { { 3.454, 5.556, 0 }, { 2.222, 0, 2.500 },
				{ 1.434, 1.135, 2.044 },{ 2.444, 2.135, 0 },{ 0, 1.55, 2.944 }
				,{ 0.434, 5.135, 0.044 },{ 6.22, 2.33, 4.044 },{ 0, 1.15, 2 }
				,{ 1.4, 4.135, 2.144 },{ 0, 2.135, 0.044 }};

		// to be deleted
		workload = new int[][] { { 4500, 3000, 20000 }, { 1000, 50000, 2500 },
				{ 34000, 1500, 2000 } };
		computing = new int[][] { { 3000, 1500, 1500 }, { 2000, 1000, 1000 },
				{ 5000, 2500, 2500 } };
		price = new double[][] { { 0.138, 0.150, 0.148 },
				{ 0.080, 0.090, 0.080 }, { 0.210, 0.200, 0.200 } };

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
			for (int f = 0; f < nfunctions; f++) {// functions loop
				for (int v = 0; v < nVMs; v++) {// VMs loop
					if (s[(a) * nVMs + v] == false||Belong[a][f]==0)
						continue;
					else {
						for (int u = 0; u < nusers; u++) {
							double e = 0;// execution time
							e = (double) Frequency[u][f]*Task[a][f]
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
			for (int f = 0; f < nfunctions; f++) {// functions loop
				for (int v = 0; v < nVMs; v++) {// VMs loop
					if (s[(a) * nVMs + v] == false||Belong[a][f]==0)
						continue;
					else {
						for (int u = 0; u < nusers; u++) {
							double e = 0;// execution time
							double l = 0;// latency
							e = (double) Frequency[u][f]*Task[a][f]
									/ (double) Computing[v];
							l=Latency[v][u]*Frequency[u][f];
							response += l+e;
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
		}
		return response;
	}

	/**
	 * constraint 1, application deployment constraint: each function must be allocate once
	 */
	public boolean constraint1(boolean[] s) {
		boolean b = true;
		for (int a = 0; a < nfunctions; a++) {
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

	@Override
	public void evaluate(Solution solution) {
		boolean[] s = EncodingUtils.getBinary(solution.getVariable(0));
		double cost = caculateCost(s);
		double response = caculateResponse(s);
		boolean c1 = constraint1(s);
		solution.setConstraint(0, c1 ? 0.0 : 1.1);
		solution.setObjective(0, cost);
		solution.setObjective(1, response);
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 2, 1);
		solution.setVariable(0, EncodingUtils.newBinary(nfunctions * nVMs));
		return solution;
	}

	/*
	 * test
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
		System.out.println(c.constraint1(s));
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumberOfConstraints() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfObjectives() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfVariables() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Solution generate() {
		Solution solution = newSolution();

		for (int i = 0; i < nfunctions * nVMs; i++) {
			((BinaryVariable) solution.getVariable(0)).set(i,
					PRNG.nextBoolean());
		}
		evaluate(solution);
		return solution;
	}

}
