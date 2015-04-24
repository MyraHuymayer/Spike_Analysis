package spike_analysis_tools;

import eu.mihosoft.vrl.annotation.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.Collections;
/**
 *
 * @author myra
 */
//nochmal ueberlegen wie man die Kategory sinnvoll unterteilt fuer die VRL 
//die einzelnen Dateien sollte man den Events zuordnen koennen, d.h. die Event nr wird nicht null gesetzt sondern man definiert sich eine unabhaengige Variable - oder liest sie aus 
@ComponentInfo(name = "Read Files", category = "Analysis/SAT", description = "")
public class Read_Files implements Serializable{
     private static final long serialVersionUID = 1L;
     
    //TO DO: 
  
    //Methode, die alle Dateien in einem Ordner auslesen kann? 
    // TODO: was sich eventuell noch aendern muss: lese Zahl aus String und gebe das wieder -- eventuell in eigener Methode (Stichwort: unabhaengige Variable)
    
    /**
     * Read all text files in a folder; Note that the structure of all text files should match
     * @param inFolder choose folder, containing the appropriate text files
     * @param numberOfColumns number of columns in the textfiles: counting from 1, ... , n columns. 
     * @param colT which column contains the time?
     * @param colV which column contains the voltage?
     * @return from all textfiles the AP_traces, containing the voltage and time course; stored in an ArrayList
     * @throws IOException
     * @throws Exception 
     */
     @OutputInfo(name="Array of spike trains")
    public ArrayList<AP_Traces> readAllFilesInFolder(@ParamInfo(name = "Input Folder",
            style = "load-folder-dialog", options = "") File inFolder, 
            @ParamInfo(name = "number of columns in textfile") int numberOfColumns,
            @ParamInfo(name = "column containing time") int colT, 
            @ParamInfo(name = "column containing voltage") int colV)throws IOException, Exception{
        
//        File f = new File("c:\\mydirectory");
//
        FilenameFilter textFilter = new FilenameFilter(){
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");             
            }
        };
        
        File[] files = inFolder.listFiles(textFilter);
        //sorts Files in folder in numerical order -- NOTE: has problems with 0, rename to 0.0
        Arrays.sort(files);

        ArrayList<AP_Traces> spikeTrains = new ArrayList<AP_Traces>();
        
        int i = 0;
        
        for(File file : files){
            
            ReadFromFile readfile = new ReadFromFile();
            String filename = file.getCanonicalPath();
           
            System.out.println("file: "+filename);
            readfile.readDataFromFile(numberOfColumns, filename);
      
            ArrayList<Double> voltage = readfile.getVoltage(colV);
            ArrayList<Double> time = readfile.getTime(colT);
            
            i++;

            //event number is mapped to the file_number, which still has to be specified lateron
            AP_Traces singleSpikeTrain = new AP_Traces(i);

            singleSpikeTrain.setTraces(voltage, time);
            
//            System.out.println("Size of voltage trace = "+singleSpikeTrain.getVoltage_trace().size());
//            System.out.println("Size of time trace = "+singleSpikeTrain.getTime_trace().size());
            
            spikeTrains.add(singleSpikeTrain);

        }
        
        return spikeTrains;
        
    }
  
    //Methode, mit der man ein einziges File auswaehlt, das dann ausgewertet wird.
    //zunaechst gehen wir davon aus, dass nur die Spannung und die Zeit ausgelesen wird, deshalb legen wir von vornherein fest, dass wir das auslesen wollen! 
    //UM DEN SPIKE TRAIN ZU ANALYSIEREN IST ES AUCH DAS WAS WIR BRAUCHEN. 
    //Es koennte aber auch sein, dass die Datei mehr Spalten hat als nur die zwei
    /**
     * read from a single textfile. 
     * @param textfile textfile, that is to be read 
     * @param numberOfColumns number of columns in the textfile: counting from 1, ... , n columns
     * @param colT which column contains the time?
     * @param colV which column contains the voltage?
     * @return object instance of AP_traces, containing the voltage and time course
     * @throws Exception 
     */
   //NOTE: es waere wahrscheinlich noch deutlich schoener, wenn man die Daten eines einzelnen Spike trains genauso auswertet wie die mehrerer Dateien - d.h. man gibt ArrayList fuer ein Element zurueck 
   //das erspart mit in Zukunft zwei Methoden zu schreiben
   @OutputInfo(name="Spike train from one file")
    public ArrayList<AP_Traces> readSingleFile(@ParamInfo(name = "Textfile",
            style = "load-dialog", options = "") File textfile, 
            @ParamInfo(name = "number of columns in textfile") int numberOfColumns,
            @ParamInfo(name = "column containing time") int colT, 
            @ParamInfo(name = "column containing voltage") int colV) throws Exception{
        
        ReadFromFile readfile = new ReadFromFile();
        String filename = textfile.getCanonicalPath();
        readfile.readDataFromFile(numberOfColumns, filename);
        
        //das muss noch allgemeiner implementiert werden, denn es koennte ja gut sein, dass der Benutzer selbst festlegen moechte was alles aus
        ArrayList<Double> voltage = readfile.getVoltage(colV);
        ArrayList<Double> time = readfile.getTime(colT);
        
        ArrayList<AP_Traces> spiketrains = new ArrayList<AP_Traces>();
        //no event has occured yet, therefore the event number is defined as zero
        AP_Traces singleSpikeTrain = new AP_Traces(0);
        
        singleSpikeTrain.setTraces(voltage, time);
        spiketrains.add(singleSpikeTrain);
        
        return spiketrains;
               
    }
   



}

