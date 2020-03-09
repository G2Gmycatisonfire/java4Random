package ro.appbase.utiltiy.concept;

import javafx.util.Pair;
import ro.appbase.object.Element;
import ro.appbase.utiltiy.graph.Matching;

public class Solution {
    private Matching matching;

    public Solution(Matching matching){
        this.matching = matching;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder().append("Matching found : \n");
        for(Pair<Element,Element> edge : this.matching.getEdges())
            stringBuilder.append("\t").append(edge.getKey()).append(" is assigned to ").append(edge.getValue()).append("\n");

        return stringBuilder.toString();
    }
}
