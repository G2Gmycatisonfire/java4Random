package ro.appbase.utiltiy.algorithm;

import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;

/**
 * Interface Algorithm
 *
 * [OPTIONAL, completely optional]
 *
 * @author Loghin Vlad
 */
public interface Algorithm {
    /**
     * Method used to start the algorithm
     * @throws InterruptedException used for Thread.sleep in debug
     */
    void start() throws InterruptedException;

    /**
     * Setter for the problem (input)
     * @param p pointer to the problem
     */
    void setProblem(Problem p);

    /**
     * Getter for the solution (output)
     * @return pointer to the solution
     */
    Solution getSolution();

    /**
     * Getter for Start Time in nanoseconds
     * @return start time
     */
    long getStartTime();

    /**
     * Getter for Finish Time in nanoseconds
     * @return finish time
     */
    long getFinishTime();

    /**
     * Getter to total runtime in nanoseconds
     * @return total runtime
     */
    default long getNanoRuntime(){
        return this.getFinishTime() - this.getStartTime();
    }

    /**
     * Getter for total runtime
     * @return total runtime
     */
    default double getRuntime(){
        return this.getNanoRuntime() / Math.pow(10,9);
    }

    /**
     * toString method for runtime
     * @return Describes algorithm runtime
     */
    default String runtimeToString(){
        return "Runtime : "
                + this.getRuntime()
                + " seconds ( "
                + this.getNanoRuntime()
                + " nanoseconds )";
    }
}
