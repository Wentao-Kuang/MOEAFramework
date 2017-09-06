package PSO;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.moeaframework.analysis.collector.Accumulator;
import org.moeaframework.analysis.collector.AttachPoint;
import org.moeaframework.analysis.collector.Collector;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.EpsilonBoxDominanceArchive;
import org.moeaframework.core.Indicator;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

public class CustomCollector implements Collector {

	private final Algorithm algorithm;

	public CustomCollector(Algorithm algorithm) {
		super();
		this.algorithm = algorithm;
	}

	@Override
	public void collect(Accumulator accumulator) {
		NondominatedPopulation result = algorithm.getResult();
		
		Writer output;
		try{
		output = new BufferedWriter(new FileWriter("PSO/generations/"+this.algorithm.getNumberOfEvaluations()/1000+".text", true));  //clears file every time
		for (Solution s : result) {
			output.append(s.getObjective(0) + "," + s.getObjective(1) + "\n");
			output.close();
			}
		
		} catch (IOException e) {
			// why does the catch need its own curly?
		}
	
		
//		OutputStreamWriter write;
//		try {
//			
//			
//			write = new OutputStreamWriter(new FileOutputStream(
//					"PSO/generations/"+this.algorithm.getNumberOfEvaluations()/1000+".text"), "UTF-8");
//			BufferedWriter bw = new BufferedWriter(write);
//			for (Solution s : result) {
//				bw.write(s.getObjective(0) + "," + s.getObjective(1) + "\n");
//				bw.flush();
//			}
//
//		} catch (IOException e) {
//			// why does the catch need its own curly?
//		}
	}

	// compute your convergence metric

	// accumulator.add("My Convergence Metric", value);

	@Override
	public AttachPoint getAttachPoint() {
		return AttachPoint.isSubclass(Algorithm.class).and(
				AttachPoint.not(AttachPoint.isNestedIn(Algorithm.class)));
	}

	@Override
	public Collector attach(Object object) {
		return new CustomCollector((Algorithm) object);
	}

}