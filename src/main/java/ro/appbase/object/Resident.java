package ro.appbase.object;

import javafx.util.Pair;

import java.util.*;

/**
 * Class Resident, derived from Element
 *
 * [COMPULSORY]
 *
 * @author Loghin Vlad
 */
public class Resident extends Element {

    private Map<Integer, Hospital> preferences;

    /**
     * Constructor
     * @param name given Name for the object, capacity default 1
     */
    public Resident(String name){
        super(name, 1);
    }

    /**
     * ToString overridden method
     * @return String interpretation of Element
     */
    @Override
    public String toString(){
        return "Resident "
                + this.name;
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
     * Method manually adds Hospital to the list of preferences
     * @param hospital Pointer to the Hospital to be added
     */
    public void addHospitalToPreferences(Hospital hospital){
        this.preferences.put(this.preferences.size(), hospital);
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
     * Setter for Element's preferences, with equal ordering
     * @param preferences pointer to the List of Paired Hospital -- Priority
     */
    public void setPreferences(List<Pair<Hospital,Integer>> preferences){
        this.preferences = new LinkedHashMap<>();
        int i = 0;
        for(Pair<Hospital, Integer> pair : preferences){
            this.preferences.put(i++, pair.getKey());
            this.priority.put(pair.getKey(), pair.getValue());
        }
    }

    /**
     * Setter for Element's preferences, with strictly increasing ordering
     * @param preferences Varargs of Hospitals
     */
    public void setPreferences(Hospital ... preferences){
        this.preferences = new HashMap<>();
        for(int i = 0; i < preferences.length; i++){
            this.preferences.put(i, preferences[i]);
            this.priority.put(preferences[i], i);
        }
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
     * @return the preference of the given Element for this Element, Integer.MAX_VALUE if Element is not in this Element's preferences
     */
    @Override
    public int getPreference(Element obj){
        for(Integer key : this.preferences.keySet())
            if( this.preferences.get(key).equals(obj) )
                return this.priority.get(obj);
        return Integer.MAX_VALUE;
    }
}
