package ro.appbase.object;

import javafx.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Hospital extends Element {
    private Map<Integer, Resident> preferences;

    public Hospital(String name, int capacity){
        super(name, capacity);
    }

    @Override
    public boolean hasWhereToGo() {
        return !this.tryouts.containsAll(this.preferences.values());
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public Map<Integer, ?> getPreferences() {
        return this.preferences;
    }

    public void setPreferences(Pair<Resident,Integer>... preferences){
        this.preferences = new LinkedHashMap<>();
        for(int i = 0; i < preferences.length; i++) {
            this.preferences.put(i, preferences[i].getKey());
            this.priority.put(preferences[i].getKey(), preferences[i].getValue());
        }
    }

    public void setPreferences(Resident ... preferences){
        this.preferences = new LinkedHashMap<>();
        for(int i = 0; i < preferences.length; i++) {
            this.preferences.put(i, preferences[i]);
            this.priority.put(preferences[i], i);
        }
    }

    public void addResidentToPreferences(Resident resident){
        this.preferences.put(this.preferences.size(), resident);
    }

    @Override
    public String toString() {
        return "Hospital \""
                + this.name
                + "\", capacity = "
                + this.capacity;
    }

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

    @Override
    public Element getLeastAppealingAssignee() {
        Element leastAppealing = null;
        for(Element pair : this.preferences.values()){
            if(this.assignedTo.contains(pair)){
                leastAppealing = pair;
            }
        }
        return leastAppealing;
    }
    public int getPreference(Element obj){
        if(!this.preferences.containsValue(obj))
            return Integer.MAX_VALUE;
        for(Integer key : this.preferences.keySet()){
            if( this.preferences.get(key).equals(obj) ) {
                return key;
            }
        }
        return Integer.MAX_VALUE;
    }
}
