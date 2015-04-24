package spike_analysis_tools;


/**
 *
 * @author myra
 */
public class Frequency {
    private int number_events; 
    private double frequency; 
    
    
    
    /*---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
 

    public int getNumber_events() {
        return number_events;
    }

    public void setNumber_events(int number_events) {
        this.number_events = number_events;
    }
    
    
    /**
     * 
     * @param tstop end of measurement in seconds
     * @return frequency
     */
    public double calculate_frequency(double tstop){
        frequency = number_events / tstop; 
        return frequency; 
        
    }
    
   
    
}
