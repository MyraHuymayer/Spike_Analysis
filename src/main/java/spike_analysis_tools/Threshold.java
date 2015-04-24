//NOTE: MUSS NOCH GETESTET WERDEN
package spike_analysis_tools;

import java.util.ArrayList;

/**
 * Calculation of the threshold and initiation time of the action potential by using either the second or third derivative method
 * @author myra
 */
public class Threshold {
    
    private double threshold;
    private double initiation;
    
    /*--------------------------------------------------------------------------------------*/
    
    /**
     * 5% of the first peak amplitude in the second derivative denotes the action potential threshold
     * @param central_difference central difference approximating the second derivative of the voltage
     * @param voltage_trace this is the voltage trace from which the central difference was calculated - may not be mixed up!
     * @param time_trace this is the concomitant time trace to the the voltage trace
     * @param delta required to find the maximal and minimal values in a set of data points
     * @return the threshold calculated with the second derivative method
     */
    public double secondDerivativeMethod(ArrayList<Double> central_difference, ArrayList<Double> voltage_trace, ArrayList<Double> time_trace, double delta){
        int thresh_ind =0;
        
        PeakDetection pd = new PeakDetection();
        
        pd.findMaximum(central_difference, delta);
        
        ValueStorage max = pd.getMaxtab();
     
        //choose the first central difference value of the stored data points 
        double central_diff_max = max.getSingleValue(0) * 0.05;
        
        //find nearest neighbors of central_diff_max in the voltage_trace
        for(int i = 1; i < central_difference.size(); i++){
            
            double neighbor1 = central_difference.get(i-1);
            double neighbor2 = central_difference.get(i);
           
            
            if(central_diff_max <= neighbor1 && central_diff_max <= neighbor2){
                
                //calculate the difference between neighbor1 and the value of the mean_spike_height
                double diff1 = Math.abs(central_diff_max - neighbor1);
                //calculate the difference between neighbor2 and the value of the mean_spike_height
                double diff2 = Math.abs(central_diff_max - neighbor2);
                
                //choose the shortest distance from mean_spike_height
                if(diff1 < diff2){
                    //set the index for t1
                    thresh_ind = i-1;
                    break;
                }else{
                    //set the index for t1
                    thresh_ind = i;
                    break;
                }
            }

        }
        //since the central_difference array lacks 2 elements of the voltage array (the first and the last)
        //we have to add 1 to the index 
        threshold = voltage_trace.get(thresh_ind+1);
        initiation = time_trace.get(thresh_ind+1);

        return threshold;
    }
    
    /**
     * Third derivative method: the time of action potential initiation/voltage threshold is determined from the first peak in the third derivative.
     * @param central_difference central difference approximating the third derivative of the voltage
     * @param voltage_trace this is the voltage trace from which the central difference was calculated - may not be mixed up!
     * @param time_trace this is the concomitant time trace
     * @param delta required to find the maximal and minimal values in a set of data points
     * @return the threshold calculated with the third derivative method
     */
    public double thirdDerivativeMethod(ArrayList<Double> central_difference, ArrayList<Double> voltage_trace, ArrayList<Double> time_trace, double delta){
        
        //determine the peaks of the third derivative using the PeakDetection algorithm 
        PeakDetection pd = new PeakDetection();
        
        pd.findMaximum(central_difference, delta);
        
        ValueStorage max = pd.getMaxtab();
             /* PeakDetection peakdetection = new PeakDetection();
         peakdetection.findMaximum(thirdderiv, 4.5);

         
         ValueStorage max = peakdetection.getMaxtab();
         ValueStorage min = peakdetection.getMintab();
         System.out.println("maximum size: "+max.getIndex().size());
         System.out.println("minimum size: "+min.getIndex().size());
         
         for(int i = 0; i<max.getValue().size(); i++){
            
             System.out.println("Index of Maximum: "+max.getSingleIndex(i));
             System.out.println("Central Difference Value of Maximum: "+max.getSingleValue(i));
         }
          System.out.println("============================================================");
        
          for(int i = 0; i<min.getValue().size(); i++){
            
             System.out.println("Index of Minimum: "+min.getSingleIndex(i));
             System.out.println("Central Difference Value of Maximum: "+min.getSingleValue(i));
         }*/
    
        //choose the first value of the stored data points 
        int index = max.getSingleIndex(0);
//        System.out.println("value of maximum: "+max.getSingleValue(0));
        //double central_diff_max = max.getSingleValue(0);
        
        //since the central_difference array lacks 4 elements of the voltage array (the two first and the two last)
        //we have to add 2 to the index 
        threshold = voltage_trace.get(index+2);

       
        //calculate the initiation time of the action potential (might be needed some time)
        initiation = time_trace.get(index+2);
        
        return threshold;
    }

    public double getInitiation() {
        return initiation;
    }
    
    public void resetTreshold(){
        threshold = 0;
    }
   
    
    
}
