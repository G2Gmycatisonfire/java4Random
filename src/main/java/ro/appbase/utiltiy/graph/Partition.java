package ro.appbase.utiltiy.graph;

import ro.appbase.object.Element;
import ro.appbase.object.Hospital;
import ro.appbase.object.Resident;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Class Partition
 *
 * [OPTIONAL]
 *
 * @author Loghin Vlad
 */
public class Partition {
    Set<Element> V;

    /**
     * Constructor
     * @param V Set of given Hospitals
     */
    public Partition(Set<Hospital> V){
        this.V = new TreeSet<>(Comparator.comparing(Element::getName));
        this.V.addAll(V);
    }

    /**
     * Constructor
     * @param V List of given residents
     */
    public Partition(List<Resident> V){
        this.V = new TreeSet<>(Comparator.comparing(Element::getName));
        this.V.addAll(V);
    }

    /**
     * Getter for the Set of vertices
     * @return pointer to the Set of Elements
     */
    public Set<Element> getV(){
        return this.V;
    }

    /**
     * Overridden toString method
     * @return String interpretation of Partition
     */
    public String toString(){
        return "Partition with nodes = " + this.V.toString();
    }
}
