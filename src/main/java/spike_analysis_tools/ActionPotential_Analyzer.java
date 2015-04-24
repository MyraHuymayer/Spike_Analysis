package spike_analysis_tools;


import eu.mihosoft.vrl.annotation.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
/**
 *
 * @author myra
 */
//nochmal ueberlegen wie man die category sinnvoll unterteilt fuer die VRL 

/* Macht diese Klasse so ueberhaupt Sinn? Eventuell waere es Sinnvoll noch statistische Auswertungen einzubauen! 
 * ==> z.B. Varianzen etc
 */
@ComponentInfo(name = "Action Potential Analyzer", category = "Analysis/SAT", description = "")
public class ActionPotential_Analyzer implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
        
   //was wollen wir uns ausgeben lassen? 
    /**
     * 
     * @param spiketrains
     * @param threshold
     * @param restingpot
     * @param ap_method insert integer to "determine", which method is used to calculate the action potential width: 1 --> action potential width calculated by means of the threshold; 2 --> action potential width calculated by means of the mean spike height
     * @param outFileName
     * @throws IOException 
     */
    public void action_potential_analysis_all_trains(@ParamInfo(name = "Array of spike trains")ArrayList<AP_Traces> spiketrains, 
            @ParamInfo(name = "Threshold [mV]")double threshold, 
            @ParamInfo(name = "Resting potential [mV]")double restingpot, 
            @ParamInfo(name = "Method number")int ap_method, 
            @ParamInfo(name = "Output file name")String outFileName) throws IOException{
        
        File outfile = new File(outFileName);
        
        try{
            FileOutputStream is = new FileOutputStream(outfile);
            OutputStreamWriter osw = new OutputStreamWriter(is, "UTF-8");
            Writer writer = new BufferedWriter(osw);
            
            writer.write("FileNo    Event     Amp            APWidth \n");
            writer.close();
            
        }catch(IOException e){
            String filename = outfile.getCanonicalPath();
            System.err.println("Error: Problem writing to the textfile "+filename+"!");
        }
        
        for(int i = 0; i< spiketrains.size();i++){
            ArrayList<AP_Traces> all_traces_from_train = split_spike_train(spiketrains.get(i), threshold, restingpot);
            
            for(int j = 0; j<all_traces_from_train.size(); j++){
                
                SpikeDimensions sd = new SpikeDimensions();
                Amplitude amp = new Amplitude();
                ArrayList<Double> time_trace = all_traces_from_train.get(j).getTime_trace();
                ArrayList<Double> voltage_trace = all_traces_from_train.get(j).getVoltage_trace();
                
                int file_number = i;
                int event_number = all_traces_from_train.get(j).getEvent_nr();
                
                amp.calculate_amplitude(voltage_trace, time_trace, threshold);
                
                double amplitude = amp.getAmplitude();
                double ap_width = 0;
                
                if(ap_method == 1){    
                    ap_width = sd.calc_APwidth_THRESH(threshold, voltage_trace, time_trace);
                }else if(ap_method == 2){
                    ap_width = sd.calc_APwidth_MSH(voltage_trace, time_trace, threshold);
                }else{
                    throw new IOException("Error: The chosen method nr. "+ap_method+" is no method to calculate the action potential width!");             
                }

                write_to_textfile(outfile, file_number, event_number, amplitude, ap_width);
                
            }
            
        }
    }

    //wie soll der aufbau meiner textdatei sein? 
    //was genau wird reingeschrieben : 
        //Amplitude eines jeden Events 
        //Actionspotentialbreite von jedem Ereignis
        //Ereignis nummer vor jedem 
    private void write_to_textfile(File outFile, int fileNum, int eventNum, double amplitude, double spikewidth) throws IOException{
  
            
        try{
            FileOutputStream is = new FileOutputStream(outFile,true);
            OutputStreamWriter osw = new OutputStreamWriter(is, "UTF-8");
            Writer writer = new BufferedWriter(osw);
            
            writer.write(fileNum + "          " + eventNum + "        "+amplitude+"       "+spikewidth+"\n");
            writer.close();
            
        }catch(IOException e){
            String filename = outFile.getCanonicalPath();
            System.err.println("Error: Problem writing to the textfile "+filename+"!");
        }
//        try {
//            //Whatever the file path is.
//            File statText = new File("E:/Java/Reference/bin/images/statsTest.txt");
//            FileOutputStream is = new FileOutputStream(statText);
//            OutputStreamWriter osw = new OutputStreamWriter(is);    
//            Writer w = new BufferedWriter(osw);
//            w.write("POTATO!!!");
//            w.close();
//        } catch (IOException e) {
//            System.err.println("Problem writing to the file statsTest.txt");
//        }

        
    }
    
    /**
     * method that splits up the spike train into its single event traces
     * @param spiketrain spike train from a single measurement/file
     * @param threshold threshold for action potential initiation in this train
     * @param restingpot resting potential of the cell measured
     * @return the detected event traces in an array
     */
    private ArrayList<AP_Traces> split_spike_train(AP_Traces spiketrain, double threshold, double restingpot){
        
        Event_Properties ep = new Event_Properties(); 
        ArrayList<Double> time = spiketrain.getTime_trace();
        ArrayList<Double> voltage = spiketrain.getVoltage_trace();
        
        ep.set_APTraces(voltage, time, threshold, restingpot);
        
        ArrayList<AP_Traces> all_event_traces = ep.getAllTraces(); 
        
        return all_event_traces;
    }
}
