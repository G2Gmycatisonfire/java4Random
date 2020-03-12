package ro.appbase.configurations;

import javafx.util.Pair;
import ro.appbase.object.Hospital;
import ro.appbase.object.Resident;
import ro.appbase.utiltiy.algorithm.GaleShapely;
import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;
import ro.appbase.utiltiy.graph.PerfectMatchingChecker;

import java.util.ArrayList;
import java.util.List;

/**
 *  Class MultipleMatching
 *
 *  Source describing bonus requirement 3 example
 *
 *  [BONUS]
 *
 * @author Loghin Vlad
 */
public class MultipleMatching {
    public static void main(String[] args){
        Resident[] residents = new Resident[]{
                new Resident("R0"),
                new Resident("R1")
        };

        Hospital[] hospitals = new Hospital[]{
                new Hospital("H0",1),
                new Hospital("H1",1)
        };

        List<Pair<Hospital, Integer>> residentPreferences = new ArrayList<>();
        residentPreferences.add(new Pair<>(hospitals[0], 0));
        residentPreferences.add(new Pair<>(hospitals[1], 0));
        residents[0].setPreferences(residentPreferences);

        residentPreferences.clear();
        residentPreferences.add(new Pair<>(hospitals[0], 0));
        residentPreferences.add(new Pair<>(hospitals[1], 0));
        residents[1].setPreferences(residentPreferences);

        List<Pair<Resident, Integer>> hospitalPreferences = new ArrayList<>();
        hospitalPreferences.add(new Pair<>(residents[0], 0));
        hospitalPreferences.add(new Pair<>(residents[1], 0));
        hospitals[0].setPreferences(hospitalPreferences);

        hospitalPreferences.clear();
        hospitalPreferences.add(new Pair<>(residents[0], 0));
        hospitalPreferences.add(new Pair<>(residents[1], 0));
        hospitals[1].setPreferences(hospitalPreferences);

        Problem p = new Problem.Builder()
                .withHospitals(hospitals)
                .withResidents(residents)
                .withAlgorithm(new GaleShapely())
                .checkForMoreMatchings(false)
                .build();

        Problem p2 = new Problem.Builder()
                .withHospitals(hospitals)
                .withResidents(residents)
                .withAlgorithm(new GaleShapely())
                .checkForMoreMatchings(true)
                .build();

        try{
            p.getAlgorithm().start();
        }
        catch(InterruptedException e){
            System.out.println(e.toString());
        }

        Solution s = p.getAlgorithm().getSolution();

        System.out.println(s);
        System.out.println(p.getAlgorithm().runtimeToString());

        try{
            p2.getAlgorithm().start();
        }
        catch(InterruptedException e){
            System.out.println(e.toString());
        }

        Solution s2 = p2.getAlgorithm().getSolution();

        System.out.println(s2);

        System.out.println("Is matching 1 perfect : " + PerfectMatchingChecker.isMatchingPerfect(p));
        System.out.println("Is matching 2 perfect : " + PerfectMatchingChecker.isMatchingPerfect(p2));
    }
}
