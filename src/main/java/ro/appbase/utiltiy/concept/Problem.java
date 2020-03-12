package ro.appbase.utiltiy.concept;

import ro.appbase.object.Element;
import ro.appbase.object.Hospital;
import ro.appbase.object.Resident;
import ro.appbase.utiltiy.algorithm.Algorithm;
import ro.appbase.utiltiy.graph.Partition;

import java.util.*;

/**
 * Class Problem
 *
 * [OPTIONAL]
 *
 * @author Loghin Vlad
 */
public class Problem {
    private Partition s;
    private Partition t;
    private List<Resident> residents;
    private Set<Hospital> hospitals;
    private Algorithm algorithm;
    private boolean checkForMultipleMatchings;

    /**
     * Nested Class Builder
     *
     * [completely optional] [Builder Pattern]
     *
     * @author Loghin Vlad
     */
    public static class Builder {
        private List<Resident> residents;
        private Set<Hospital> hospitals;
        private Algorithm algorithm;
        private boolean checkForMultipleMatchings = false;

        /**
         * Method used to enable a different method of generating a matching, used to demonstrate bonus task 3
         * @param condition true or false as in toggle or do not toggle
         * @return pointer to the builder
         */
        public Builder checkForMoreMatchings(boolean condition){
            this.checkForMultipleMatchings = condition;
            return this;
        }

        /**
         * Method used to assign hospitals to problem builder
         * @param hospitals Varargs of Hospital
         * @return pointer to the builder
         */
        public Builder withHospitals(Hospital ... hospitals){
            this.hospitals = new TreeSet<>(Comparator.comparing(Element::getName));
            this.hospitals.addAll(Arrays.asList(hospitals));

            return this;
        }

        /**
         * Method used to assign residents to problem builder
         * @param residents Varargs of Resident
         * @return pointer to the builder
         */
        public Builder withResidents(Resident ... residents){
            this.residents = new ArrayList<>();
            this.residents.addAll(Arrays.asList(residents));
            this.residents.sort(Comparator.comparing(Element::getName));

            return this;
        }

        /**
         * Method used to assign algorithm
         * @param algorithm pointer to the Algorithm
         * @return pointer to the builder
         */
        public Builder withAlgorithm(Algorithm algorithm){
            this.algorithm = algorithm;

            return this;
        }

        /**
         * build Method, called last, returns built object
         * @return pointer to the Problem built object
         */
        public Problem build(){
            Problem problem = new Problem();

            problem.hospitals = this.hospitals;
            problem.residents = this.residents;
            problem.t = new Partition(this.hospitals);
            problem.s = new Partition(this.residents);
            problem.algorithm = this.algorithm;
            problem.checkForMultipleMatchings = this.checkForMultipleMatchings;

            problem.algorithm.setProblem(problem);

            return problem;
        }
    }

    /**
     * Constructor, private (called in builder)
     */
    private Problem(){

    }

    /**
     * Method called to check whether to generate matching with < or  <= priority, used in bonus task 3
     * @return true if <=, false if <
     */
    public boolean checkForMatchings(){
        return this.checkForMultipleMatchings;
    }

    /**
     * Getter for Residents in problem
     * @return pointer to the List of residents
     */
    public List<Resident> getResidents() {
        return this.residents;
    }

    /**
     * Getter for Hospitals in problem
     * @return pointer to the Set of residents
     */
    public Set<Hospital> getHospitals() {
        return this.hospitals;
    }

    /**
     * Overridden toString Method
     * @return String interpretation of the problem's input
     */
    public String toString(){
        return "Problem instance : \nResidents = "
                + this.residents.toString()
                + "\nHospitals = "
                + this.hospitals.toString()
                + "\nAnd preferences : \n"
                + this.preferencesToString();
    }

    /**
     * Method called to print all objects' preferences
     */
    public void printPreferences(){
        this.printResidentsPreferences();
        this.printHospitalPreferences();
    }

    /**
     * Method called to print Residents' preferences
     */
    public void printResidentsPreferences(){
        for(Resident resident : this.residents) {
            System.out.print(resident + "'s preferences = ");
            System.out.print(resident.getPreferences().toString());
            System.out.print("\n");
        }
    }

    /**
     * Method called to print Hospitals' preferences
     */
    public void printHospitalPreferences(){
        for(Hospital hospital : this.hospitals){
            System.out.print(hospital + "'s preferences = ");
            System.out.print(hospital.getPreferences().toString());
            System.out.print("\n");
        }
    }

    /**
     * Method called to turn preferences into String
     * @return String of preferences tables
     */
    private String preferencesToString(){
        return this.residentsPreferencesToString() + this.hospitalsPreferencesToString();
    }

    /**
     * Method called to turn residents' preferences to String
     * @return String of residents' preferences table
     */
    private String residentsPreferencesToString(){
        StringBuilder result = new StringBuilder();
        for(Resident resident : this.residents)
            result.append(resident.getPreferences().toString()).append("\n");
        return result.toString();
    }

    /**
     * Method called to turn hospitals' preferences to String
     * @return String of hospitals' preferences table
     */
    private String hospitalsPreferencesToString(){
        StringBuilder result = new StringBuilder();
        for(Hospital hospital : this.hospitals)
            result.append(hospital.getPreferences().toString()).append("\n");
        return result.toString();
    }

    /**
     * Getter for Partition S
     * @return pointer to the partition
     */
    public Partition getS() {
        return this.s;
    }

    /**
     * Getter for Partition T
     * @return pointer to the partition
     */
    public Partition getT() {
        return this.t;
    }

    /**
     * Getter for the Algorithm
     * @return pointer to the algorithm
     */
    public Algorithm getAlgorithm(){
        return this.algorithm;
    }
}
