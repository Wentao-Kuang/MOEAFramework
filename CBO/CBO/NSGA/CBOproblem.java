package CBO.NSGA;


import org.moeaframework.core.PRNG;
import org.moeaframework.core.Problem;
import org.moeaframework.util.Vector;
import org.moeaframework.util.io.CommentedLineReader;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils; 
import org.moeaframework.core.variable.BinaryVariable;
import org.moeaframework.problem.AbstractProblem;

public class CBOproblem implements Problem{

	
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
	 * number of User groups  "u"
	 */
	private int nusers;
	
	/**
	 * number of VMs  "v"
	 */
	private int nVMs;
	
	
	/**
	 * Entry {@code workload[u][f]} is the function workload matrix from including functions {@code f}
	 * in user groups {@code u}.
	 */
	private int[][] workload;
	
	/**
	 * Entry {@code computing[v][f]} is the VM computing capability matrix from including functions {@code f}
	 * in VMs {@code v}.
	 */
	private int[][] computing;
	
	
	/**
	 * Entry {@code price[v][f]} is the VM price matrix from including functions {@code f}
	 * in VMs {@code v}.
	 */
	private double[][] price;
	
	/**
	 * Entry {@code latency[v][u]} is the latency matrix from including user groups {@code u}
	 * in VMs {@code v}.
	 */
	private double[][] latency;
	
	
	public void createExampleData(){
		int f=10;
		int u=30;
		int v=100;
		nfunctions=f;
		nusers=u;
		nVMs=v;

		//generator dataset
		ExampleDataGenerator g=new ExampleDataGenerator();
		workload = g.readIntMatrix("CBO/workload", u, f);
		computing = g.readIntMatrix("CBO/computing", v, f);
		price = g.readDoubleMatrix("CBO/price", v, f);
		latency = g.readDoubleMatrix("CBO/latency", v, u);		
		
		/**
		 * report example dataset
		 *
		workload = new int[][]{{4500, 3000, 20000}, {1000, 50000, 2500}, {34000, 1500, 2000}};
		computing = new int[][]{{3000, 1500, 1500}, {2000, 1000, 1000}, {5000, 2500, 2500}};
		price = new double[][]{{0.138, 0.150, 0.148}, {0.080, 0.090, 0.080}, {0.210, 0.200, 0.200}};
		latency = new double[][]{{3.454,5.556,0},{2.222,0,2.500},{1.434,1.135,2.044}};
		*/
	}
	
	/**
	 * calculate the cost fitness
	 * @param a
	 * @return
	 */
	public double caculateCost(boolean[] a){
		double cost = 0.0;
		for(int f = 0; f < nfunctions; f++){
			//functions loop
			for(int v = 0; v < nVMs; v++){
				//VMs loop				
				if(a[(f)*nVMs+v]==false) continue;
				else{
					for(int u = 0;u < nusers;u++){
						double e=0;//execution time
						e=(double)workload[u][f]/(double)computing[v][f];
						cost+=e/price[v][f];
						/**
						 * 	
						System.out.println("f:"+f+"u:"+v+"v");					
						System.out.println(workload[u][f]);
						System.out.println(price[v][f]);
						System.out.println(e);
						 */
					}
				}
			}
		}
		//System.out.println(cost/3600);
		return cost/3600;
	}
	
	/**
	 * calculate the response time fitness
	 * @param a
	 * @return
	 */
	public double caculateResponse(boolean[] a){
		double response = 0.0;
		for(int f = 0; f < nfunctions; f++){
			//functions loop
			for(int v = 0; v < nVMs; v++){
				//VMs loop				
				if(a[(f)*nVMs+v]==false) continue;
				else{
					for(int u = 0;u < nusers;u++){
						double e=0;//execution time
						e=(double)workload[u][f]/(double)computing[v][f];
						double l=latency[v][u];
						response+=e+l;
						/**
						System.out.println("f:"+f+"u:"+v+"v");					
						System.out.println(workload[u][f]);
						System.out.println(price[v][f]);
						*/
					}
				}
			}
		}
		return response;
	}
	
	/**
	 * calculate the latency fitness
	 * @param a
	 * @return
	 */
	public double caculateLatency(boolean[] a){
		double l = 0.0;
		for(int f = 0; f < nfunctions; f++){
			//functions loop
			for(int v = 0; v < nVMs; v++){
				//VMs loop				
				if(a[(f)*nVMs+v]==false) continue;
				else{
					for(int u = 0;u < nusers;u++){
						l+=latency[v][u];
					}
				}
			}
		}
		return l;
	}

	
	
	/**
	 * constraint 1, each function must be allocate once
	 */
	public boolean constraint1(boolean[] a){
		boolean b = true;
		for(int f=0;f<nfunctions;f++){
			int sum=0;
			for(int v=0;v<nVMs;v++){
				sum+=a[(f)*nVMs+v]? 1 : 0;
			}
			if(sum<1) b =false;
		}
		//System.out.println(sum);
		return b;
	}
	
	@Override
	public void evaluate(Solution solution) {
		boolean[] a = EncodingUtils.getBinary(solution.getVariable(0));
		double cost = caculateCost(a);
		double response = caculateResponse(a);
		//double la = caculateLatency(a);
		boolean c1 = constraint1(a);
		solution.setConstraint(0, c1?0.0:1.1);
	    solution.setObjective(0, cost);
	    solution.setObjective(1, response);
	    //solution.setObjective(2, la);
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(1, 2, 1);
		solution.setVariable(0, EncodingUtils.newBinary(nfunctions*nVMs)); 
		return solution;
	}

	
	/*
	 * test
	 */
	public static void main(String[] args) {
		CBOproblem c = new CBOproblem();
		c.createExampleData();
		boolean[] a= new boolean[]{false,true,false,true,false,false,false,false,false};
		c.constraint1(a);
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

		for (int i = 0; i < nfunctions*nVMs; i++) {
			((BinaryVariable)solution.getVariable(0)).set(i,PRNG.nextBoolean());
		}
		evaluate(solution);
		return solution;
	}

	
}
