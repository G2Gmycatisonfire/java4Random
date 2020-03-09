package ro.appbase.object;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Hospital extends Element {
    private Map<Integer, Resident> preferences;

    public Hospital(String name, int capacity){
        super(name, capacity);
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public Map<Integer, ?> getPreferences() {
        return this.preferences;
    }

    public void setPreferences(Resident ... preferences){
        this.preferences = new LinkedHashMap<>();
        for(int i = 0; i < preferences.length; i++)
            this.preferences.put(i, preferences[i]);
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
        //System.out.println("\t...Start find worst match debug");
        Element leastAppealing = null;
        for(Element pair : this.preferences.values()){
           // System.out.println("\t" + this);
            //System.out.println("\t" + pair);
            if(this.assignedTo.contains(pair)){
                leastAppealing = pair;
                //System.out.println("\t\t" + pair);
            }
        }
        //System.out.println("\t...End find worst match debug");
        return leastAppealing;
    }
    public int getPreference(Element obj){
        if(!this.preferences.containsValue(obj))
            return Integer.MAX_VALUE;
        for(Integer key : this.preferences.keySet()){
            if( this.preferences.get(key).equals(obj) ) {
                //System.out.println("For " + this + "," + obj + "'s priority is " + key);
                return key;
            }
        }
        return Integer.MAX_VALUE;
    }
}
