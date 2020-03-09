package ro.appbase.utiltiy.concept;

import javafx.util.converter.ShortStringConverter;
import ro.appbase.object.Element;
import ro.appbase.object.Hospital;
import ro.appbase.object.Resident;
import ro.appbase.utiltiy.algorithm.Algorithm;
import ro.appbase.utiltiy.algorithm.GaleShapely;
import ro.appbase.utiltiy.graph.Partition;

import java.util.*;
import java.util.stream.Collectors;

public class Problem {
    private Partition s;
    private Partition t;
    private List<Resident> residents;
    private Set<Hospital> hospitals;
    private Algorithm algorithm;

    public static class Builder {
        private List<Resident> residents;
        private Set<Hospital> hospitals;

        public Builder withHospitals(Hospital ... hospitals){
            this.hospitals = new TreeSet<>(Comparator.comparing(Element::getName));
            this.hospitals.addAll(Arrays.asList(hospitals));

            return this;
        }

        public Builder withResidents(Resident ... residents){
            this.residents = new ArrayList<>();
            this.residents.addAll(Arrays.asList(residents));
            this.residents.sort(Comparator.comparing(Element::getName));

            return this;
        }

        public Problem build(){
            Problem problem = new Problem();

            problem.hospitals = this.hospitals;
            problem.residents = this.residents;
            problem.t = new Partition(this.hospitals);
            problem.s = new Partition(this.residents);
            problem.algorithm = new GaleShapely(problem);

            return problem;
        }
    }

    private Problem(){

    }

    public List<Resident> getResidents() {
        return this.residents;
    }

    public Set<Hospital> getHospitals() {
        return this.hospitals;
    }

    public String toString(){
        return "Problem instance : \nResidents = "
                + this.residents.toString()
                + "\nHospitals = "
                + this.hospitals.toString()
                + "\nAnd preferences : \n"
                + this.preferencesToString();
    }

    public void printPreferences(){
        this.printResidentsPreferences();
        this.printHospitalPreferences();
    }

    public void printResidentsPreferences(){
        for(Resident resident : this.residents) {
            System.out.print(resident.getPreferences().toString());
            System.out.print("\n");
        }
    }

    public void printHospitalPreferences(){
        for(Hospital hospital : this.hospitals){
            System.out.print(hospital.getPreferences().toString());
            System.out.print("\n");
        }
    }

    private String preferencesToString(){
        return this.residentsPreferencesToString() + this.hospitalsPreferencesToString();
    }

    private String residentsPreferencesToString(){
        StringBuilder result = new StringBuilder();
        for(Resident resident : this.residents)
            result.append(resident.getPreferences().toString()).append("\n");
        return result.toString();
    }

    private String hospitalsPreferencesToString(){
        StringBuilder result = new StringBuilder();
        for(Hospital hospital : this.hospitals)
            result.append(hospital.getPreferences().toString()).append("\n");
        return result.toString();
    }

    public Partition getS() {
        return this.s;
    }

    public Partition getT() {
        return this.t;
    }

    public Algorithm getAlgorithm(){
        return this.algorithm;
    }
}
