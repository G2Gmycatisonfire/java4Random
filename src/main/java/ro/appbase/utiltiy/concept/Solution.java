package ro.appbase.utiltiy.concept;

import javafx.util.Pair;
import ro.appbase.object.Element;
import ro.appbase.utiltiy.graph.Matching;

/**
 * Class Solution
 *
 * [OPTIONAL]
 *
 * @author Loghin Vlad
 */
public class Solution {
    private Matching matching;

    /**
     * Constructor
     * @param matching pointer to the Matching representing the solution
     */
    public Solution(Matching matching){
        this.matching = matching;
    }

    /**
     * Overridden toString method
     * @return String interpretation of the Solution
     */
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder().append("Matching found : \n");
        for(Pair<Element,Element> edge : this.matching.getEdges())
            stringBuilder.append("\t").append(edge.getKey()).append(" is assigned to ").append(edge.getValue()).append("\n");
        return stringBuilder.toString();
    }
}
