package ro.appbase.configurations;

import ro.appbase.object.Hospital;
import ro.appbase.object.Resident;
import ro.appbase.utiltiy.algorithm.GaleShapely;
import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;
import ro.appbase.utiltiy.graph.PerfectMatchingChecker;
import ro.appbase.utiltiy.randomiser.HRGenerator;

import java.util.Arrays;

/**
 * Class RandomNamesStartPoint
 *
 * Source describing optional requirements 1 and 3
 *
 * [OPTIONAL]
 *
 * @author Loghin Vlad
 */
public class RandomNamesStartPoint {
    public static void main(String[] args) {

        ///THIRD PARTY LIBRARY (INSIDE HR GENERATOR) GENERATING FAKE NAMES
        HRGenerator test = new HRGenerator.HRBuilder()
                .withHospitalCount(100)
                .withResidentCount(100)
                .withHospitalStaffBehaviour(HRGenerator.HospitalParameters.RATES_ALL)
                .withMaxHospitalCapacity(10)
                .withResidentBehaviour(HRGenerator.ResidentParameters.RATES_ALL)
                .doubleCheckForUnwantedHospitals(true)
                .doubleCheckForUnwantedResidents(true)
                .build();

        System.out.println(Arrays.toString(test.getResidents()));
        System.out.println(Arrays.toString(test.getHospitals()));

        for(Resident r : test.getResidents())
            System.out.println(r);

        for(Hospital h : test.getHospitals())
            System.out.println(h);

        for(Resident r : test.getResidents())
            System.out.println(r.getName() + " " + r.getPreferences().size() + r.getPreferences().toString());

        System.out.println("\n");

        for(Hospital h : test.getHospitals())
            System.out.println(h.getName() + " " + h.getPreferences().size() + h.getPreferences().toString());

        Problem p = new Problem.Builder()
                .withHospitals(test.getHospitals())
                .withResidents(test.getResidents())
                .withAlgorithm(new GaleShapely())
                .build();

        System.out.println(p);

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
