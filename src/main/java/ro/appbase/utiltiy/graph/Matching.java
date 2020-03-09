package ro.appbase.utiltiy.graph;

import javafx.util.Pair;
import ro.appbase.object.Element;

import java.util.HashSet;
import java.util.Set;

public class Matching {
    private Set<Pair<Element, Element>> edges;

    public Matching(){
        this.edges = new HashSet<>();
    }

    public void addEdge(Element from ,Element to){
        this.edges.add(new Pair<>(from, to));
    }

    public Matching addAllEdges(Partition s){
        for( Element e : s.getV() )
            this.addEdge(e, e.getAssignedTo().stream().findFirst().orElse(null));
        return this;
    }

    public Set<Pair<Element, Element>> getEdges() {
        return this.edges;
    }
}
