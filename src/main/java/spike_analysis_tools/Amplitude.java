//TODO: TESTEN obs funktioniert
package spike_analysis_tools;

import java.util.*;
/**
 *
 * @author myra
 */
public class Amplitude {
    
    private double amplitude; 
    private double time_amp;
    private int index;
//    Event e = new Event();
    
    /*----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    /**
     * calculate the amplitude of the action potential and the time, when this action potential occurs
     * @param voltage_trace voltage array of the event that is currently investigated
     * @param time_trace time array of the event that is currently investigated
     * @param threshold threshold of the action potential
     */
    public void calculate_amplitude(ArrayList<Double> voltage_trace, ArrayList<Double> time_trace, double threshold){
        //nochmal drueber nachdenken 
        for(int i = 2; i < voltage_trace.size(); i++){
//            if(e.getStage1()== 1 && e.getStage3() == 0 ){
                double previous = voltage_trace.get(i-2);
                double actual = voltage_trace.get(i-1);
                double next = voltage_trace.get(i);
                
                if(actual > previous && actual > next){
                    amplitude = actual;
                    time_amp = time_trace.get(i-1);
                    index = i-1;
//                }
            }
        }
           
    } 

    public double getAmplitude() {
        return amplitude;
    }
    
    public double getTime_amp() {
        return time_amp;
    }

    //macht es Sinn den Index hier festzulegen? ja oder? -- da zur Berechnung der amplitude and der Spike Dimensions 
    //die gleichen AP_Traces verwenden ==> NOTE: DAS MUSS IM PLUGIN AUF JEDEN FALL SICHERGESTELLT WERDEN
    public int getIndex() {
        return index;
    }

    
   
    
        
}
