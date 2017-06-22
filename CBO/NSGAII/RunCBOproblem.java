package NSGAII;
import java.util.ArrayList;

import CBO.NSGA.CBOproblem1;

import org.moeaframework.core.Problem;
import org.moeaframework.util.Vector;
import org.moeaframework.util.io.CommentedLineReader;
import org.moeaframework.Executor;
import org.moeaframework.Instrumenter;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.NondominatedPopulation; 
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.analysis.plot.Plot;

public class RunCBOproblem {
	public static void main(String[] args) {
			

			
			NondominatedPopulation result = new Executor()
			.withAlgorithm("NSGAII")
			.withProblemClass(CBOproblem.class)
			.withProperty("populationSize", 100)
			.distributeOnAllCores()
			.withMaxEvaluations(50000)
			.run();		
			
//			NondominatedPopulation result1 = new Executor()
//			.withAlgorithm("random")
//			.withProblemClass(CBOproblem.class)
//			.withProperty("populationSize", 100)
//			.distributeOnAllCores()
//			.withMaxEvaluations(50000)
//			.run();
//			
//			NondominatedPopulation result2 = new Executor()
//			.withAlgorithm("GA")
//			.withProblemClass(CBOproblem.class)
//			.withProperty("populationSize", 100)
//			.distributeOnAllCores()
//			.withMaxEvaluations(50000)
//			.run();
			
			double[] objective1=new double[result.size()];
			double[] objective2=new double[result.size()];
//			double[] objective1_1=new double[result1.size()];
//			double[] objective1_2=new double[result1.size()];
//			double[] objective2_1=new double[result2.size()];
//			double[] objective2_2=new double[result2.size()];
			
			for (Solution s : result){
				//System.out.println(s.getVariable(0));
				objective1[result.indexOf(s)]=s.getObjective(0);
				objective2[result.indexOf(s)]=s.getObjective(1);
				//System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");
			}
			
//			for (Solution s : result1){
//				objective1_1[result1.indexOf(s)]=s.getObjective(0);
//				objective1_2[result1.indexOf(s)]=s.getObjective(1);
//			}
//			
//			for (Solution s : result2){
//				objective2_1[result2.indexOf(s)]=s.getObjective(0);
//				objective2_2[result2.indexOf(s)]=s.getObjective(1);
//			}
			

			Plot p =new Plot();
			//p.add("Radom",result1);
			//p.add("NSGAII", result);
			  p.scatter("NSGAII", objective1, objective2);
//			  p.scatter("random", objective1_1, objective1_2);
//			  p.scatter("GA", objective2_1, objective2_2);
			  p.setXLabel("Cost");
			  p.setYLabel("Response time");
			//p.add("NSGAII", result,0,1);
			//p.add("GA",result2);
			//p.add("random",result3);
		
			p.show();
	}
	
	
}
	