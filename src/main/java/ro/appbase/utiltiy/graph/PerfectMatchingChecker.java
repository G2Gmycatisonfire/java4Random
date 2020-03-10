package ro.appbase.utiltiy.graph;

import ro.appbase.object.Element;
import ro.appbase.utiltiy.concept.Problem;

/**
 * Class PerfectMatchingChecker
 *
 * [OPTIONAL]
 *
 * @author Loghin Vlad
 */
public class PerfectMatchingChecker {
    /**
     * Method checking stability of a matching
     *  - forall pairs r-h, if exists r' or h' such as r' is more favorable to h than r
     *  - or h' to r more than h, matching is not stable
     * @param p pointer to the Problem (input + output)
     * @return true if matching is stable, false otherwise
     */
    public static boolean isMatchingPerfect(Problem p){
        for(Element e : p.getT().getV()){
            Element worstMatch = e.getLeastAppealingAssignee();
            for(Element other : p.getS().getV())
                if(e.getAssignedTo().contains(other) && e.getPreference(other) > e.getPreference(worstMatch))
                    return false;
        }
        return true;
    }
}
