package ro.appbase.utiltiy.algorithm;

import ro.appbase.utiltiy.concept.Solution;

public interface Algorithm {
    void start() throws InterruptedException ;
    Solution getSolution();
    long getNanoRuntime();
    double getRuntime();
}
