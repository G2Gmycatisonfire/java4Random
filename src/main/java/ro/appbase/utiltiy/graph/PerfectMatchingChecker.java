package ro.appbase.utiltiy.graph;

import ro.appbase.object.Element;
import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;

public class PerfectMatchingChecker {

    public static boolean isMatchingPerfect(Problem p){
        for(Element e : p.getT().getV()){
            Element worstMatch = e.getLeastAppealingAssignee();
            for(Element other : p.getS().getV()){
                if(e.getAssignedTo().contains(other) && e.getPreference(other) > e.getPreference(worstMatch))
                    return false;
            }
        }
        return true;
    }
}
