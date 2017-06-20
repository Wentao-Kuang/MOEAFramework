package CBO.NSGA;

import org.moeaframework.core.Population;
import org.moeaframework.problem.DTLZ.DTLZ2;

public class Test1 {

	
	public static void main(String[] args) {
		CBOproblem1 problem = new CBOproblem1();
		Population trueParetoFront = new Population();

		for (int i = 0;  i< 1000; i++) {
		    trueParetoFront.add(problem.generate());
		}
	}
}

