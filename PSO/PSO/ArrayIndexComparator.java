package PSO;

import java.util.Comparator;

public class ArrayIndexComparator implements Comparator<Integer>
{
    private double[] grades;
    public ArrayIndexComparator(double[] arr) {
        grades = arr;
    }
    public int compare(Integer i, Integer j) {
        return Double.compare(grades[j], grades[i]);
    }
}