package spike_analysis_tools;
//NOTE: No GUI for this class! this will only be used lateron by the other classes 
//NOTE: Mit anderen Daten weitere Tests durchfuehren und schauen ob Algorithmus stabil ist und eventuelles Abfangen von Exceptions 

//NOTE & TODO: eventuell waere es noch ganz schick, wenn man den Zeitraum eines events festlegt ... hier oder doch woanders? 
/**
 *
 * @author myra
 */
public class Event {
    //Urspruenglich sind alle stages auf null gesetzt
    private int stage1 = 0; 
    private int stage2 = 0; 
    private int stage3 = 0; 
    private int stage4 = 0; 
    //NOTE: derivate werden nur in interessantem Bereich untersucht
//    private int stage5 = 0; 
//    private int stage6 = 0; 
    private int stage = 0; 
    private double threshold; 
    private double restingPot;
    
    private boolean event_detected;
    private boolean onset_detected;
    private boolean offset_detected;
    
    private double event_onset;
    private double event_offset;
    
//   private int counter = 0;
    /*-----------------------------------------------------------------------------------------------------------------------------------------------------------------*/
    
    // FOR OTHER CLASSES THIS METHOD IS RELEVANT ....
    public boolean detect_event(double v){ //, double threshold, double restingPot
        event_detected = false;
        setStage1(v,threshold);
        setStage2(v, threshold);
        setStage3(v, restingPot);
        
        
        
        stage = stage1 + stage2 + stage3; 
        
        if(stage == 3){
            event_detected = true;
            resetStages();
        }
        return event_detected; 
    }
    // AND THIS METHOD IS RELEVANT --> ALL THE OTHER METHODS WILL NOT BE CALLED ELSEWHERE 
    /*    NOTE: only in this class the starting and ending value of the end can be determined!! -- so this class is the class of main interest actually and should be used in most calculations
     *    
     */  
    public boolean detect_event_strict(double v, double v_next, double t){ //double threshold, double restingPot
        event_detected = false;
        
        setStage1(v,threshold);
        setStage2(v, threshold);
        setStage3(v, restingPot);
        setStage4(v, restingPot);
        
        setEvent_onset(v, v_next, t);
        
        stage = stage1 + stage2 + stage3 + stage4; 
        
        if(stage == 4){
            event_detected = true;
//            counter++;
            setEvent_offset(t);
            
            resetStages();
        }
        //if offset was detected reset the offset to false
        offset_detected = false;
        
        return event_detected; 
    }
    
 
   
    private void setStage1(double v, double thresh){
        if(v > thresh ){
            stage1 = 1;
        }
        
    }
    
    private void setStage2(double v, double thresh){
        if(v < thresh && stage1 == 1){
            stage2 = 1;
        }
    }
    
    private void setStage3(double v, double restingPot){
        if(v < restingPot && stage1 == 1 && stage2 == 1){
            stage3 = 1; 
        }
    }
    
    private void setStage4(double v, double restingPot){
        if(v >= restingPot && stage1 == 1 && stage2 == 1 && stage3 == 1){
          stage4 =1;
        }
    }
   
    private void resetStages(){
        stage1 = 0; 
        stage2 = 0;
        stage3 = 0; 
        stage4 = 0; 
        onset_detected = false;
//        offset_detected = false;
    }

     private void setEvent_onset(double v, double v_next, double event_onset) {
        if(v < threshold && v_next >threshold ){
            this.event_onset = event_onset;
            onset_detected = true;
        }
         
    }
     
     private void setEvent_offset(double event_offset) {
//        if(event_detected == true){
            this.event_offset = event_offset;
            offset_detected = true;
//        }
    }
    
    
     
     
    public int getStage1() {
        return stage1;
    }

    public int getStage2() {
        return stage2;
    }

    public int getStage3() {
        return stage3;
    }

    public int getStage4() {
        return stage4;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getRestingPot() {
        return restingPot;
    }

    public void setRestingPot(double restingPot) {
        this.restingPot = restingPot;
    }

    
    public double getEvent_onset() {
        return event_onset;
    }

   

    public double getEvent_offset() {
        return event_offset;
    }

    public boolean isOnset_detected() {
        return onset_detected;
    }

    public boolean isOffset_detected() {
        return offset_detected;
    }

    
    
    
}
