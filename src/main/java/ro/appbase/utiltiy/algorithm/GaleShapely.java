package ro.appbase.utiltiy.algorithm;

import ro.appbase.object.Element;
import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;
import ro.appbase.utiltiy.graph.Matching;

/**
 * Class GaleShapley, implementation of Algorithm
 *
 * Generates perfect matching
 *
 * [BONUS]
 *
 * @author Loghin Vlad
 */
public class GaleShapely implements Algorithm {
    private Problem p;
    long startTime;
    long finishTime;

    /**
     * Constructor
     */
    public GaleShapely() {

    }

    @Override
    public void start() throws InterruptedException {
        this.startTime = System.nanoTime();

        while(this.p.getS().getV().stream().anyMatch(Element::canAssign)){
            Element node = this.p.getS().getV().stream()
                    .filter(Element::canAssign)
                    .findFirst()
                    .orElse(null);

            if(node != null){
                Element match = node.getNextTryout();   node.getTryouts().add(match);
                if(match == null){
                    node.setCapacity(0);
                    continue;
                }
                if( match.canAssign() ){
                    match.assign(node);
                    node.assign(match);
                }
                else{
                    Element worstMatch = match.getLeastAppealingAssignee();
                    if( match.getPreference(node) < match.getPreference(worstMatch) && worstMatch!=null && worstMatch.hasWhereToGo() ) {
                        worstMatch.free();
                        match.getAssignedTo().remove(worstMatch);

                        match.getAssignedTo().add(node);
                        node.getAssignedTo().add(match);
                    }
                }
            }
        }

        this.finishTime = System.nanoTime();
    }

    @Override
    public void setProblem(Problem p) {
        this.p = p;
    }

    @Override
    public Solution getSolution() {
        return new Solution(new Matching().addAllEdges(this.p.getS()));
    }

    @Override
    public long getStartTime() {
        return this.startTime;
    }

    @Override
    public long getFinishTime() {
        return this.finishTime;
    }
}
