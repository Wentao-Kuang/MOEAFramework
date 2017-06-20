package NSGAII;

import org.moeaframework.core.Population;
import org.moeaframework.problem.DTLZ.DTLZ2;

public class Test {

	
	public static void main(String[] args) {
		CBOproblem problem = new CBOproblem();
		Population trueParetoFront = new Population();

		for (int i = 0;  i< 1000; i++) {
		    trueParetoFront.add(problem.generate());
		}
	}
}

