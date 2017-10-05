package org.moeaframework.algorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.moeaframework.core.NondominatedSortingPopulation;
import org.moeaframework.core.Solution;

public class Generations {
	public void writeGenerations(NondominatedSortingPopulation population, int num){
		Writer output;
		try{
		output = new BufferedWriter(new FileWriter("PSO/generations/"+num+".text", true));  //clears file every time
		for (Solution s : population) {
			output.append(s.getObjective(0) + "," + s.getObjective(1) + "\n");
			output.close();
			}
		
		} catch (IOException e) {
			// why does the catch need its own curly?
		}
	}
}
