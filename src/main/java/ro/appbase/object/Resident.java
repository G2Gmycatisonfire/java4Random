package ro.appbase.object;

import java.util.*;
import java.util.stream.Stream;

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

    @Override
    public int getCapacity() {
        return 1;
    }

    public void setPreferences(Hospital ... preferences){
        this.preferences = new HashMap<>();
        for(int i = 0; i < preferences.length; i++)
            this.preferences.put(i, preferences[i]);
    }

    @Override
    public Map<Integer, ?> getPreferences() {
        return this.preferences;
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
        //System.out.println("...Start find worst match debug");
        Element leastAppealing = null;
        for(Element pair : this.preferences.values()){
            //System.out.println(this);
            //System.out.println(pair);
            if(this.assignedTo.contains(pair)){
                leastAppealing = pair;
                //System.out.println(pair);
            }
        }
        //System.out.println("...End find worst match debug");
        return leastAppealing;
    }

    @Override
    public int getPreference(Element obj){
        for(Integer key : this.preferences.keySet()){
            if( this.preferences.get(key).equals(obj) )
                return key;
        }
        return Integer.MAX_VALUE;
    }
}
