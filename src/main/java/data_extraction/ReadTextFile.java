/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data_extraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author myra
 */
public class ReadTextFile {
        private ArrayList<Double> time = new ArrayList<Double>();
    private ArrayList<Double> voltage =  new ArrayList<Double>();
    private ArrayList<Double> current =  new ArrayList<Double>();
    private ArrayList<Double> gating_variable_activation =  new ArrayList<Double>();
    private ArrayList<Double> gating_variable_inactivation =  new ArrayList<Double>();

    private String filename;
    
    private String path; 
    private File file;
    
    //created nested ArrayList -- this should have as many dimensions as numcol
    private List<ArrayList<Double>>lists = new ArrayList<ArrayList<Double>>();
    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//    public String getFilename() {
//        return filename;
//    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    
    
    //Als Nutzer muss man wissen, wie seine Datei aufgebaut ist --> z.B. Spalte 1 = Zeit, Spalte 2 = Spannung etc. -- Anzahl der Spalten angeben 
   /**
    * 
    * @param numcol the number of columns in the data file that is read 
    * @throws Exception 
    */
    public void readDataFromFile(int numcol, String filename) throws Exception{ //
       int counter = 0; 
       
       
       
       for(int i = 0; i < numcol; i++){
           lists.add(new ArrayList<Double>());
       }
       
       try{
            FileInputStream fis = new FileInputStream(filename);
            Reader r = new BufferedReader(new InputStreamReader(fis));

            StreamTokenizer st = new StreamTokenizer(r); 
            
            
            

            st.resetSyntax();
            st.parseNumbers();
            st.whitespaceChars(' ', ' ');
            st.whitespaceChars(' ', '\t');
//            st.whitespaceChars('\n', '\n');
            st.eolIsSignificant(false);
            st.ordinaryChars('e', 'e');
            st.ordinaryChars('+', '+');
           

            boolean eof = false;
            
            double d = 0;   
            
            do{
                int token = st.nextToken();
                
//                System.out.println("Counter = "+counter);

                
//                st.ttype
                switch (token) {
                    case StreamTokenizer.TT_NUMBER:  //System.out.println("Number: "+st.nval);// list.add(st.nval);
                        d = st.nval;
                        counter++;
//                        System.out.println("1. d = "+d+" counter = "+counter);
                        lists.get(counter-1).add(d);
//                         System.out.println("Last list element "+lists.get(counter -1).get(lists.get(counter-1).size()-1)+" == "+d+" ?");
                        break;
//                    case StreamTokenizer.TT_WORD:
//                        if(st.ttype = '-'){
//                            
//                        }
                        
                    case StreamTokenizer.TT_EOF: //System.out.println(" ");
                        eof = true;
                        break;
                    case StreamTokenizer.TT_EOL: //System.out.println("End of Line! ");
                        if(counter < numcol){
                            throw new IOException("Error! The given column number is too large! Check input file please!");
                        }else if(counter > numcol){
                            throw new IOException("Error! The given column number is too small! Check input file please!");
                        }
                        counter = 0; 
                        break;
                    case StreamTokenizer.TT_WORD:
//                        System.out.println("hello "+ st.sval);
                        break;
                    case 'e':
                        st.nextToken();
                        if (st.ttype == '+') {
                            st.nextToken();
                            d = d * Math.pow(10.0, st.nval);
                            lists.get(counter -1).remove(lists.get(counter-1).size()-1);
                            lists.get(counter -1).add(d);
//                            System.out.println("2. d = "+d+" counter = "+counter);
//                            System.out.println("Last list element "+lists.get(counter -1).get(lists.get(counter-1).size()-1)+" == "+d+" ?");
                        }else if(st.ttype == StreamTokenizer.TT_NUMBER){
                            d = d * Math.pow(10.0, st.nval);
                            lists.get(counter -1).remove(lists.get(counter-1).size()-1);
                            lists.get(counter -1).add(d);
//                            System.out.println("3. d = "+d+" counter = "+counter);
//                            System.out.println("Last list element "+lists.get(counter -1).get(lists.get(counter-1).size()-1)+" == "+d+" ?");
                        }else{
                            throw new IOException("Error! Double expected after e! ");
                        }
                         break;
                    default: System.out.println(" ");//        case StreamTokenizer.TT_NUMBER:(char) token +
               
                   
//                switch(token){
//                    case StreamTokenizer.TT_EOF: //System.out.println(" ");
//                        eof = true;
//                        break;
//                    case StreamTokenizer.TT_EOL: System.out.println("End of Line! ");
//                        counter = 0; 
//                        System.out.println("Counter = "+counter);
//                        break;
//                    case StreamTokenizer.TT_NUMBER:  //System.out.println("Number: "+st.nval);// list.add(st.nval);
//                        
//                        break; 
//                    default: System.out.println((char) token + " ");
                }
//                if(counter > 0 && counter <= numcol){
//                    lists.get(counter-1).add(d);
//                    System.out.println(d);
//                }

            }while(!eof);
       } catch(Exception e){
           e.printStackTrace();
       }
       
     
    }
    
    /**
     * 
     * @param col column containing the time in the data file
     * @return 
     */
    public ArrayList<Double> getTime(int col){
        //expecting that most users will start to count from 1 and not from 0 
        time = lists.get(col-1);
        return time; 
    }
    
    /**
     * 
     * @param col column containing the voltage in the data file
     * @return an array containing the voltage
     */
    public ArrayList<Double> getVoltage(int col){
        voltage = lists.get(col-1); 
        return voltage; 
        
    }
    
    public ArrayList<Double> getCurrent(int col){
        current = lists.get(col-1); 
        return current; 
        
    }
    
    public ArrayList<Double> getActivationGatingVariable(int col){
        gating_variable_activation = lists.get(col-1); 
        return gating_variable_activation; 
        
    }
    
    //not das ist quasi ein setter und getter in einem !
    public ArrayList<Double> getInactivationGatingVariable(int col){
        gating_variable_inactivation = lists.get(col-1); 
        return gating_variable_inactivation; 
        
    }
}
