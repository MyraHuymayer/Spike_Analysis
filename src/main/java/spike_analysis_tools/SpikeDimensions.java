//TODO: DAS MUSS AUF JEDEN FALL NOCH GETESTET WERDEN
package spike_analysis_tools;

import java.util.ArrayList;

/**
 *
 * @author myra
 */
public class SpikeDimensions {

    
    private double apWidth;
    private double threshold; //either this is set by the user or calculated by using the second- or third-order derivative method
    private double spike_height; //eigene Klasse notwendig oder sollte das hier noch berechnet werden?
    private double minimum;
    private final Amplitude amplitude = new Amplitude();
   
    /*---------------------------------------------------------------------------------------------------*/
    
    
    public double getThreshold(){
	return threshold; 
    }
    
//     das wird spaeter gemacht
    public double calc_APwidth_THRESH(double threshold, ArrayList<Double> voltage_trace, ArrayList<Double> time_trace ){
        
        int t2_index = 0;
        
        //the onset of the time array is t1
         double t1 = time_trace.get(0);
         double t2 = 0;
         
         amplitude.calculate_amplitude(voltage_trace, time_trace, threshold);
//        double amp = amplitude.getAmplitude();
        int index = amplitude.getIndex();
        
        //find nearest neighbors of t2 
         for(int i = index; i < voltage_trace.size(); i++){
            
            double neighbor1 = voltage_trace.get(i-1);
            double neighbor2 = voltage_trace.get(i);
            
            if(threshold <= neighbor1 && threshold >= neighbor2){
                
                //calculate the difference between neighbor1 and the value of the mean_spike_height
                double diff1 = Math.abs(threshold - neighbor1);
                //calculate the difference between neighbor2 and the value of the mean_spike_height
                double diff2 = Math.abs(threshold - neighbor2);
                
                //choose the shortest distance from mean_spike_height
                if(diff1 < diff2){
                    //set the index for t1
                    t2_index = i-1;
                }else{
                    //set the index for t1
                    t2_index = i;
                }
                         
                t2 = time_trace.get(t2_index);
//                System.out.println("t2_index was hopefully changed in if-statement: "+t2_index);
//                System.out.println("threshold = "+threshold+"; neighbor1 = "+neighbor1);
//                System.out.println("threshold = "+threshold+"; neighbor2 = "+neighbor2);
//                System.out.println("t2 = "+t2);
            }

        }
        
         apWidth = t2 - t1;

         return apWidth;
    }

    public double getSpike_height() {
        return spike_height;
    }

    /**
     * calculates the spike height by subtracting the maximal voltage by the minimal voltage
     * @param voltage_trace voltage array of the event that is currently investigated
     * @param amp value of the amplitude (maximal voltage in the action potential)
     * @param time_amp_index index of time array where amplitude occurred
     * @return spike height 
     */
    private double calculate_SpikeHeight(ArrayList<Double> voltage_trace, double amp, int time_amp_index){

        //calculate the minimal voltage in this AP-trace
        for(int i = time_amp_index; i < voltage_trace.size()-1; i++){
           
            double previousval = voltage_trace.get(i-1);
            double actualval = voltage_trace.get(i);
            double nextval = voltage_trace.get(i+1);
            
            //find minimal value 
            if(actualval == previousval && actualval < voltage_trace.get(i-2) && actualval < nextval){
                 minimum = actualval;
            }else if(actualval < previousval && actualval < nextval){//problem: what if actualvalue and previousvalue are the same?
                
                minimum = actualval;
                
            }else if(actualval == nextval  && actualval < voltage_trace.get(i+2) && actualval < previousval){
               
                minimum = actualval;
                
            }else if(actualval == nextval && actualval == previousval && actualval < voltage_trace.get(i+2) && actualval < voltage_trace.get(i-2)){
                
                minimum = actualval;
                
            }/*else{
                System.out.println("Minimum not found");
            }*/
            
        }
        
        spike_height = amp - minimum;
        
        return spike_height; 
    }
    
    //NOTE: Muss auf jeden Fall getestet werden 
    /**
     * calculates the Action potential width by means of the mean spike height for every single event 
     * @param voltage_trace voltage array of the event that is currently investigated
     * @param time_trace time array of the event that is currently investigated
     * @param threshold threshold of the action potential
     * @return the calculated action potential width 
     */
    public double calc_APwidth_MSH(ArrayList<Double> voltage_trace, ArrayList<Double> time_trace, double threshold){

        //t1 is the first value of the spike width, t2 is the last value of the spike width
        double t1 = 0, t2 = 0;
        
        int t1_index = 0, t2_index = 0;
//        double neighbor1, neighbor2; 
//        int step = 0;
       
        //calculate the amplitude for this spike 
       //NOTE: eventuell wird die Amplitude noch hier eingesourct!!
        amplitude.calculate_amplitude(voltage_trace, time_trace, threshold);
        double amp = amplitude.getAmplitude();
//        double t_amp = amplitude.getTime_amp();
        int index = amplitude.getIndex();
        
        //calculate the spike height 
        double spikeheight = calculate_SpikeHeight(voltage_trace, amp, index);
        

        //calculate the mean spike height value for this event 
        double mean_spike_height = amp - 0.5 * spikeheight;
        
        //calcute t1 in this for loop
        for(int i = 1; i < index; i++){
            //the calculated may not match the values of the mean_spike_height we check which value is the nearest 
            //to the mean_spike_height 
            double neighbor1 = voltage_trace.get(i-1);
            double neighbor2 = voltage_trace.get(i);
            
            //search the values that lye next to the mean_spike_height
            if(mean_spike_height >= neighbor1 && mean_spike_height <= neighbor2){
                
                //calculate the difference between neighbor1 and the value of the mean_spike_height
                double diff1 = Math.abs(mean_spike_height - neighbor1);
                //calculate the difference between neighbor2 and the value of the mean_spike_height
                double diff2 = Math.abs(mean_spike_height - neighbor2);
                
                //choose the shortest distance from mean_spike_height
                if(diff1 < diff2){
                    //set the index for t1
                    t1_index = i-1;
                }else{
                    //set the index for t1
                    t1_index = i;
                }
                          
                
            }
           
            t1 = time_trace.get(t1_index);
//            System.out.println("t1_index was hopefully changed in if-statement: "+t1_index);
          
        }

        //calculate t2 in this loop
        for(int i = index; i < voltage_trace.size(); i++){
            
            double neighbor1 = voltage_trace.get(i-1);
            double neighbor2 = voltage_trace.get(i);
            
            if(mean_spike_height <= neighbor1 && mean_spike_height >= neighbor2){
                
                //calculate the difference between neighbor1 and the value of the mean_spike_height
                double diff1 = Math.abs(mean_spike_height - neighbor1);
                //calculate the difference between neighbor2 and the value of the mean_spike_height
                double diff2 = Math.abs(mean_spike_height - neighbor2);
                
                //choose the shortest distance from mean_spike_height
                if(diff1 < diff2){
                    //set the index for t1
                    t2_index = i-1;
                }else{
                    //set the index for t1
                    t2_index = i;
                }
                         
                t2 = time_trace.get(t2_index);
//                System.out.println("t2_index was hopefully changed in if-statement: "+t2_index);
                
            }

        }

        apWidth = t2 - t1;
//        System.out.println("t2 was hopefully changed: "+t2);
//        System.out.println("t1 was hopefully changed : "+t1);
//        System.out.println("apWidth = "+apWidth);
        return apWidth;
    }
    
}