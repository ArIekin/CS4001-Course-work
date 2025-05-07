///PartTimeStaffHire class which is the subclass of StaffHire it is used to distinguish a part time staff member
public class PartTimeStaffHire extends StaffHire {
    
    private int workingHours;//Stores the staff member's working hours
    private double wagesPerHour;//Stores the staff member's wager
    private String shifts;//Stores the staff member's shifts
    private boolean terminated;//Stores if staff is terminated
    
    
    //Constructor for PartTimeStaffHire
    public PartTimeStaffHire(int vacancyNumber, String designation, String jobType, String staffName, String joiningDate, String qualification, String appointedBy, boolean joined,int workingHour, double wagesPerHour, String shifts) {
        // Call superclass constructor
        super(vacancyNumber, designation, jobType, staffName, joiningDate, qualification, appointedBy, joined);
        this.workingHours = workingHours; // sets to user input
        this.wagesPerHour = wagesPerHour; // sets to user input
        this.shifts = shifts; // sets to user input
        this.terminated = false; // Initially not terminated
    }
    
    // Getter and setter methods
    public int getWorkingHour() {
        return workingHours;
    }
    
    public void setWorkingHour(int workingHours) {
        this.workingHours = workingHours;
        System.out.println("Working hours have been changed to: " + workingHours);
    }
    
    public double getWagesPerHour() {
        return wagesPerHour;
    }
    
    public void setWagesPerHour(double wagesPerHour) {
        this.wagesPerHour = wagesPerHour;
        System.out.println("Wages per hour have been changed to: " + wagesPerHour);
    }
    
    public String getShifts() {
        return shifts;
    }
    
  
    //Set shifts method - only works if staff has joined and hasn't been terminated
    public void setShifts(String newShifts) {
        if (getJoined() && !terminated) { //if joined = true && terminated = false
            this.shifts = newShifts;
            System.out.println("Shifts have been changed to: " + newShifts);
        } else {
            System.out.println("Cannot change shifts as staff is not appointed or has been terminated.");
        }
    }
    
    public boolean getTerminated() {
        return terminated;
    }
    
    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
        System.out.println("Terminated has been changed to: " + terminated);
    }
    
    //This checks if the staff is terminated, if yes then no action is taken aside from an error message, otherwise it blanks all the information about the staff
    public void terminateStaff() {
        if (terminated) {
            System.out.println("Staff is already terminated.");
        } else {
            setStaffName("");
            setJoiningDate("");
            setQualification("");
            setAppointedBy("");
            setJoined(false);
            this.terminated = true;
            System.out.println("Staff has been terminated.");
        }
    }
    

    //Override display method to include part-time specific attributes
    @Override
    public void display() {
        // Call parent class display method first
        super.display();
        
        // Then display this class's attributes
        if (getJoined() && !terminated) {
            System.out.println("Working Hours: " + workingHours);
            System.out.println("Wages Per Hour: " + wagesPerHour);
            System.out.println("Shifts: " + shifts);
            System.out.println("Income Per Day: " + (workingHours * wagesPerHour));
        }
        System.out.println("Terminated: " + (terminated ? "Yes" : "No"));
    }
}