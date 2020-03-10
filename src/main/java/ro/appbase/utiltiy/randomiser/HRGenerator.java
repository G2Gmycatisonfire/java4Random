package ro.appbase.utiltiy.randomiser;

import com.github.javafaker.Faker;
import ro.appbase.object.Hospital;
import ro.appbase.object.Resident;

import java.util.HashSet;
import java.util.Set;

/**
 * HRGenerator Class
 *
 * [OPTIONAL]
 *
 * @author Loghin Vlad
 */
public class HRGenerator {

    /**
     * Enum ResidentParameters
     *
     * Describes resident assignment behaviour options
     */
    public enum ResidentParameters{
        RATES_ALL,
        PARANOID,
        RESPONSIBLE,
        ATTENTIVE,
        REGULAR,
        IRRESPONSIBLE,
        DRUNK,
        RANDOM
    }

    /**
     * Enum ResidentParameters
     *
     * Describes hospital assignment behaviour options
     */
    public enum HospitalParameters{
        RATES_ALL,
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

    /**
     * Nested Class HRBuilder
     *
     * [completely optional] [Builder Pattern]
     *
     * @author Loghin Vlad
     */
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

        private boolean doubleCheckResidents = false;
        private boolean doubleCheckHospitals = false;

        /**
         * assigner for number of generated residents
         * @param residentCount number of residents
         * @return pointer to this Builder object
         */
        public HRBuilder withResidentCount(int residentCount){
            this.residentCount = residentCount;
            return this;
        }

        /**
         * assigner for number of generated hospitals
         * @param hospitalCount number of hospitals
         * @return pointer to this Builder object
         */
        public HRBuilder withHospitalCount(int hospitalCount){
            this.hospitalCount = hospitalCount;
            return this;
        }

        /**
         * assigner for hospital max capacity
         * @param maxHospitalCapacity capacity of hospitals
         * @return pointer to this Builder object
         */
        public HRBuilder withMaxHospitalCapacity(int maxHospitalCapacity){
            this.maxHospitalCapacity = maxHospitalCapacity;
            return this;
        }

        /**
         * assigner for resident assignment behaviour
         * @param residentBehaviour enum value of behaviour
         * @return pointer to this Builder object
         */
        public HRBuilder withResidentBehaviour(ResidentParameters residentBehaviour){
            this.residentBehaviour = residentBehaviour;
            return this;
        }

        /**
         * assigner for hospital assignment behaviour
         * @param hospitalStaffBehaviour enum value of behaviour
         * @return pointer to this Builder object
         */
        public HRBuilder withHospitalStaffBehaviour(HospitalParameters hospitalStaffBehaviour){
            this.hospitalBehaviour = hospitalStaffBehaviour;
            return this;
        }

        /**
         * assigner for double checker option for residents
         *
         * Checks whether any residents have no hospitals matched
         *
         * @param doubleCheck boolean telling whether to double check or not
         * @return pointer to this Builder object
         */
        public HRBuilder doubleCheckForUnwantedResidents(boolean doubleCheck){
            this.doubleCheckResidents = doubleCheck;
            return this;
        }

        /**
         * assigner for double checker option for hospitals
         *
         * Checks whether any hospitals have no residents matched
         *
         * @param doubleCheck boolean telling whether to double check or not
         * @return pointer to this Builder object
         */
        public HRBuilder doubleCheckForUnwantedHospitals(boolean doubleCheck){
            this.doubleCheckHospitals = doubleCheck;
            return this;
        }

        /**
         * build Method, called last, returns built object
         * @return pointer to the built HRGenerator object
         */
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

            if(this.doubleCheckResidents)
                generated.checkForUnassignedResidents();
            if(this.doubleCheckHospitals)
                generated.checkForUnassignedHospitals();

