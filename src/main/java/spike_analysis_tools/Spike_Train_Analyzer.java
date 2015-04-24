package spike_analysis_tools;

import eu.mihosoft.vrl.annotation.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author myra
 */
//was soll diese Klasse koennen? Sie bekommt entweder ein SpikeTrain oder ein array von spiketrains - d.h. es gibt zwei Methoden
//
@ComponentInfo(name = "Spike Train Analyzer", category = "Analysis/SAT", description = "")
public class Spike_Train_Analyzer implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Double> frequencies = new ArrayList<Double>();
    private ArrayList<Double> meanActionPotentialWidth = new ArrayList<Double>();
    private ArrayList<Integer> numberOfEvents = new ArrayList<Integer>();
//    private double mean_action_potential_width;
//    private double frequency;
//    private int number;
    
    /*--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

    
    /**
     * 
     * @param spiketrains
     * @param threshold
     * @param rest
     * @param tstop
     * @param apmethod insert integer to "determine", which method is used to calculate the action potential width: 1 --> action potential width calculated by means of the threshold; 2 --> action potential width calculated by means of the mean spike height
     */
    public void analyze_spike_trains(@ParamInfo(name = "Array of spike trains") ArrayList<AP_Traces> spiketrains, 
            @ParamInfo(name = "Threshold [mV]") double threshold, 
            @ParamInfo(name = "Resting potential [mV]") double rest,
            @ParamInfo(name = "TStop [s]")double tstop, 
            @ParamInfo(name = "Method number")int apmethod) throws IOException{
        //frequenz 
        //anzahl der ereignisse
        //mittlere AP-Breite --> je nachdem welchen int man angibt wird entweder die ap-breite mittels der schwelle oder mittels der mean spike height methode durchgefuehrt
        //
        for(int i = 0; i<spiketrains.size(); i++){
            
            ArrayList<Double> time = spiketrains.get(i).getTime_trace();
            ArrayList<Double> voltage = spiketrains.get(i).getVoltage_trace();
            
            Event event = new Event();
            event.setRestingPot(rest);
            event.setThreshold(threshold);
            
            Frequency f = new Frequency();
            
            Event_Properties ep = new Event_Properties();
            
            int num = ep.number_of_events(voltage, time, threshold, rest);
            numberOfEvents.add(num);
            System.out.println("Number of events: " + num );
            
            f.setNumber_events(num);
            double freq = f.calculate_frequency(tstop);
            System.out.println("Frequency: " + freq );
            
            frequencies.add(freq);
            
            ep.set_APTraces(voltage, time, threshold, rest);
                        
            ArrayList<AP_Traces> all_event_traces = ep.getAllTraces();
//            double[] actionpot_currentTrain = new double[all_event_traces.size()];
            
            
            SpikeDimensions sd = new SpikeDimensions();
            //funktioniert nicht richtig
            if(apmethod == 1){
                double mean_AP_width = 0;
                
                for(int ii = 0; ii<all_event_traces.size(); ii++ ){
//                    System.out.println("Wie oft in dieser Schleife? "+ ii);
                    ArrayList<Double> time_trace = all_event_traces.get(ii).getTime_trace();
                    ArrayList<Double> voltage_trace = all_event_traces.get(ii).getVoltage_trace();
                                    
                    
                    
                    mean_AP_width = mean_AP_width + sd.calc_APwidth_THRESH(threshold, voltage_trace, time_trace);
//                    actionpot_currentTrain[ii] = sd.calc_APwidth_THRESH(threshold, voltage_trace, time_trace);
//                    System.out.println("MEAN AP WIDTH = "+mean_AP_width);
                    
                }
//                System.out.println("---------------------------------------------");
                mean_AP_width = mean_AP_width/num;
//                System.out.println("MEAN AP WIDTH = "+mean_AP_width);
                meanActionPotentialWidth.add(mean_AP_width);       
                
            }else if(apmethod == 2){
                double mean_AP_width = 0;
                for(int ii = 0; ii<all_event_traces.size(); ii++ ){
                    ArrayList<Double> time_trace = all_event_traces.get(ii).getTime_trace();
                    ArrayList<Double> voltage_trace = all_event_traces.get(ii).getVoltage_trace();
                                                       
                    mean_AP_width = mean_AP_width + sd.calc_APwidth_MSH(voltage_trace, time_trace, threshold);

//                    System.out.println("MEAN AP WIDTH = "+mean_AP_width);
                    
                }
//                System.out.println("---------------------------------------------");
                mean_AP_width = mean_AP_width/num;
//                System.out.println("MEAN AP WIDTH = "+mean_AP_width);
                meanActionPotentialWidth.add(mean_AP_width);   
            }else{
                throw new IOException("Error: The chosen method nr. "+apmethod+" is no method to calculate the action potential width!");
                
            }
            
            
        }
    }

    //Vorerst als getter definiert - vorraussichtlich macht es Sinn die folgenden Elemente als Trajectories zurueckzugeben
    public ArrayList<Double> getFrequencies() {
        return frequencies;
    }

    public ArrayList<Double> getMeanActionPotentialWidth() {
        return meanActionPotentialWidth;
    }

    public ArrayList<Integer> getNumberOfEvents() {
        return numberOfEvents;
    }
    
    //    /**
