package spike_analysis_tools;


import eu.mihosoft.vrl.system.InitPluginAPI;
import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;
/**
 *
 * @author myra
 */
public class SAPluginConfigurator extends VPluginConfigurator{
    
    
    public SAPluginConfigurator(){
        //specification of plugin name and version
        setIdentifier(new PluginIdentifier("Spike_Analysis","0.1"));
        
        //description of the plugin
        setDescription("Tool, to analyze Spike trains. <br>"
                + "Reads data from textfile (yielded from NEURON).<br>"
                + "Returns the threshold, the action potential width, the firing rate.");
        
        //copyright info
        setCopyrightInfo("Spike Analysis tool",
                "(c) Myra Huymayer", "www.gcsc.uni-frankfurt.com", "License name?", "License text?");
    }
    
     @Override
    public void register(PluginAPI api){
        
        //register plugin with canvas
        if(api instanceof VPluginAPI){
            VPluginAPI vapi = (VPluginAPI) api;
            vapi.addComponent(Read_Files.class);
            vapi.addComponent(ActionPotential_Analyzer.class);
            vapi.addComponent(Spike_Train_Analyzer.class);
            vapi.addComponent(Threshold_Detection.class);
        }
    }
    
    @Override
    public void unregister(PluginAPI api){
        //nothing to unregister
    }
    
    @Override
    public void init(InitPluginAPI iApi){
        //nothing to init
    }
}
