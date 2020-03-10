package ro.appbase.object;

import java.util.*;

public abstract class Element {
    protected int capacity;
    protected String name;
    protected Set<Element> assignedTo;
    protected Set<Element> tryouts;
    protected Map<Element, Integer> priority;

    protected Element(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        this.assignedTo = new TreeSet<>(Comparator.comparing(Element::getName));
        this.tryouts = new HashSet<>();
        this.priority = new HashMap<>();
    }

    public Set<Element> getTryouts() {
        return this.tryouts;
    }

    public Set<Element> getAssignedTo(){
        return this.assignedTo;
    }

    public Map<Element, Integer> getPriority(){
        return this.priority;
    }

    public abstract boolean hasWhereToGo();

    public void free(){
        this.assignedTo.clear();
    }

    public boolean canAssign(){
        return this.assignedTo.size() < this.getCapacity();
    }

    public void assign(Element other){
        this.assignedTo.add(other);
    }

    public abstract int getCapacity();

    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    public String getName(){
        return this.name;
    }

    public abstract Map<Integer, ? > getPreferences();

    public abstract String toString();

    public abstract Element getNextTryout();

    public abstract Element getLeastAppealingAssignee();

    public abstract int getPreference(Element obj);

    public boolean equals(Element obj){
        return this.name.equals(obj.name);
    }
}
