package PSO;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import NSGAII.CBOproblem;

public class WriteRefferenceSet {

	public static void main(String[] args) {
		try {
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream("PSO/result.text"), "UTF-8");
			BufferedWriter bw = new BufferedWriter(write);
			int iterations = 50;
			for (int i = 0; i < iterations; i++) {
				NondominatedPopulation result = new Executor()
						.withAlgorithm("NSGAII")
						.withProblemClass(NSGAIIproblem.class)
						.withProperty("populationSize", 50)
						.withProperty("withReplacement", true)
						.withProperty("sbx.rate", 0.9)
						.withProperty("sbx.distributionIndex", 15.0)
						.withProperty("pm.rate", 0.5)
						.withProperty("pm.distributionIndex", 20.0)
						.withMaxEvaluations(5000)
						.distributeOnAllCores()
						.run();

				for (Solution s : result) {
					// System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");
					bw.write(s.getObjective(0) + "," + s.getObjective(1) + "\n");
				}
			}
			bw.flush();
		} catch (IOException e) {
			// why does the catch need its own curly?
		}

	}

}
