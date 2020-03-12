package ro.appbase.object;

import javafx.util.Pair;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class Hospital, derived from Element
 *
 * [COMPULSORY]
 *
 * @author Loghin Vlad
 */
public class Hospital extends Element {
    private Map<Integer, Resident> preferences;

    /**
     * Constructor
     * @param name Name of this object
     * @param capacity Resident capacity of this Hospital
     */
    public Hospital(String name, int capacity){
        super(name, capacity);
    }

    /**
     * Boolean method used to check whether Element can be unmatched ( has another match )
     * @return true if Element has another match, otherwise false
     */
    @Override
    public boolean hasWhereToGo() {
        return !this.tryouts.containsAll(this.preferences.values());
    }

    /**
     * Getter for assignment capacity
     * @return capacity
     */
    @Override
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Getter for the Element's preferences
     * @return pointer to the map
     */
    @Override
    public Map<Integer, ?> getPreferences() {
        return this.preferences;
    }

    /**
     * Setter for Element's preferences, with equal ordering
     * @param preferences pointer to the List of Paired Resident -- Priority
     */
    public void setPreferences(List<Pair<Resident,Integer>> preferences){
        this.preferences = new LinkedHashMap<>();
        int i = 0;
        for(Pair<Resident, Integer> pair : preferences){
            this.preferences.put(i++, pair.getKey());
            this.priority.put(pair.getKey(), pair.getValue());
        }
    }

    /**
     * Setter for Element's preferences, with strictly increasing ordering
     * @param preferences Varargs of Residents
     */
    public void setPreferences(Resident ... preferences){
        this.preferences = new LinkedHashMap<>();
        for(int i = 0; i < preferences.length; i++) {
            this.preferences.put(i, preferences[i]);
            this.priority.put(preferences[i], i);
        }
    }

    /**
     * Method used to manually add to the list of preferences
     * @param resident pointer to the Element to be added
     */
    public void addResidentToPreferences(Resident resident){
        this.preferences.put(this.preferences.size(), resident);
    }

    /**
     * ToString overridden method
     * @return String interpretation of Element
     */
    @Override
    public String toString() {
        return "Hospital \""
                + this.name
                + "\", capacity = "
                + this.capacity;
    }

    /**
     * Method used to get the next preference that has not been yet tried
     * @return pointer to the Element
     */
    @Override
    public Element getNextTryout() {
        if(this.tryouts.containsAll(this.preferences.values()))
            return null;
        return Objects.requireNonNull(this.preferences
                .entrySet()
                .stream()
                .filter(e -> !this.tryouts.contains(e.getValue()))
                .findFirst()
                .orElse(null))
                .getValue();
    }

    /**
     * Method used to get the least preferable Element assigned to this Element
     * @return pointer to the Element
     */
    @Override
    public Element getLeastAppealingAssignee() {
        Element leastAppealing = null;
        for(Element pair : this.preferences.values())
            if(this.assignedTo.contains(pair))
                leastAppealing = pair;
        return leastAppealing;
    }

    /**
     * Method used to get an Element's preference for this Element
     * @param obj the object whose preference is in question
     * @return the preference of the given Element, Integer.MAX_VALUE if Element is not in this Element's preferences
     */
    @Override
    public int getPreference(Element obj){
        if(!this.preferences.containsValue(obj))
            return Integer.MAX_VALUE;
        for(Integer key : this.preferences.keySet())
            if( this.preferences.get(key).equals(obj) )
                return this.priority.get(obj);
        return Integer.MAX_VALUE;
    }
}
