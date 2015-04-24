
package spike_analysis_tools;

import java.util.ArrayList;

/**
 *
 * @author myra
 */
public class Event_Properties {
   
    private int number_events; 
    private final ArrayList<AP_Traces> all = new ArrayList<AP_Traces>();
    
    private final Event e = new Event();
    
    /*---------------------------------------------------------------------------------------------------------------------------------------*/
     
    /**
     * counts the number of events in a given voltage trace - uses a rather 
     * @param voltage voltage array, read from file 
     * @param time time array, read from file
     * @param threshold threshold either defined by user or calculated by using the threshold class (still to be implemented)
     * @param restingPot resting potential, defined by user 
     * @return number of events
     */
    public int number_of_events(ArrayList<Double> voltage, ArrayList<Double> time, double threshold, double restingPot){ //im Prinzip moechte muss man das nicht immer wieder uebergeben! -- gebraucht wird es letztlich nur von event detection
        number_events = 0; 
        
        for(int i = 0; i<voltage.size(); i++){
            e.setRestingPot(restingPot);
            e.setThreshold(threshold);

           boolean bool = e.detect_event(voltage.get(i)); 
           //, threshold , restingPot
           if(bool == true){
              number_events++; 

           }
       }
        return number_events; 
    }
    
    //NOTE: um das hier festlegen zu koennen muessen wir die Events mit der Strikten Methode ermitteln, da wir auf jeden Fall den Offset brauchen. 
    //NOTE: DAS MUSS UNBEDINGT GETESTET WERDEN 
   /**
    * sets voltage and time traces for each event so that parameters can be evaluated for single action potentials
     * @param voltage voltage array, read from file 
     * @param time time array, read from file
     * @param threshold threshold either defined by user or calculated by using the threshold class (still to be implemented)
     * @param restingPot resting potential, defined by user 
    */
    public void set_APTraces(ArrayList<Double> voltage, ArrayList<Double> time, double threshold, double restingPot ){
        
        
        //counter for the strict events
        int num_eve = 0;
        //on and off are set 0 before starting the for loop - just in case 
        double on ;
        double off;

        int testcounter = 0;
//        System.out.println("t_ONSET = "+on);
        ArrayList<Double> time_temp = new ArrayList<Double>();
        ArrayList<Double> volt_temp = new ArrayList<Double>();
        
        for(int i = 0; i < time.size() -1; i++){
            e.setRestingPot(restingPot);
            e.setThreshold(threshold);
            //was an event detected? Only true when the last stage of an event is set true
            boolean event_detected = e.detect_event_strict(voltage.get(i), voltage.get(i+1), time.get(i));
            //detection of the on and offset
            on = e.getEvent_onset();
//            System.out.println("t_ONSET = "+on);
            
            off = e.getEvent_offset();
            
                    
            double time_test = time.get(i);
            //if an onset was detected, start writing the time and voltage values into two dummy arrays
            if(e.isOnset_detected() == true){


                time_temp.add(time.get(i));

                volt_temp.add(voltage.get(i));

            }

            
            //wird auch ein offset gefunden und somit ein event detektiert beende den "Schreibprozess"
            if(event_detected == true && off == time.get(i)){

                //count event 
                num_eve++;

                //last item added to these arrays --> time.get(i) == off
                time_temp.add(time.get(i));
//                System.out.println("Last added time :"+time_temp.get(time_temp.size()-1));
//                System.out.println("Time_temp size:"+time_temp.size());
                volt_temp.add(voltage.get(i));
//                System.out.println("Last added voltage:"+volt_temp.get(volt_temp.size()-1));
//                System.out.println("Volt_temp size:"+volt_temp.size());
                
                
                AP_Traces apt = new AP_Traces(num_eve);               
                //speicher die Arraylists in einer neuen Objektinstanz von AP_Traces
                apt.setTraces(volt_temp, time_temp);

                all.add(apt);
                
                //remove all items of the temporal time and voltage-arrays
                time_temp.clear();
                volt_temp.clear();

            }
             
            
            
           
        }


    }

    public ArrayList<AP_Traces> getAllTraces() {
        return all;
    }

    

    
   
    
}
