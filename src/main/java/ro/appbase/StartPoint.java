package ro.appbase;

import ro.appbase.object.Hospital;
import ro.appbase.object.Resident;
import ro.appbase.utiltiy.concept.Problem;
import ro.appbase.utiltiy.concept.Solution;

import java.util.Objects;
import java.util.function.Predicate;
public class StartPoint {
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

        residents[0].setPreferences(hospitals[0], hospitals[1], hospitals[2]);
        residents[1].setPreferences(hospitals[0], hospitals[1], hospitals[2]);
        residents[2].setPreferences(hospitals[0], hospitals[1]);
        residents[3].setPreferences(hospitals[0], hospitals[2]);

        hospitals[0].setPreferences(residents[3], residents[0], residents[1], residents[2]);
        hospitals[1].setPreferences(residents[0], residents[2], residents[1]);
        hospitals[2].setPreferences(residents[0], residents[1], residents[3]);

        Problem p = new Problem.Builder()
                .withHospitals(hospitals)
                .withResidents(residents)
                .build();

        //p.printPreferences();

        Predicate<Resident> findsAcceptableH0 = r-> r.getPreferences()
                .containsValue(hospitals[2]);
        Predicate<Resident> findsAcceptableH2 = r-> r.getPreferences()
                .containsValue(hospitals[0]);
        p.getResidents().stream()
            .filter(findsAcceptableH0.and(findsAcceptableH2))
            .forEach(System.out::println);


        p.getHospitals()
                .stream()
                .filter(h -> Objects.requireNonNull(h.getPreferences()
                        .entrySet()
                        .stream()
                        .findFirst()
                        .orElse(null))
                        .getValue()
                        .equals(residents[0]))
                .forEach(System.out::println);

        System.out.println(p);
        System.out.println(p.getS());
        System.out.println(p.getT());

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
