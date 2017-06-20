package CBO.NSGA;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

public class RunWriteCBO {

public static void main(String[] args) {
	try {
    	OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream("CBO/result.text"),"UTF-8");
        BufferedWriter bw = new BufferedWriter(write);
        int iterations=50;
        for(int i=0;i<iterations;i++){
        NondominatedPopulation result = new Executor()
		.withAlgorithm("NSGAII")
		.withProblemClass(CBOproblem.class)
		.withProperty("populationSize", 100)
		.distributeOnAllCores()
		.withMaxEvaluations(10000)
		.run();
		
		for (Solution s : result){
			//System.out.println(s.getObjective(0)+","+s.getObjective(1)+"\n");
			bw.write(s.getObjective(0)+","+s.getObjective(1)+"\n");
		}
        }
        bw.flush();
    } catch (IOException e) {
        //why does the catch need its own curly?
    }

}
	
}
