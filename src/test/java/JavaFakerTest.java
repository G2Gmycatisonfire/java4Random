import com.github.javafaker.Faker;
import ro.appbase.object.Hospital;
import ro.appbase.object.Resident;
import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;

import java.util.Arrays;

public class JavaFakerTest {
    public static void main(String[] args) {
        HRGenerator test = new HRGenerator.HRBuilder()
                .withHospitalCount(100)
                .withResidentCount(100)
                .withHospitalStaffBehaviour(HRGenerator.HospitalParameters.REGULAR_STAFF)
                .withMaxHospitalCapacity(10)
                .withResidentBehaviour(HRGenerator.ResidentParameters.REGULAR)
                .build();

        System.out.println(Arrays.toString(test.getResidents()));
        System.out.println(Arrays.toString(test.getHospitals()));

        for(Resident r : test.getResidents()){
            System.out.println(r);
        }

        for(Hospital h : test.getHospitals())
            System.out.println(h);

        for(Resident r : test.getResidents()){
            System.out.println(r.getName() + " " + r.getPreferences().size() + r.getPreferences().toString());
        }

        System.out.println("\n");

        for(Hospital h : test.getHospitals()){
            System.out.println(h.getName() + " " + h.getPreferences().size() + h.getPreferences().toString());
        }

        Problem p = new Problem.Builder()
                .withHospitals(test.getHospitals())
                .withResidents(test.getResidents())
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
    }
}
