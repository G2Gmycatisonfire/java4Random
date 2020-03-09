package ro.appbase.utiltiy.algorithm;

import ro.appbase.object.Element;
import ro.appbase.object.Resident;
import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;
import ro.appbase.utiltiy.graph.Matching;
import ro.appbase.utiltiy.graph.Partition;

import java.util.Set;

public class GaleShapely implements Algorithm {
    private Problem p;
    long startTime;
    long finishTime;

    public GaleShapely(Problem p) {
        this.p = p;
    }

    @Override
    public void start() throws InterruptedException {
        while(this.p.getS().getV().stream().anyMatch(Element::canAssign)){
            //Thread.sleep(1000);
            Element node = this.p.getS().getV().stream()
                    .filter(Element::canAssign)
                    .findFirst()
                    .orElse(null);

            //System.out.println(node);

            if(node != null){
                Element match = node.getNextTryout();   node.getTryouts().add(match);
                if( match.canAssign() ){
                    match.assign(node);
                    node.assign(match);
                    //System.out.println(node + " matched to " + match);
                }
                else{
                    Element worstMatch = match.getLeastAppealingAssignee();
                    //System.out.println("the worst match is = " + worstMatch);
                    if( match.getPreference(node) < match.getPreference(worstMatch) ) {
                        worstMatch.free();
                        match.getAssignedTo().remove(worstMatch);

                        match.getAssignedTo().add(node);
                        node.getAssignedTo().add(match);
                        //System.out.println(node + " with priority = " + match.getPreference(node) + " matched to " + match + ", removed " + worstMatch + " with priority = " + match.getPreference(worstMatch) + " matched to " + match);
                    }
                }
            }
        }


        //for(Element e : this.p.getS().getV()){
       //     System.out.println(e + " is assigned to " + e.getAssignedTo().toString());
        //}
    }

    @Override
    public Solution getSolution() {
        return new Solution(new Matching().addAllEdges(this.p.getS()));
    }

    @Override
    public long getNanoRuntime() {
        return 0;
    }

    @Override
    public double getRuntime() {
        return 0;
    }
}
