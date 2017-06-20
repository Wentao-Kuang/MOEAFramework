package CBO.NSGA;
import java.util.ArrayList;

import CBO.NSGA.CBOproblem1;

import org.moeaframework.core.Problem;
import org.moeaframework.util.Vector;
import org.moeaframework.util.io.CommentedLineReader;
import org.moeaframework.Executor;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.NondominatedPopulation; 
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.analysis.plot.Plot;

public class RunCBOproblem1 {
	public static void main(String[] args) {
		Plot p =new Plot();
		
			NondominatedPopulation result = new Executor()
			.withAlgorithm("NSGAII")
			.withProblemClass(CBOproblem1.class)
			.withProperty("populationSize", 100)
			.distributeOnAllCores()
			.withMaxEvaluations(10000)
			.run();
			
			
			NondominatedPopulation result1 = new Executor()
			.withAlgorithm("random")
			.withProblemClass(CBOproblem1.class)
			.withProperty("populationSize", 100)
			.distributeOnAllCores()
			.withMaxEvaluations(10000)
			.run();
			
			NondominatedPopulation result2 = new Executor()
			.withAlgorithm("GA")
			.withProblemClass(CBOproblem1.class)
			.withProperty("populationSize", 100)
			.distributeOnAllCores()
			.withMaxEvaluations(10000)
			.run();		

			
			for (Solution s : result){
				System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");
			}
			p.add("Radom",result1);
			p.add("NSGAII", result);
			//p.add("NSGAII", result,0,1);
			p.add("GA",result2);
			//p.add("random",result3);
		
	 p.show();
	}
}
