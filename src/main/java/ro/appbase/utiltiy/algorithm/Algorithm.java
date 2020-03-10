package ro.appbase.utiltiy.algorithm;

import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;

public interface Algorithm {
    void start() throws InterruptedException;
    void setProblem(Problem p);
    Solution getSolution();
    long getStartTime();
    long getFinishTime();
    default long getNanoRuntime(){
        return this.getStartTime() - this.getFinishTime();
    }
    default double getRuntime(){
        return (this.getStartTime() - this.getFinishTime()) / Math.pow(10,9);
    }
}
