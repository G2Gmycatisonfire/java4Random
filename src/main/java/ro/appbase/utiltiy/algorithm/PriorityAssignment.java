package ro.appbase.utiltiy.algorithm;

import ro.appbase.object.Element;
import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;
import ro.appbase.utiltiy.graph.Matching;

/**
 * Class PriorityAssignment, implementation of Algorithm
 *
 * Creates matching based on Hospital requirements
 *
 * [OPTIONAL]
 *
 * @author Loghin Vlad
 */
public class PriorityAssignment implements Algorithm {
    private Problem p;
    private long startTime;
    private long finishTime;

    /**
     * Constructor
     */
    public PriorityAssignment(){

    }

    @Override
    public void start() throws InterruptedException {
        this.startTime = System.nanoTime();

        for(Element h : this.p.getT().getV()){
            while(h.canAssign()){
                Element r = h.getNextTryout();

                if(r == null)
                    break;

                h.getTryouts().add(r);
                if(r.canAssign()){
                    h.assign(r);
                    r.assign(h);
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
