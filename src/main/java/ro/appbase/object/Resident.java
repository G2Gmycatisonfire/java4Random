package ro.appbase.object;

import javafx.util.Pair;

import java.util.*;

public class Resident extends Element {

    private Map<Integer, Hospital> preferences;

    public Resident(String name){
        super(name, 1);
    }

    @Override
    public String toString(){
        return "Resident "
                + this.name;
    }

    public boolean hasWhereToGo() {
        return !this.tryouts.containsAll(this.preferences.values());
    }

    public void addHospitalToPreferences(Hospital hospital){
        this.preferences.put(this.preferences.size(), hospital);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    public void setPreferences(Pair<Hospital, Integer>... preferences){
        this.preferences = new HashMap<>();
        for(int i = 0; i < preferences.length; i++){
            this.preferences.put(i, preferences[i].getKey());
            this.priority.put(preferences[i].getKey(), preferences[i].getValue());
        }
    }

    public void setPreferences(Hospital ... preferences){
        this.preferences = new HashMap<>();
        for(int i = 0; i < preferences.length; i++){
            this.preferences.put(i, preferences[i]);
            this.priority.put(preferences[i], i);
        }
    }

    @Override
    public Map<Integer, ?> getPreferences() {
        return this.preferences;
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
        for(Element pair : this.preferences.values())
            if(this.assignedTo.contains(pair))
                leastAppealing = pair;
        return leastAppealing;
    }

    @Override
    public int getPreference(Element obj){
        for(Integer key : this.preferences.keySet())
            if( this.preferences.get(key).equals(obj) )
                return this.priority.get(obj);
        return Integer.MAX_VALUE;
    }
}
