
package spike_analysis_tools;

import java.util.ArrayList;


/**
 * The aim of this class is to store data values and their corresponding index in the data array
 * @author myra
 */
public class ValueStorage {
    
      
    private final ArrayList<Double> value = new ArrayList<Double>();
    private final ArrayList<Integer> index = new ArrayList<Integer>();
    
    /*-------------------------------------------------------------------------------------------*/

    public void store_Value_and_Index(double val, int ind){
        value.add(val);
        index.add(ind);
    }

    public ArrayList<Double> getValue() {
        return value;
    }

    public ArrayList<Integer> getIndex() {
        return index;
    }
   
    /**
     * returns one element of the value array
     * @param i is the wanted index of the value-array
     * @return the required element of the value array
     */
    public double getSingleValue(int i) {
        double val = value.get(i);
        return val;
    }
    
    /**
     * returns one element of the index array
     * @param i is the wanted index of the index-array
     * @return the required element of the index array
     */
    public int getSingleIndex(int i){
        int ind = index.get(i);
        return ind;
    }
    
}
