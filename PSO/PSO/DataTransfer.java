package PSO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class DataTransfer {
	public static void main(String[] args) {
		int workloadSize = 500;
        String[][] workload=new String[10000][workloadSize];
		for(int j=1;j<=1250;j++){
        String csvFile = "/Users/kuangwentao/Documents/workspace/MOEAFramework/CBO/datasets/Workload/"+j+".csv";
        String line = "";
        String cvsSplitBy = ";";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	line = br.readLine();
        	int count = line.length() - line.replace(cvsSplitBy, "").length()+1;
        	for(int i=0;i<8;i++){
            while ((line = br.readLine()) != null&&workloadSize>0) {
            	String[] tokens= line.split(";");            	
                // use comma as separator             
                	workload[j-1+i*1250][500-workloadSize]=tokens[3];
                workloadSize--;
            }
            workloadSize=500;}
            //System.out.println(Arrays.toString(workload[j-1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
	  }
		 String filename="/Users/kuangwentao/Documents/workspace/MOEAFramework/CBO/datasets/workload.txt";
		 try {
		        BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

		        for (int i = 0; i < workload.length; i++) {
		        	for (int j = 0; j < workload[i].length; j++) {
		        	    bw.write(workload[i][j] + ((j == workload[i].length-1) ? "" : ","));
		        	}
		            bw.newLine();
		        }
		        bw.flush();
		    } catch (IOException e) {}
		
		
		//System.out.println((workload[0][1]));
    }
}
