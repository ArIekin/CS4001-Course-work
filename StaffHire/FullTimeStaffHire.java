//FullTimeStaffHire class which is the subclass of StaffHire it is used to distinguish a full time staff member
public class FullTimeStaffHire extends StaffHire {  //extends StaffHire indiciates that this is a subclass
    
    private double salary;                          //stores the Staff's salary
    private int weeklyFractionalHours;              //stores the Staff's weekly fractional hours
    
    //Constructor of the FullTimeStaffHire class taking parameters from the StaffHire class and its own two parameters
    public FullTimeStaffHire(int vacancyNumber, String designation, String jobType, String staffName, String joiningDate, String qualification, String appointedBy, boolean joined,double salary, int weeklyFractionalHours) {

        super(vacancyNumber, designation, jobType, staffName, joiningDate, qualification, appointedBy, joined); //call to the parent class
        this.salary = salary;                                                                                   //sets salary to user input
        this.weeklyFractionalHours = weeklyFractionalHours;                                                     //sets the weekly fractional hours to user input
    }
    
    // Getter and setter methods
    public double getSalary() {
        return salary; //returns the current value of Salary
    }
    
    //Set salary method - only works if staff has joined
    public void setSalary(double newSalary) {
        if (getJoined()) { //getJoined checks if staff has joined
            this.salary = newSalary; //sets to new salary after the check
            System.out.println("Salary has been changed to: " + newSalary); //display information
        } else {
            System.out.println("Cannot set salary as no staff is appointed yet."); //shows error if staff hasn't joined
        }
    }
    
    public int getWeeklyFractionalHours() {
        return weeklyFractionalHours; //returns current value for weeklyFractionalHours
    }
    
    public void setWeeklyFractionalHours(int newWeeklyFractionalHours) {
        this.weeklyFractionalHours = newWeeklyFractionalHours;
        System.out.println("Weekly fractional hours has been changed to: " + weeklyFractionalHours); //sets a new value for the weeklyFractionalHours attribute
    }
    
    //Override display method to include salary and weekly hours
    @Override
    public void display() {
        // Call parent class display method first
        super.display();
        
        // Then display this class's attributes
        if (getJoined()) {
            System.out.println("Salary: " + salary);
            System.out.println("Weekly Fractional Hours: " + weeklyFractionalHours);
        }
    }
}