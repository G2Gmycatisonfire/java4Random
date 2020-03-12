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
 * Class EqualPriorityStartPoint
 *
 * Source describing bonus requirement 1
 *
 * --- Requirement 3 is theoretical, has an example in MultipleMatching start point ---
 *
 * [BONUS]
 *
 * @author Loghin Vlad
 */
public class EqualPriorityStartPoint {
    public static void main(String[] args){
        Resident[] residents = new Resident[]{
                new Resident("R0"),
                new Resident("R1"),
                new Resident("R2"),
                new Resident("R3")
        };

        Hospital[] hospitals = new Hospital[]{
                new Hospital("H0",1),
                new Hospital("H1",2),
                new Hospital("H2",2)
        };

        List<Pair<Hospital, Integer>> residentPreferences = new ArrayList<>();
        residentPreferences.add(new Pair<>(hospitals[0], 0));
        residentPreferences.add(new Pair<>(hospitals[1], 1));
        residentPreferences.add(new Pair<>(hospitals[2], 1));
        residents[0].setPreferences(residentPreferences);

        residentPreferences.clear();
        residentPreferences.add(new Pair<>(hospitals[0], 0));
        residentPreferences.add(new Pair<>(hospitals[1], 1));
        residentPreferences.add(new Pair<>(hospitals[2], 2));
        residents[1].setPreferences(residentPreferences);

        residentPreferences.clear();
        residentPreferences.add(new Pair<>(hospitals[0], 0));
        residentPreferences.add(new Pair<>(hospitals[1], 1));
        residents[2].setPreferences(residentPreferences);

        residentPreferences.clear();
        residentPreferences.add(new Pair<>(hospitals[0], 0));
        residentPreferences.add(new Pair<>(hospitals[2], 1));
        residents[3].setPreferences(residentPreferences);

        List<Pair<Resident, Integer>> hospitalPreferences = new ArrayList<>();
        hospitalPreferences.add(new Pair<>(residents[3], 0));
        hospitalPreferences.add(new Pair<>(residents[0], 1));
        hospitalPreferences.add(new Pair<>(residents[1], 2));
        hospitalPreferences.add(new Pair<>(residents[2], 3));
        hospitals[0].setPreferences(hospitalPreferences);

        hospitalPreferences.clear();
        hospitalPreferences.add(new Pair<>(residents[0], 0));
        hospitalPreferences.add(new Pair<>(residents[1], 1));
        hospitalPreferences.add(new Pair<>(residents[2], 2));
        hospitals[1].setPreferences(hospitalPreferences);

        hospitalPreferences.clear();
        hospitalPreferences.add(new Pair<>(residents[0], 0));
        hospitalPreferences.add(new Pair<>(residents[1], 1));
        hospitalPreferences.add(new Pair<>(residents[3], 2));
        hospitals[2].setPreferences(hospitalPreferences);

        Problem p = new Problem.Builder()
                .withHospitals(hospitals)
                .withResidents(residents)
                .withAlgorithm(new GaleShapely())
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

        System.out.println("Is matching perfect : " + PerfectMatchingChecker.isMatchingPerfect(p));
    }
}