            return generated;
        }
    }

    /**
     * Method assigning unassigned residents
     * @throws NullPointerException if any hospitals do not exist (statistically unimportant)
     */
    private void checkForUnassignedResidents() throws NullPointerException{
        for(Resident r : this.residents){
            boolean hasHospital = false;
            int minimumNumberOfResidents = Integer.MAX_VALUE;
            Hospital hospitalWithMinimumResidents = null;
            for(Hospital h : this.hospitals){
                if(h.getPreferences().size() < minimumNumberOfResidents){
                    minimumNumberOfResidents = h.getPreferences().size();
                    hospitalWithMinimumResidents = h;
                }

                if(h.getPreferences().containsValue(r))
                    hasHospital = true;
            }

            if(!hasHospital){
                if(hospitalWithMinimumResidents == null)
                    throw new NullPointerException("Cannot get here");
                hospitalWithMinimumResidents.addResidentToPreferences(r);
            }
        }
    }

    /**
     * Method assigning unassigned hospitals
     * @throws NullPointerException if any residents do not exist (statistically unimportant)
     */
    private void checkForUnassignedHospitals() throws NullPointerException{
        for(Hospital h : this.hospitals){
            boolean hasResident = false;
            int minimumNumberOfHospitals = Integer.MAX_VALUE;
            Resident residentWithMinimumHospitals = null;
            for(Resident r : this.residents){
                if(r.getPreferences().size() < minimumNumberOfHospitals){
                    minimumNumberOfHospitals = r.getPreferences().size();
                    residentWithMinimumHospitals = r;
                }

                if(r.getPreferences().containsValue(h))
                    hasResident = true;
            }

            if(!hasResident){
                if(residentWithMinimumHospitals == null)
                    throw new NullPointerException("Cannot get here either");
                residentWithMinimumHospitals.addHospitalToPreferences(h);
            }
        }
    }

    /**
     * method called to assign residents to hospitals' preferences
     */
    private void assignResidents(){
        Set<Hospital> preferences = new HashSet<>();
        int which;
        for(Resident r : this.residents){
            int l = 0;
            preferences.clear();
            int howMany = Math.max(Math.min(this.getResidentChoiceCount(), this.hospitals.length),1);
            Hospital[] prefs = new Hospital[howMany];
            for(int i = 0; i < howMany; i++){
                which = (int)(Math.random() * this.hospitals.length);
                while(preferences.contains(this.hospitals[which]))
                    which = (int)(Math.random()*this.hospitals.length);
                preferences.add(this.hospitals[which]);
                prefs[l++] = this.hospitals[which];
            }
            r.setPreferences(prefs);
        }
    }

    /**
     * method called to assign hospitals to residents' preferences
     */
    private void assignHospitals(){
        Set<Resident> preferences = new HashSet<>();
        int which;
        for(Hospital h : this.hospitals){
            int l = 0;
            preferences.clear();
            int howMany = Math.max(Math.min((int)(Math.random() * this.maxHospitalCapacity) + this.maxHospitalCapacity, this.residentCount), 1);

            if(this.hospitalBehaviour == HospitalParameters.RATES_ALL)
                howMany = this.residentCount;

            if(howMany < 0)
                howMany = Math.min(5, this.residentCount);
            Resident[] prefs = new Resident[howMany];

            for(int i = 0; i < howMany; i++){
                which = (int)(Math.random() * this.residents.length);
                while(preferences.contains(this.residents[which]))
                    which = (int)(Math.random() * this.residents.length);
                preferences.add(this.residents[which]);
                prefs[l++] = this.residents[which];
            }
            h.setPreferences(prefs);
        }
    }

    /**
     * method called to assign both residents to hospitals and hospitals to residents
     */
    private void assign(){
        this.assignResidents();
        this.assignHospitals();
    }

    /**
     * method called to generate hospitals with random names
     */
    private void generateHospitals(){
        this.hospitals = new Hospital[this.hospitalCount];
        for(int i = 0; i < this.residentCount; i++)
            this.hospitals[i] = new Hospital(this.nameGenerator.medical().hospitalName(), this.getCapacity());
    }

    /**
     * method called to get a number of choices in preferences for residents based on behaviour
     * @return value of number of choices
     */
    private int getResidentChoiceCount(){
        switch(this.residentBehaviour){
            case RATES_ALL:     return this.hospitals.length;
            case RESPONSIBLE:   return (int)(Math.random() * this.hospitals.length) - this.hospitals.length/8;
            case ATTENTIVE:     return (int)(Math.random() * this.hospitals.length) - this.hospitals.length/4;
            case PARANOID:      return (int)(Math.random() * this.hospitals.length) - this.hospitals.length/3;
            case REGULAR:       return (int)(Math.random() * this.hospitals.length) - this.hospitals.length/2;
            case IRRESPONSIBLE: return Math.max(4, this.hospitals.length);
            case DRUNK:         return (int)(Math.random() * 2) + 1;
            default:            return (int)(Math.random() * this.hospitals.length) + 1;
        }
    }

    /**
     * method called to get a number of choices in preferences for hospitals based on behaviour
     * @return value of number of choices
     */
    private int getCapacity(){
        switch(this.hospitalBehaviour){
            case RATES_ALL:         return this.hospitalCount / this.residentCount + (int)(Math.random() * 5);
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

    /**
     * method called to generate residents with random names
     */
    private void generateResidents(){
        this.residents = new Resident[this.residentCount];
        for(int i = 0; i < this.residentCount; i++)
            this.residents[i] = new Resident(this.nameGenerator.name().fullName());
    }

    /**
     * Constructor, private, called by builder
     */
    private HRGenerator(){

    }

    /**
     * Getter for residents
     * @return pointer to an array of Residents
     */
    public Resident[] getResidents(){
        return this.residents;
    }

    /**
     * Getter for hospitals
     * @return pointer to an array of Hospitals
     */
    public Hospital[] getHospitals(){
        return this.hospitals;
    }
}

