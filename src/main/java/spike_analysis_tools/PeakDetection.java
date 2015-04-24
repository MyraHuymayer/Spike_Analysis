//NOTE: Algorithmus nochmal angucken und verstehen und TESTEN
package spike_analysis_tools;

import java.util.ArrayList;

/**
 * based on the Peak Detection algorithm in Matlab by Eli Billauer (http://www.billauer.co.il/peakdet.html [13.02.15]).
 * @author myra
 */
public class PeakDetection {
    
    private final ValueStorage maxtab = new ValueStorage();
    private final ValueStorage mintab = new ValueStorage();

    /**
     * Find the minima and maxima in a set of data points and store minima and maxima and their indices 
     * @param datapoints array of the data points which we want to investigate for minima and maxima 
     * @param delta maximum was preceded by a value lower by delta ??
     */
    public void findMaximum(ArrayList<Double> datapoints, double delta){
        double mn = Double.POSITIVE_INFINITY;
        double mx = Double.NEGATIVE_INFINITY;
        int mnpos = 0, mxpos = 0;
        int lookformax =1;
        
        for(int i = 0; i< datapoints.size(); i++){
           
           
           if(datapoints.get(i) > mx){
               mx = datapoints.get(i);
               mxpos = i;
           }
           
           if(datapoints.get(i) < mn){
               mn = datapoints.get(i);
               mnpos = i;
           }
            
            if(lookformax == 1){
                if(datapoints.get(i) < mx - delta){          
                    maxtab.store_Value_and_Index(mx, i);
                    mn = datapoints.get(i);
                    mnpos = i;
                    lookformax = 0;
                }
            }else {
                if(datapoints.get(i) > mn + delta){
                    mintab.store_Value_and_Index(mn, i);
                    mx = datapoints.get(i);
                    mxpos = i;
                    lookformax = 1;
                }
            }
        }
        
        
        
        
    }
    
    
 //mn = Inf; mx = -Inf;
//mnpos = NaN; mxpos = NaN;
//
//lookformax = 1;
//
//for i=1:length(v)
//  this = v(i);
//  if this > mx, mx = this; mxpos = x(i); end
//  if this < mn, mn = this; mnpos = x(i); end
//  
//  if lookformax
//    if this < mx-delta
//      maxtab = [maxtab ; mxpos mx];
//      mn = this; mnpos = x(i);
//      lookformax = 0;
//    end  
//  else
//    if this > mn+delta
//      mintab = [mintab ; mnpos mn];
//      mx = this; mxpos = x(i);
//      lookformax = 1;
//    end
//  end
//end  
    
//function [maxtab, mintab]=peakdet(v, delta, x)
//%PEAKDET Detect peaks in a vector
//%        [MAXTAB, MINTAB] = PEAKDET(V, DELTA) finds the local
//%        maxima and minima ("peaks") in the vector V.
//%        MAXTAB and MINTAB consists of two columns. Column 1
//%        contains indices in V, and column 2 the found values.
//%      
//%        With [MAXTAB, MINTAB] = PEAKDET(V, DELTA, X) the indices
//%        in MAXTAB and MINTAB are replaced with the corresponding
//%        X-values.
//%
//%        A point is considered a maximum peak if it has the maximal
//%        value, and was preceded (to the left) by a value lower by
//%        DELTA.
//
//% Eli Billauer, 3.4.05 (Explicitly not copyrighted).
//% This function is released to the public domain; Any use is allowed.
//
//maxtab = [];
//mintab = [];
//
//v = v(:); % Just in case this wasn't a proper vector
//
//if nargin < 3
//  x = (1:length(v))';
//else 
//  x = x(:);
//  if length(v)~= length(x)
//    error('Input vectors v and x must have same length');
//  end
//end
//  
//if (length(delta(:)))>1
//  error('Input argument DELTA must be a scalar');
//end
//
//if delta <= 0
//  error('Input argument DELTA must be positive');
//end
//
//mn = Inf; mx = -Inf;
//mnpos = NaN; mxpos = NaN;
//
//lookformax = 1;
//
//for i=1:length(v)
//  this = v(i);
//  if this > mx, mx = this; mxpos = x(i); end
//  if this < mn, mn = this; mnpos = x(i); end
//  
//  if lookformax
//    if this < mx-delta
//      maxtab = [maxtab ; mxpos mx];
//      mn = this; mnpos = x(i);
//      lookformax = 0;
//    end  
//  else
//    if this > mn+delta
//      mintab = [mintab ; mnpos mn];
//      mx = this; mxpos = x(i);
//      lookformax = 1;
//    end
//  end
//end

    public ValueStorage getMaxtab() {
        return maxtab;
    }

    public ValueStorage getMintab() {
        return mintab;
    }

}
