//TODO: DAS MUSS AUF JEDEN FALL NOCH GETESTET WERDEN
package spike_analysis_tools;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author myra
 */
public class CentralDifference {
    
    private final ArrayList<Double> central_difference = new ArrayList<Double>();
    private final ArrayList<Double> time = new ArrayList<Double>();
    
    /*-------------------------------------------------------------------------------------------------------------*/
    /**
     * Approximation of the second derivative of the voltage data
     * @param voltage voltage read from file (NOTE: the document read from needs a fixed step size for time) 
     * @param time time that fits to the central difference array (for plotting sakes)
     * @param stepsize fixed step size in ms (NOTE: it might be useful to implement a method with a variable step size)  
     * @return the array with the central difference values
     */
    public ArrayList<Double> second_derivative(ArrayList<Double> voltage, ArrayList<Double> time, double stepsize)throws IOException{

        //	-- the number of elements should match those of the voltage and time tables --       
        // hence we set the first and last element of this list = 0 (das sind dann quasi boundary conditions)
        //
        for(int i = 1; i < voltage.size()-1; i++){
            
            double step = time.get(i) - time.get(i-1);
            if(step > stepsize+0.01 || step < stepsize-0.01  ){
              
                throw new IOException("Error: step size differs significantly from given stepsize! ");
            }
            
            double numerator = voltage.get(i-1) - 2*voltage.get(i) + voltage.get(i+1);
            double denominator = Math.pow(stepsize, 2);
            
            double tmp = numerator/denominator;
            //NOTE that the central difference lacks two elements that are present in the voltage trace
            central_difference.add(tmp);
            this.time.add(time.get(i));
        }
        return central_difference;
    }
    
    /**
     * Approximation of the third derivative of the voltage data
     * @param voltage voltage read from file (NOTE: the document read from needs a fixed step size for time)
     * @param time time that fits to the central difference array (for plotting sakes)
     * @param stepsize fixed step size is used (NOTE: it might be useful to implement a method with a variable step size)  
     * @return the array with the central difference values for the third derivative
     */
    public ArrayList<Double> third_derivative(ArrayList<Double> voltage, ArrayList<Double> time, double stepsize) throws IOException{
        
        for(int i = 2; i<voltage.size()-2; i++){
            
            double step = time.get(i) - time.get(i-1);
            
 

           if(step > stepsize+0.01 || step < stepsize-0.01  ){
              
                throw new IOException("Error: step size differs significantly from given stepsize! ");
            }


            double numerator = -voltage.get(i-2) + 2*voltage.get(i-1) - 2* voltage.get(i+1) + voltage.get(i+2);
            double denominator = 2* Math.pow(stepsize , 3);
            
            double tmp = numerator/denominator;
            //time is adjusted to the central difference trace
            this.time.add(time.get(i));
            central_difference.add(tmp);
        }
            
        return central_difference;
    }
    

    public ArrayList<Double> getTime() {
        return time;
    }

}
