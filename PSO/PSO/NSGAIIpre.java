package PSO;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;

public class NSGAIIpre {
	public NondominatedPopulation preNSGAII(){
		NondominatedPopulation result = new Executor()
		.withAlgorithm("NSGAII")
		.withProblemClass(NSGAIIproblem.class)
		.withProperty("populationSize", 100)
		.withProperty("withReplacement", true)
		.withProperty("sbx.rate", 0.95)
		.withProperty("sbx.distributionIndex", 15.0)
		.withProperty("pm.rate", 0.05)
		.withProperty("pm.distributionIndex", 20.0)
		.distributeOnAllCores()
		.withMaxEvaluations(10000)
		.run();
		return result;
	}
}
