import com.github.javafaker.Faker;
import org.yaml.snakeyaml.tokens.BlockEndToken;
import ro.appbase.object.*;

import javax.xml.bind.annotation.XmlType;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HRGenerator {

    public enum ResidentParameters{
        PARANOID,
        RESPONSIBLE,
        ATTENTIVE,
        REGULAR,
        IRRESPONSIBLE,
        DRUNK,
        RANDOM
    }

    public enum HospitalParameters{
        IN_CRISIS,
        UNDERSTAFFED,
        COMPETENT_STAFF,
        REGULAR_STAFF,
        IMPATIENT_STAFF,
        INCOMPETENT_STAFF,
        CARELESS_STAFF,
        RANDOM
    }

    private Faker nameGenerator;

    private int residentCount;
    private int hospitalCount;
    private int maxHospitalCapacity;
    private ResidentParameters residentBehaviour;
    private HospitalParameters hospitalBehaviour;

    private Resident[] residents;
    private Hospital[] hospitals;

    public static class HRBuilder{
        public final int DEFAULT_RESIDENT_COUNT = 100;
        public final int DEFAULT_HOSPITAL_COUNT = 50;
        public final int DEFAULT_MAX_HOSPITAL_CAPACITY = 3;
        public final ResidentParameters DEFAULT_RESIDENT_BEHAVIOUR = ResidentParameters.REGULAR;
        public final HospitalParameters DEFAULT_HOSPITAL_BEHAVIOUR = HospitalParameters.REGULAR_STAFF;

        private int residentCount = DEFAULT_RESIDENT_COUNT;
        private int hospitalCount = DEFAULT_HOSPITAL_COUNT;
        private int maxHospitalCapacity = DEFAULT_MAX_HOSPITAL_CAPACITY;
        private ResidentParameters residentBehaviour = DEFAULT_RESIDENT_BEHAVIOUR;
        private HospitalParameters hospitalBehaviour = DEFAULT_HOSPITAL_BEHAVIOUR;

        public HRBuilder withResidentCount(int residentCount){
            this.residentCount = residentCount;
            return this;
        }

        public HRBuilder withHospitalCount(int hospitalCount){
            this.hospitalCount = hospitalCount;
            return this;
        }

        public HRBuilder withMaxHospitalCapacity(int maxHospitalCapacity){
            this.maxHospitalCapacity = maxHospitalCapacity;
            return this;
        }

        public HRBuilder withResidentBehaviour(ResidentParameters residentBehaviour){
            this.residentBehaviour = residentBehaviour;
            return this;
        }

        public HRBuilder withHospitalStaffBehaviour(HospitalParameters hospitalStaffBehaviour){
            this.hospitalBehaviour = hospitalStaffBehaviour;
            return this;
        }

        public HRGenerator build(){
            HRGenerator generated = new HRGenerator();

            generated.hospitalBehaviour = this.hospitalBehaviour;
            generated.hospitalCount = this.hospitalCount;
            generated.maxHospitalCapacity = this.maxHospitalCapacity;
            generated.residentBehaviour = this.residentBehaviour;
            generated.residentCount = this.residentCount;

            generated.nameGenerator = new Faker();

            generated.generateResidents();
            generated.generateHospitals();
            generated.assign();

            return generated;
        }
    }

    private void assignResidents(){
        Set<Hospital> preferences = new HashSet<>();
        int which;
        for(Resident r : this.residents){
            preferences.clear();
            int howMany = Math.max(Math.min(this.getResidentChoiceCount(), this.hospitals.length),1);
            if(howMany < 0)
                howMany = Math.min(5, this.hospitalCount);
            Hospital[] prefs = new Hospital[howMany];
            for(int i = 0; i < howMany; i++){
                which = (int)(Math.random() * this.hospitals.length);
                while(preferences.contains(this.hospitals[which])){
                    which = (++which)%this.hospitals.length;
                }
                preferences.add(this.hospitals[which]);
            }
            preferences.toArray(prefs);
            r.setPreferences(prefs);
        }
    }

    private void assignHospitals(){
        Set<Resident> preferences = new HashSet<>();
        int which;
        for(Hospital h : this.hospitals){
            preferences.clear();
            int howMany = Math.max(Math.min((int)(Math.random() * this.maxHospitalCapacity) + this.maxHospitalCapacity, this.residentCount), 1);
            if(howMany < 0)
                howMany = Math.min(5, this.residentCount);
            Resident[] prefs = new Resident[howMany];

            for(int i = 0; i < howMany; i++){
                which = (int)(Math.random() * this.residents.length);
                while(preferences.contains(this.residents[which])){
                    which = (++which)%this.residents.length;
                }

                preferences.add(this.residents[which]);
            }
            preferences.toArray(prefs);
            h.setPreferences(prefs);
        }
    }

    private void assign(){
        this.assignResidents();
        this.assignHospitals();
    }

    private void generateHospitals(){
        this.hospitals = new Hospital[this.hospitalCount];
        for(int i = 0; i < this.residentCount; i++)
            this.hospitals[i] = new Hospital(this.nameGenerator.medical().hospitalName(), this.getCapacity());
    }

    private int getResidentChoiceCount(){
        switch(this.residentBehaviour){
            case RESPONSIBLE:   return (int)(Math.random() * this.hospitals.length) - this.hospitals.length/8;
            case ATTENTIVE:     return (int)(Math.random() * this.hospitals.length) - this.hospitals.length/4;
            case PARANOID:      return (int)(Math.random() * this.hospitals.length) - this.hospitals.length/3;
            case REGULAR:       return (int)(Math.random() * this.hospitals.length) - this.hospitals.length/2;
            case IRRESPONSIBLE: return Math.max(4, this.hospitals.length);
            case DRUNK:         return (int)(Math.random() * 2) + 1;
            default:            return (int)(Math.random() * this.hospitals.length) + 1;
        }
    }

    private int getCapacity(){
        switch(this.hospitalBehaviour){
            case IN_CRISIS:         return this.maxHospitalCapacity - (int)(Math.random() * (this.maxHospitalCapacity/20));
            case UNDERSTAFFED:      return this.maxHospitalCapacity - (int)(Math.random() * (this.maxHospitalCapacity/10));
            case REGULAR_STAFF:     return (int)(Math.random() * (this.maxHospitalCapacity/2)) + this.maxHospitalCapacity/4;
            case COMPETENT_STAFF:   return (int)(Math.random() * (this.maxHospitalCapacity/2)) - 1;
            case IMPATIENT_STAFF:   return (int)(Math.random() * (this.maxHospitalCapacity/8)) + this.maxHospitalCapacity/2;
            case CARELESS_STAFF:    return (int)(Math.random() * (this.maxHospitalCapacity/3)) + this.maxHospitalCapacity/8;
            case INCOMPETENT_STAFF: return (int)(Math.random() * (this.maxHospitalCapacity/2)) + this.maxHospitalCapacity/2;
            default:                return (int)(Math.random()*this.maxHospitalCapacity);
        }
    }

    private void generateResidents(){
        this.residents = new Resident[this.residentCount];
        for(int i = 0; i < this.residentCount; i++)
            this.residents[i] = new Resident(this.nameGenerator.name().fullName());
    }

    private HRGenerator(){

    }

    public Resident[] getResidents(){
        return this.residents;
    }

    public Hospital[] getHospitals(){
        return this.hospitals;
    }
}

