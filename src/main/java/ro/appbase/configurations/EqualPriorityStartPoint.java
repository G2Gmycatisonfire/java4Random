package ro.appbase.configurations;

import javafx.util.Pair;
import ro.appbase.object.Hospital;
import ro.appbase.object.Resident;
import ro.appbase.utiltiy.algorithm.GaleShapely;
import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Class EqualPriorityStartPoint
 *
 * Source describing bonus requirement 1 and 3
 *
 * --- Requirement 3 is theoretical ---
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

        residents[0].setPreferences(
                new Pair<>(hospitals[0], 0),
                new Pair<>(hospitals[1], 1),
                new Pair<>(hospitals[2], 1)
        );
        residents[1].setPreferences(
                new Pair<>(hospitals[0], 0),
                new Pair<>(hospitals[1], 1),
                new Pair<>(hospitals[2], 2)
        );
        residents[2].setPreferences(
                new Pair<>(hospitals[0], 0),
                new Pair<>(hospitals[1], 1)
        );
        residents[3].setPreferences(
                new Pair<>(hospitals[0], 0),
                new Pair<>(hospitals[2], 1)
        );

        hospitals[0].setPreferences(
                new Pair<>(residents[3], 0),
                new Pair<>(residents[0], 1),
                new Pair<>(residents[1], 2),
                new Pair<>(residents[2], 3)
        );
        hospitals[1].setPreferences(
                new Pair<>(residents[0], 0),
                new Pair<>(residents[1], 1),
                new Pair<>(residents[2], 2)
        );
        hospitals[2].setPreferences(
                new Pair<>(residents[0], 0),
                new Pair<>(residents[1], 1),
                new Pair<>(residents[3], 2)
        );

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

        p.printPreferences();
    }
}
