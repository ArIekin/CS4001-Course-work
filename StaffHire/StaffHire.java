
public class StaffHire {
    
    private int vacancyNumber;                                                      //shows the Staff's vacancy number
    private String designation;                                                     //shows the Staff's designation
    private String jobType;                                                         //shows the Staff's job type
    private String staffName;                                                       //shows the Staff's name
    private String joiningDate;                                                     //shows the Staff's joining date
    private String qualification;                                                   //shows the Staff's qualification
    private String appointedBy;                                                     //shows who the Staff has been appointed by
    private boolean joined;                                                         //shows if a Staff has joined
    
    // constructor of the StaffHire class taking user input, specifying the data types needed
    public StaffHire(int vacancyNumber, String designation, String jobType, String staffName, String joiningDate, String qualification, String appointedBy, boolean joined) {
        this.vacancyNumber = vacancyNumber;                                         // sets vacancyNumber to user input
        this.designation = designation;                                             //sets designation to user input
        this.jobType = jobType;                                                     //sets jobType to user input
        this.staffName = staffName;                                                 //sets staffName to user input
        this.joiningDate = joiningDate;                                             //sets joiningDate to user input
        this.qualification = qualification;                                         //sets qualification to user input
        this.appointedBy = appointedBy;                                             //sets appointedby to user input
        this.joined = joined;                                                       //sets joined to user input
    }
    
    public void setVacancyNumber(int vacancyNumber) {                               //vacancyNumber setter
        this.vacancyNumber = vacancyNumber;                                         //changes vacancyNumber to user input
        System.out.println("Vacancy number has been changed to: " + vacancyNumber); //displays the change in the terminal
    }
    
    public int getVacancyNumber() {                                                 //vacancyNumber getter
        return vacancyNumber;                                                       //returns the value of vacancyNumber
    }
    
    public void setDesignation(String designation) {                                //designation setter
        this.designation = designation;                                             //changes designation to user input
        System.out.println("Designation has been changed to: " + designation);      //displays the change in the terminal
    }
    
    public String getDesignation() {                                                //designation getter
        return designation;                                                         //returns the value of designation
    }
    
    public void setJobType(String jobType) {                                        //jobType setter
        this.jobType = jobType;                                                     //changes jobType to user input
        System.out.println("Job type has been changed to: " + jobType);             //displays the change in the terminal
    }
    
    public String getJobType() {                                                    //jobType getter
        return jobType;                                                             //returns the value of jobType
    }
    
    public void setStaffName(String staffName) {                                    //staffName setter
        this.staffName = staffName;                                                 //changes staffName to user input
        System.out.println("Staff name has been changed to: " + staffName);         //displays the change in the terminal
    }
    
    public String getStaffName() {                                                  //staffName getter
        return staffName;                                                           //displays staffName
    }
    
    public void setJoiningDate(String joiningDate) {                                //joiningDate setter
        this.joiningDate = joiningDate;                                             //changes joiningDate to user input
        System.out.println("Join date has been changed to: " + joiningDate);        //displays the change in the terminal
    }
    
    public String getJoiningDate() {                                                //joiningDate getter
        return joiningDate;                                                         //returns the value of joiningDate
    }
    
    public void setQualification(String qualification) {                            //qualification setter
        this.qualification = qualification;                                         //changes qualification to user input
        System.out.println("Qualification has been changed to: " + qualification);  //displays the change in the terminal
    }
    
    public String getQualification() {                                              //qualification getter
        return qualification;                                                       //returns the value of qualification
    }
    
    public void setAppointedBy(String appointedBy) {                                //appointedBy setter
        this.appointedBy = appointedBy;                                             //changes appointedBy to user input
        System.out.println("Appointed by has been changed to: " + appointedBy);     //displays the change in the terminal
    }
    
    public String getAppointedBy() {                                                //appointedBy getter
        return appointedBy;                                                         //returns the value of appointedBy
    }
    
    public void setJoined(boolean joined) {                                         //joined boolean setter
        this.joined = joined;                                                       //changes joined to user input
        System.out.println("Joined has been set to: " + joined);                    //displays the change in the terminal
    }
    
    public boolean getJoined() {                                                    //joined boolean getter
        return joined;                                                              //returns the value of joined
    }
    

    public void display() {
        System.out.println("Vacancy Number: " + vacancyNumber);     // displays the vacancy number in the terminal
        System.out.println("Designation: " + designation);          // displays the designation in the terminal
        System.out.println("Job Type: " + jobType);                 // displays job type in the terminal
        System.out.println("Staff Name: " + staffName);             // displays staff name in the terminal
        System.out.println("Joining Date: " + joiningDate);         // displays joining date in the terminal
        System.out.println("Qualification: " + qualification);      // displays qualification in the terminal
        System.out.println("Appointed By: " + appointedBy);         // displays appointed by in the terminal
        System.out.println("Joined: " + (joined ? "Yes" : "No"));   // displays the boolean in the terminal
    }
}