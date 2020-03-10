package ro.appbase.object;

import java.util.*;

/**
 * Abstract Class Element
 *
 * [OPTIONAL]
 *
 * @author Loghin Vlad
 */
public abstract class Element {
    protected int capacity;
    protected String name;
    protected Set<Element> assignedTo;
    protected Set<Element> tryouts;
    protected Map<Element, Integer> priority;

    /**
     * Constructor
     * @param name String containing the Name of the Element
     * @param capacity Number of possible assignments to Element
     */
    protected Element(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        this.assignedTo = new TreeSet<>(Comparator.comparing(Element::getName));
        this.tryouts = new HashSet<>();
        this.priority = new HashMap<>();
    }

    /**
     * Getter for tryouts Set (Elements which this Element has already tried matching with)
     * @return pointer to the set
     */
    public Set<Element> getTryouts() {
        return this.tryouts;
    }

    /**
     * Getter for Set containing assignments for this Element
     * @return pointer to the set
     */
    public Set<Element> getAssignedTo(){
        return this.assignedTo;
    }

    /**
     * Getter for priority map for this Element's preferences priority
     * @return pointer to the map
     */
    public Map<Element, Integer> getPriority(){
        return this.priority;
    }

    /**
     * Boolean method used to check whether Element can be unmatched ( has another match )
     * @return true if Element has another match, otherwise false
     */
    public abstract boolean hasWhereToGo();

    /**
     * Method used to free this Element's assignments
     */
    public void free(){
        this.assignedTo.clear();
    }

    /**
     * Boolean used to check whether Element can have another Element assigned to him
     * @return true if possible, false otherwise
     */
    public boolean canAssign(){
        return this.assignedTo.size() < this.getCapacity();
    }

    /**
     * Method used to assign another Element to this Element
     * @param other pointer to the other Element
     */
    public void assign(Element other){
        this.assignedTo.add(other);
    }

    /**
     * Getter for assignment capacity
     * @return capacity
     */
    public abstract int getCapacity();

    /**
     * Setter for assignment capacity
     * @param capacity new capacity
     */
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    /**
     * Getter for the Element's name
     * @return pointer to the String
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter for the Element's preferences
     * @return pointer to the map
     */
    public abstract Map<Integer, ? > getPreferences();

    /**
     * ToString overridden method
     * @return String interpretation of Element
     */
    public abstract String toString();

    /**
     * Method used to get the next preference that has not been yet tried
     * @return pointer to the Element
     */
    public abstract Element getNextTryout();

    /**
     * Method used to get the least preferable Element assigned to this Element
     * @return pointer to the Element
     */
    public abstract Element getLeastAppealingAssignee();

    /**
     * Method used to get an Element's preference for this Element
     * @param obj the object whose preference is in question
     * @return the preference of the given Element, Integer.MAX_VALUE if Element is not in this Element's preferences
     */
    public abstract int getPreference(Element obj);

    /**
     * Overridden equals method
     * @param obj The Element which this element is compared to
     * @return true if Elements are equal, false otherwise
     */
    public boolean equals(Element obj){
        return this.name.equals(obj.name);
    }
}
