/*
 * NOTE: it might be useful to change the voltage arrays to class objects so that no 
 * confusion can occur when parameters are used in methods
 */
package spike_analysis_tools;

import java.util.ArrayList;

/**
 *
 * @author myra
 */
public class AP_Traces {
    
    private final int event_nr; 
     //store time and voltage trace of the action potential in array lists
    private final ArrayList<Double> voltage_trace = new ArrayList<Double>();
    private final ArrayList<Double> time_trace = new ArrayList<Double>();
//    private final Event e = new Event();
    /*----------------------------------------------------------------------------------------------------*/

    public AP_Traces(int event_nr) {
        this.event_nr = event_nr;
    }

    
//    public void setVoltage_trace(double v) {
//        if(e.getStage1() == 1 || e.getStage2() == 1 || e.getStage3() == 1 || e.getStage4() == 1){
//            voltage_trace.add(v);
//        }
//    }
    
    //NOTE: Eventuell kann die if anweisung noch geloescht werden
    //NOTE: Eventuell brauchen wir diese so gar nicht !
//    public void setTraces(double v, double t) {
//        if(e.getEvent_onset() <= t && e.getEvent_offset() >= t && e.getStage1() != 0){
//            time_trace.add(t);
//            voltage_trace.add(v);
//        }
//    }
    
    public void setTraces(ArrayList<Double> v, ArrayList<Double> t) {
        //each value has to be copied to the arrays because v and t are deleted in Event_Properties 
        for(int i = 0; i < v.size(); i++){
        
            voltage_trace.add(v.get(i));
                      
        
        }
        
        for(int i = 0; i < t.size(); i++){
            
            time_trace.add(t.get(i));
            
        }
            
        
    }
    
    public int getEvent_nr() {
        return event_nr;
    }
    

    public ArrayList<Double> getVoltage_trace() {
        return voltage_trace;
    }

    public ArrayList<Double> getTime_trace() {
        return time_trace;
    }
    
    
    
}
