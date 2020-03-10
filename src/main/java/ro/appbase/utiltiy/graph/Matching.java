package ro.appbase.utiltiy.graph;

import javafx.util.Pair;
import ro.appbase.object.Element;

import java.util.HashSet;
import java.util.Set;

/**
 * Class Matching
 *
 * [OPTIONAL]
 *
 * @author Loghin Vlad
 */
public class Matching {
    private Set<Pair<Element, Element>> edges;

    /**
     * Constructor
     */
    public Matching(){
        this.edges = new HashSet<>();
    }

    /**
     * Adds a single edge to the matching
     * @param from pointer to the generating node
     * @param to pointer to the destination node
     */
    public void addEdge(Element from ,Element to){
        this.edges.add(new Pair<>(from, to));
    }

    /**
     * Adds all the edges starting from a partition
     * @param s pointer to the Partition containing nodes
     * @return pointer to this Matching
     */
    public Matching addAllEdges(Partition s){
        for( Element e : s.getV() )
            this.addEdge(e, e.getAssignedTo().stream().findFirst().orElse(null));
        return this;
    }

    /**
     * Getter for the Edges in the matching
     * @return pointer to the set of pairs
     */
    public Set<Pair<Element, Element>> getEdges() {
        return this.edges;
    }
}
