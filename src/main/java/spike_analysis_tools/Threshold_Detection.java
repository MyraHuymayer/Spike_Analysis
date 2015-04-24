package spike_analysis_tools;

import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author myra
 */
@ComponentInfo(name = "Threshold Detection", category = "Analysis/SAT", description = "")
public class Threshold_Detection implements Serializable{
    
    private static final long serialVersionUID = 1L;

 
    @OutputInfo(name="mean threshold")
    public double second_derivative_method(@ParamInfo(name = "Single Events from measurement")ArrayList<AP_Traces> single_event, 
            @ParamInfo(name = "Delta")double delta, 
            @ParamInfo(name = "Stepsize [ms]")double stepsize)throws IOException{
        
        CentralDifference cd = new CentralDifference();
        Threshold th = new Threshold();
        ArrayList<Double> threshold = new ArrayList<Double>();
        double thresh = 0;
        
        for(int i = 0; i<single_event.size(); i++){
            
            ArrayList<Double> time = single_event.get(i).getTime_trace();
            ArrayList<Double> voltage = single_event.get(i).getVoltage_trace();
            
            ArrayList<Double> secondDeriv = cd.second_derivative(voltage, time, stepsize);
            
            thresh = thresh + th.secondDerivativeMethod(secondDeriv, voltage, time, delta);
            
            
            threshold.add(th.secondDerivativeMethod(secondDeriv, voltage, time, delta));
            
            secondDeriv.clear();
            
        }
        
        for(int i = 0; i< threshold.size(); i++){
            System.out.println("Threshold for several spike traces: "+threshold.get(i));
        }
        int number = single_event.size();
        thresh = thresh/number;
        
        return thresh;
    }
    
    @OutputInfo(name="mean threshold")
    public double third_derivative_method(@ParamInfo(name = "Single Events from measurement")ArrayList<AP_Traces> single_event, 
            @ParamInfo(name = "Delta") double delta,
            @ParamInfo(name = "Stepsize [ms]")double stepsize) throws IOException{
        
        CentralDifference cd = new CentralDifference();
        Threshold th = new Threshold();
        ArrayList<Double> threshold = new ArrayList<Double>();
        
        double thresh = 0;
        
        for(int i = 0; i < single_event.size(); i++){
            ArrayList<Double> time = single_event.get(i).getTime_trace();
            ArrayList<Double> voltage = single_event.get(i).getVoltage_trace();
            
            ArrayList<Double> thirdDeriv = cd.third_derivative(voltage, time, stepsize);
            
            thresh = thresh + th.thirdDerivativeMethod(thirdDeriv, voltage, time, delta);
            System.out.println("------Threshold for several spike traces: "+th.thirdDerivativeMethod(thirdDeriv, voltage, time, delta));
            
            threshold.add(th.thirdDerivativeMethod(thirdDeriv, voltage, time, delta));
            System.out.println("Size of deriv = "+thirdDeriv.size());
            
            thirdDeriv.clear();
        }
        for(int i = 0; i< threshold.size(); i++){
            System.out.println("Threshold for several spike traces: "+threshold.get(i));
        }
        int number = single_event.size();
        thresh = thresh/number;
        
        return thresh;
        
    }
    
}