//     * 
//     * @param spiketrain
//     * @param threshold
//     * @param rest
//     * @param tstop
//     * @param apmethod
//     * @return
//     * @throws IOException 
//     */
//    public String analyse_single_spike_train(@ParamInfo(name = "Single spike train") AP_Traces spiketrain,
//            @ParamInfo(name = "Threshold [mV]")double threshold, 
//            @ParamInfo(name = "Resting potential [mV]") double rest,
//            @ParamInfo(name = "TStop [s]")double tstop, 
//            @ParamInfo(name = "Method number")int apmethod) throws IOException{
//        
//        ArrayList<Double> time = spiketrain.getTime_trace();
//        ArrayList<Double> voltage = spiketrain.getVoltage_trace();
//      
//        Event event = new Event();
//        event.setRestingPot(rest);
//        event.setThreshold(threshold);
//        
//        Frequency f = new Frequency();
//            
//        Event_Properties ep = new Event_Properties();
//            
//        number = ep.number_of_events(voltage, time, threshold, rest);
//        
//        f.setNumber_events(number);
//        frequency = f.calculate_frequency(tstop);
//        System.out.println("Frequency: " + frequency );
//        
//        ep.set_APTraces(voltage, time, threshold, rest);
//        ArrayList<AP_Traces> all_event_traces = ep.getAllTraces();
//        
//        SpikeDimensions sd = new SpikeDimensions();
//        double mean_AP_width = 0;
//        String methodName;
//        
//        if(apmethod == 1){
//                
//                    for(int ii = 0; ii<all_event_traces.size(); ii++ ){
////                    System.out.println("Wie oft in dieser Schleife? "+ ii);
//                    ArrayList<Double> time_trace = all_event_traces.get(ii).getTime_trace();
//                    ArrayList<Double> voltage_trace = all_event_traces.get(ii).getVoltage_trace();
//                                    
//                    
//                    
//                    mean_AP_width = mean_AP_width + sd.calc_APwidth_THRESH(threshold, voltage_trace, time_trace);
////                    actionpot_currentTrain[ii] = sd.calc_APwidth_THRESH(threshold, voltage_trace, time_trace);
////                    System.out.println("MEAN AP WIDTH = "+mean_AP_width);
//                    
//                }
////                System.out.println("---------------------------------------------");
//                methodName ="Threshold";
//                mean_action_potential_width = mean_AP_width/number;
////                System.out.println("MEAN AP WIDTH = "+mean_AP_width);
//               
//                
//            }else if(apmethod == 2){
//                
//                for(int ii = 0; ii<all_event_traces.size(); ii++ ){
//                    ArrayList<Double> time_trace = all_event_traces.get(ii).getTime_trace();
//                    ArrayList<Double> voltage_trace = all_event_traces.get(ii).getVoltage_trace();
//                                                       
//                    mean_AP_width = mean_AP_width + sd.calc_APwidth_MSH(voltage_trace, time_trace, threshold);
//
////                    System.out.println("MEAN AP WIDTH = "+mean_AP_width);
//                    
//                }
////                System.out.println("---------------------------------------------");
//                methodName ="Mean spike height";
//                mean_action_potential_width = mean_AP_width/number;
////                System.out.println("MEAN AP WIDTH = "+mean_AP_width);
//                
//            }else{
//                throw new IOException("Error: The chosen method nr. "+apmethod+" is no method to calculate the action potential width!");
//                
//            }
//        
//        //am liebsten haette ich das durch neue Zeilen getrennt
//        String outmessage ="For this file: ";
//        outmessage = outmessage + "Number of events: "+number+"; ";
//        outmessage = outmessage + "Frequency: "+frequency+"; ";
//        outmessage = outmessage + "Mean action potential width calculated by means of "+methodName+" : "+mean_action_potential_width+"";
//        return outmessage;
//                
//                
//            
//    }
//    
    
    
}
