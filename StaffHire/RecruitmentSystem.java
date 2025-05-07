// Import necessary libraries for GUI components, event handling, and data structures
import java.awt.*;              // For GUI components like Container, GridLayout, etc.
import java.awt.event.*;        // For event handling (ActionListener interface)
import javax.swing.*;           // For Swing GUI components (JFrame, JButton, etc.)
import javax.swing.table.DefaultTableModel;  // For creating and managing table data
import java.util.ArrayList;     // For dynamic array implementation (staffList)
import java.util.List;          // For List interface
import java.io.ByteArrayOutputStream;  // For capturing output streams
import java.io.PrintStream;     // For redirecting System.out
import javax.swing.text.*;      // For document filtering (input validation)
import java.util.regex.Pattern; // For regular expression pattern matching
import java.text.SimpleDateFormat;  // For date formatting and parsing
import java.text.ParseException;    // For handling date parsing errors

/**
 * Main class for the Recruitment System GUI
 * This class creates and manages the graphical user interface for the staff recruitment system
 * It handles user interactions, data storage, and operations on staff records
 */
public class RecruitmentSystem implements ActionListener {  // ActionListener interface enables button event handling
    // Main GUI components
    private JFrame frame;               // Main application window
    private JPanel mainPanel;           // Main container panel

    // Buttons for main menu attributes - each button triggers a specific action
    private JButton addFullTimeStaffButton;    // Button to add a full-time staff member
    private JButton addPartTimeStaffButton;    // Button to add a part-time staff member
    private JButton setSalaryButton;           // Button to update salary of full-time staff
    private JButton setShiftsButton;           // Button to update shifts of part-time staff
    private JButton terminateStaffButton;      // Button to terminate part-time staff
    private JButton displayButton;             // Button to display staff details
    private JButton clearButton;               // Button to clear input fields

    // Form fields attributes - input fields for staff information
    private JTextField vacancyNumberField;          // For vacancy number input
    private JTextField designationField;            // For job designation input
    private JTextField jobTypeField;                // For job type input
    private JTextField staffNameField;              // For staff name input
    private JTextField joiningDateField;            // For joining date input (dd/mm/yyyy)
    private JTextField qualificationField;          // For qualification input
    private JTextField appointedByField;            // For appointed by input
    private JTextField salaryField;                 // For salary input (full-time only)
    private JTextField weeklyFractionalHoursField;  // For weekly hours input (full-time only)
    private JTextField workingHoursField;           // For working hours input (part-time only)
    private JTextField wagesPerHourField;           // For hourly wage input (part-time only)
    private JTextField shiftsField;                 // For shifts input (part-time only)
    private JTextField displayNumberField;          // For entering index to display staff

    // Checkbox attribute for joined status
    private JCheckBox joinedCheckBox;               // Indicates if staff has joined

    // Staff list to store all staff objects (both full-time and part-time)
    private List<StaffHire> staffList = new ArrayList<>();  // ArrayList for dynamic storage

    // Components for displaying staff in a table
    private JTable staffTable;           // Table to display staff information
    private DefaultTableModel tableModel; // Model to handle table data

    /**
     * Constructor - sets up the main GUI
     * Initializes all components and arranges them in the layout
     */
    public RecruitmentSystem() {
        // Create main frame (window) with title
        frame = new JFrame("Recruitment System");
        frame.setSize(1000, 700);                      // Set window size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close application when window is closed
        frame.setLocationRelativeTo(null);             // Center window on screen

        // Create main panel with border layout and spacing
        mainPanel = new JPanel(new BorderLayout(10, 10));  // 10px spacing between components
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Add padding
        mainPanel.setBackground(Color.decode("#F2EFE7"));   // Set background color

        // Add title panel at the top
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.decode("#F2EFE7"));  // Match background color
        JLabel titleLabel = new JLabel("Staff Recruitment System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));  // Set font for title
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);  // Add to top of main panel

        // Create and add all panels
        createFormPanel();     // Create panel with input fields
        createButtonPanel();   // Create panel with buttons
        createTablePanel();    // Create panel with staff table

        // Add main panel to frame and display
        frame.add(mainPanel);
        frame.setVisible(true);  // Make the window visible
    }

    /**
     * String Document Filter class
     * Ensures text fields only accept alphabetic characters and spaces
     * Used for name, designation, job type, etc.
     */
    class StringDocumentFilter extends DocumentFilter {
        // Regular expression pattern allowing alphabetic characters and spaces
        // The * allows empty strings (for clearing fields)
        private final Pattern pattern = Pattern.compile("[a-zA-Z\\s]*");

        /**
         * Controls insertion of text into the document
         * Only allows text matching the pattern
         */
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) 
        throws BadLocationException {
            // Check if text is null or matches the pattern
            if (text == null || pattern.matcher(text).matches()) {
                super.insertString(fb, offset, text, attrs);  // Allow the insertion
            } else {
                Toolkit.getDefaultToolkit().beep();  // Make error sound if invalid input
            }
        }

        /**
         * Controls replacement of text in the document
         * Only allows text matching the pattern
         */
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
        throws BadLocationException {
            // Check if text is null or matches the pattern
            if (text == null || pattern.matcher(text).matches()) {
                super.replace(fb, offset, length, text, attrs);  // Allow the replacement
            } else {
                Toolkit.getDefaultToolkit().beep();  // Make error sound if invalid input
            }
        }
    }

    /**
     * Integer Document Filter class
     * Ensures text fields only accept whole numbers
     * Used for vacancy number, fractional hours, etc.
     */
    class IntegerDocumentFilter extends DocumentFilter {
        // Regular expression pattern allowing only digits
        private final Pattern pattern = Pattern.compile("\\d*");

        /**
         * Controls insertion of text into the document
         * Only allows text matching the pattern
         */
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) 
        throws BadLocationException {
            // Check if text is null or matches the pattern
            if (text == null || pattern.matcher(text).matches()) {
                super.insertString(fb, offset, text, attrs);  // Allow the insertion
            } else {
                Toolkit.getDefaultToolkit().beep();  // Make error sound if invalid input
            }
        }

        /**
         * Controls replacement of text in the document
         * Only allows text matching the pattern
         */
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
        throws BadLocationException {
            // Check if text is null or matches the pattern
            if (text == null || pattern.matcher(text).matches()) {
                super.replace(fb, offset, length, text, attrs);  // Allow the replacement
            } else {
                Toolkit.getDefaultToolkit().beep();  // Make error sound if invalid input
            }
        }
    }

    /**
     * Decimal Document Filter class
     * Ensures text fields only accept decimal numbers
     * Used for salary, wages per hour, etc.
     */
    class DecimalDocumentFilter extends DocumentFilter {
        // Regular expression pattern allowing digits and one decimal point
        private final Pattern pattern = Pattern.compile("\\d*\\.?\\d*");

        /**
         * Controls insertion of text into the document
         * Only allows text matching the pattern
         */
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) 
        throws BadLocationException {
            // Check if text is null or matches the pattern
            if (text == null || pattern.matcher(text).matches()) {
                super.insertString(fb, offset, text, attrs);  // Allow the insertion
            } else {
                Toolkit.getDefaultToolkit().beep();  // Make error sound if invalid input
            }
        }

        /**
         * Controls replacement of text in the document
         * Only allows text matching the pattern
         */
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
        throws BadLocationException {
            // Check if text is null or matches the pattern
            if (text == null || pattern.matcher(text).matches()) {
                super.replace(fb, offset, length, text, attrs);  // Allow the replacement
            } else {
                Toolkit.getDefaultToolkit().beep();  // Make error sound if invalid input
            }
        }
    }

    /**
     * Date Document Filter class
     * Ensures date field only accepts valid dates in dd/mm/yyyy format
     * Used for joining date
     */
    class DateDocumentFilter extends DocumentFilter {
        // Regular expression for partial date format (dd/mm/yyyy)
        private final Pattern datePattern = Pattern.compile("([0-9]{0,2}/)?([0-9]{0,2}/)?([0-9]{0,4})");
        private final int MAX_LENGTH = 10;  // Max length of date string (dd/mm/yyyy = 10 chars)

        /**
         * Controls insertion of text into the document
         * Only allows valid partial dates
         */
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) 
        throws BadLocationException {
            // Get current text and calculate resulting text after insertion
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String resultingText = new StringBuilder(currentText).insert(offset, text).toString();

            // Check if resulting text is valid partial date and within max length
            if (isValidPartialDate(resultingText) && resultingText.length() <= MAX_LENGTH) {
                super.insertString(fb, offset, text, attrs);  // Allow the insertion
            } else {
                Toolkit.getDefaultToolkit().beep();  // Make error sound if invalid input
            }
        }

        /**
         * Controls replacement of text in the document
         * Only allows valid partial dates
         */
        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
        throws BadLocationException {
            // Get current text and calculate resulting text after replacement
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String resultingText = new StringBuilder(currentText).replace(offset, offset + length, text).toString();

            // Check if resulting text is valid partial date and within max length
            if (isValidPartialDate(resultingText) && resultingText.length() <= MAX_LENGTH) {
                super.replace(fb, offset, length, text, attrs);  // Allow the replacement
            } else {
                Toolkit.getDefaultToolkit().beep();  // Make error sound if invalid input
            }
        }

        /**
         * Checks if text is a valid partial date
         * Allows progressive typing of a date
         */
        private boolean isValidPartialDate(String text) {
            // Empty string is valid (for clearing the field)
            if (text.isEmpty()) {
                return true;
            }

            // Check if text matches pattern for partial date
            if (!datePattern.matcher(text).matches()) {
                return false;
            }

            // If it's a complete date, validate it
            if (text.length() == MAX_LENGTH) {
                return isValidCompleteDate(text);
            }

            return true;  // Partial date is valid
        }

        /**
         * Checks if text is a valid complete date
         * Validates the actual date (day, month, year)
         */
        private boolean isValidCompleteDate(String dateStr) {
            // Create date formatter with strict validation
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);  // Strict date validation

            try {
                // Try to parse the date
                sdf.parse(dateStr);

                // Additional validation for day and month ranges
                String[] parts = dateStr.split("/");
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);

                // Check day and month ranges
                return day >= 1 && day <= 31 && month >= 1 && month <= 12;
            } catch (ParseException | NumberFormatException e) {
                return false;  // Invalid date
            }
        }
    }

    /**
     * Creates the form panel with all input fields
     * Sets up the layout and adds all text fields with appropriate filters
     */
    private void createFormPanel() {
        // Create panel with GridBagLayout for flexible component placement
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.decode("#F2EFE7"));  // Set background color
        formPanel.setBorder(BorderFactory.createTitledBorder("Staff Information"));  // Add border with title

        // GridBagConstraints control how components are placed in the grid
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);  // Padding around components (top, left, bottom, right)
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Components fill horizontal space

        // ----- Add labels - left column -----
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;  // Right-align labels
        formPanel.add(new JLabel("Vacancy Number:"), gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Designation:"), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Job Type:"), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Staff Name:"), gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Joining Date:"), gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Qualification:"), gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Appointed By:"), gbc);

        // ----- Add labels - right column -----
        gbc.gridx = 2; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;  // Right-align labels
        formPanel.add(new JLabel("Salary:"), gbc);

        gbc.gridx = 2; gbc.gridy = 1;
        formPanel.add(new JLabel("Weekly Fractional Hours:"), gbc);

        gbc.gridx = 2; gbc.gridy = 2;
        formPanel.add(new JLabel("Working Hours:"), gbc);

        gbc.gridx = 2; gbc.gridy = 3;
        formPanel.add(new JLabel("Wages Per Hour:"), gbc);

        gbc.gridx = 2; gbc.gridy = 4;
        formPanel.add(new JLabel("Shifts:"), gbc);

        gbc.gridx = 2; gbc.gridy = 5;
        formPanel.add(new JLabel("Display Number:"), gbc);

        gbc.gridx = 2; gbc.gridy = 6;
        formPanel.add(new JLabel("Joined:"), gbc);

        // ----- Text fields - left column -----
        gbc.anchor = GridBagConstraints.WEST;  // Left-align text fields
        gbc.ipadx = 100;  // Internal padding to make text fields wider

        // Vacancy Number field - accepts only integers
        gbc.gridx = 1; gbc.gridy = 0;
        vacancyNumberField = new JTextField(15);  // Width of 15 columns
        ((AbstractDocument) vacancyNumberField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        formPanel.add(vacancyNumberField, gbc);
        vacancyNumberField.setToolTipText("e.g., 123456");  // Tooltip with example

        // Designation field - accepts only letters and spaces
        gbc.gridx = 1; gbc.gridy = 1;
        designationField = new JTextField(15);
        ((AbstractDocument) designationField.getDocument()).setDocumentFilter(new StringDocumentFilter());
        formPanel.add(designationField, gbc);
        designationField.setToolTipText("e.g., Head lecturer");

        // Job Type field - accepts only letters and spaces
        gbc.gridx = 1; gbc.gridy = 2;
        jobTypeField = new JTextField(15);
        ((AbstractDocument) jobTypeField.getDocument()).setDocumentFilter(new StringDocumentFilter());
        formPanel.add(jobTypeField, gbc);
        jobTypeField.setToolTipText("e.g., Lecturer/Mentor");

        // Staff Name field - accepts only letters and spaces
        gbc.gridx = 1; gbc.gridy = 3;
        staffNameField = new JTextField(15);
        ((AbstractDocument) staffNameField.getDocument()).setDocumentFilter(new StringDocumentFilter());
        formPanel.add(staffNameField, gbc);
        staffNameField.setToolTipText("e.g., Lisa Rinna");

        // Joining Date field - accepts only valid dates
        gbc.gridx = 1; gbc.gridy = 4;
        joiningDateField = new JTextField(15);
        ((AbstractDocument) joiningDateField.getDocument()).setDocumentFilter(new DateDocumentFilter());
        formPanel.add(joiningDateField, gbc);
        joiningDateField.setToolTipText("dd/mm/yyyy");

        // Qualification field - accepts only letters and spaces
        gbc.gridx = 1; gbc.gridy = 5;
        qualificationField = new JTextField(15);
        ((AbstractDocument) qualificationField.getDocument()).setDocumentFilter(new StringDocumentFilter());
        formPanel.add(qualificationField, gbc);
        qualificationField.setToolTipText("e.g., Masters/PHD/Other");

        // Appointed By field - accepts only letters and spaces
        gbc.gridx = 1; gbc.gridy = 6;
        appointedByField = new JTextField(15);
        ((AbstractDocument) appointedByField.getDocument()).setDocumentFilter(new StringDocumentFilter());
        formPanel.add(appointedByField, gbc);
        appointedByField.setToolTipText("e.g., name of Head of School");

        // ----- Text fields - right column -----
        
        // Salary field - accepts only decimal numbers
        gbc.gridx = 3; gbc.gridy = 0;
        salaryField = new JTextField(15);
        ((AbstractDocument) salaryField.getDocument()).setDocumentFilter(new DecimalDocumentFilter());
        formPanel.add(salaryField, gbc);
        salaryField.setToolTipText("e.g., 25000.00");

        // Weekly Fractional Hours field - accepts only integers
        gbc.gridx = 3; gbc.gridy = 1;
        weeklyFractionalHoursField = new JTextField(15);
        ((AbstractDocument) weeklyFractionalHoursField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        formPanel.add(weeklyFractionalHoursField, gbc);
        weeklyFractionalHoursField.setToolTipText("e.g., 10");

        // Working Hours field - accepts only integers
        gbc.gridx = 3; gbc.gridy = 2;
        workingHoursField = new JTextField(15);
        ((AbstractDocument) workingHoursField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        formPanel.add(workingHoursField, gbc);
        workingHoursField.setToolTipText("e.g., 20");

        // Wages Per Hour field - accepts only decimal numbers
        gbc.gridx = 3; gbc.gridy = 3;
        wagesPerHourField = new JTextField(15);
        ((AbstractDocument) wagesPerHourField.getDocument()).setDocumentFilter(new DecimalDocumentFilter());
        formPanel.add(wagesPerHourField, gbc);
        wagesPerHourField.setToolTipText("e.g., 13.82");

        // Shifts field - accepts only letters and spaces
        gbc.gridx = 3; gbc.gridy = 4;
        shiftsField = new JTextField(15);
        ((AbstractDocument) shiftsField.getDocument()).setDocumentFilter(new StringDocumentFilter());
        formPanel.add(shiftsField, gbc);
        shiftsField.setToolTipText("e.g., Morning/Afternoon/Evening");

        // Display Number field - accepts only integers
        gbc.gridx = 3; gbc.gridy = 5;
        displayNumberField = new JTextField(15);
        ((AbstractDocument) displayNumberField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        formPanel.add(displayNumberField, gbc);
        displayNumberField.setToolTipText("Please enter an existing staff index number");

        // Joined checkbox
        gbc.gridx = 3; gbc.gridy = 6;
        gbc.ipadx = 0;  // Reset padding for checkbox
        joinedCheckBox = new JCheckBox();
        formPanel.add(joinedCheckBox, gbc);

        // Create wrapper panel with padding
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));  // Add padding
        wrapperPanel.setBackground(Color.decode("#F2EFE7"));  // Match background color
        wrapperPanel.add(formPanel, BorderLayout.CENTER);  // Add form panel to wrapper

        // Add wrapper panel to main panel
        mainPanel.add(wrapperPanel, BorderLayout.CENTER);
    }

    /**
     * Creates the button panel with all action buttons
     * Sets up button layout and adds action listeners
     */
    private void createButtonPanel() {
        // Create panel with grid layout (2 rows, 4 columns, with gaps)
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBackground(Color.decode("#F2EFE7"));  // Set background color
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));  // Add vertical padding

        // Create all buttons using helper method
        addFullTimeStaffButton = createButton("Add Full Time Staff", buttonPanel);
        addPartTimeStaffButton = createButton("Add Part Time Staff", buttonPanel);
        setSalaryButton = createButton("Set Salary", buttonPanel);
        setShiftsButton = createButton("Set Shifts", buttonPanel);
        terminateStaffButton = createButton("Terminate Staff", buttonPanel);
        displayButton = createButton("Display Staff", buttonPanel);
        clearButton = createButton("Clear", buttonPanel);

        // Add button panel to main panel at the bottom
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Helper method to create a button and add it to panel
     * Ensures consistent button creation
     * 
     * @param text  The text to display on the button
     * @param panel The panel to add the button to
     * @return      The created button
     */
    private JButton createButton(String text, JPanel panel) {
        JButton button = new JButton(text);  // Create button with text
        button.setFocusable(false);          // Prevent focus highlighting
        button.addActionListener(this);      // Register this class as action listener
        panel.add(button);                   // Add button to panel
        return button;                       // Return the button
    }

    /**
     * Creates the table panel to display staff list
     * Sets up table model, columns, and scrolling
     */
    private void createTablePanel() {
        // Create table model with column names
        String[] columnNames = {"Index", "Vacancy #", "Staff Name", "Designation", "Job Type", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);  // 0 initial rows

        // Create table with model
        staffTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(staffTable);  // Add scrolling capability
        scrollPane.setPreferredSize(new Dimension(750, 150));  // Set preferred size

        // Create panel for table
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.decode("#F2EFE7"));  // Set background color
        tablePanel.setBorder(BorderFactory.createTitledBorder("Staff List"));  // Add border with title
        tablePanel.add(scrollPane);  // Add table with scrolling

        // Add table panel to main panel at the top
        mainPanel.add(tablePanel, BorderLayout.NORTH);
    }

    /**
     * Refreshes the table with current staff list
     * Called whenever the staff list changes
     */
    private void refreshTable() {
        // Clear the table
        tableModel.setRowCount(0);  // Remove all rows

        // Add each staff member to the table
        for (int i = 0; i < staffList.size(); i++) {
            StaffHire staff = staffList.get(i);  // Get staff at index i

            // Determine status based on staff type
            String status;
            if (staff instanceof FullTimeStaffHire) {
                status = "Full Time";  // Full-time staff
            } else {
                PartTimeStaffHire partTime = (PartTimeStaffHire) staff;
                status = partTime.getTerminated() ? "Terminated" : "Part Time";  // Part-time status
            }

            // Create row data
            Object[] row = {
                i,  // Index in the list
                staff.getVacancyNumber(),  // Vacancy number
                staff.getStaffName(),      // Staff name
                staff.getDesignation(),    // Designation
                staff.getJobType(),        // Job type
                status                     // Status (Full Time/Part Time/Terminated)
            };

            // Add row to table
            tableModel.addRow(row);
        }
    }

    /**
     * Clears all form fields
     * Used when adding new staff or when Clear button is clicked
     */
    private void clearFields() {
        // Clear all text fields by setting empty text
        vacancyNumberField.setText("");
        designationField.setText("");
        jobTypeField.setText("");
        staffNameField.setText("");
        joiningDateField.setText("");
        qualificationField.setText("");
        appointedByField.setText("");
        salaryField.setText("");
        weeklyFractionalHoursField.setText("");
        workingHoursField.setText("");
        wagesPerHourField.setText("");
        shiftsField.setText("");
        displayNumberField.setText("");
        joinedCheckBox.setSelected(false);  // Uncheck joined checkbox
    }

    /**
     * Gets display number from field with validation
     * 
     * @return The validated display number, or -1 if invalid
     */
    private int getDisplayNumber() {
        int displayNum = -1;  // Default invalid value
        try {
            // Try to parse display number as integer
            displayNum = Integer.parseInt(displayNumberField.getText());

            // Validate range (must be a valid index in staffList)
            if (displayNum < 0 || displayNum >= staffList.size()) {
                JOptionPane.showMessageDialog(frame, 
                    "Error: Display number must be between 0 and " + (staffList.size() - 1),
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return -1;  // Invalid range
            }
        } catch (NumberFormatException e) {
            // Not a valid number
            JOptionPane.showMessageDialog(frame, 
                "Error: Please enter a valid number for Display Number.",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return -1;  // Invalid number
        }
        return displayNum;  // Return validated display number
    }

    /**
     * Adds a full time staff member
     * Collects data from form fields and creates a new FullTimeStaffHire object
     */
    private void addFullTimeStaff() {
        try {
            // Get values from text fields
            int vacancyNumber = Integer.parseInt(vacancyNumberField.getText());
            String designation = designationField.getText();
            String jobType = jobTypeField.getText();
            String staffName = staffNameField.getText();
            String joiningDate = joiningDateField.getText();
            String qualification = qualificationField.getText();
            String appointedBy = appointedByField.getText();
            boolean joined = joinedCheckBox.isSelected();
            double salary = Double.parseDouble(salaryField.getText());
            int weeklyFractionalHours = Integer.parseInt(weeklyFractionalHoursField.getText());

            // Validate required fields
            if (designation.isEmpty() || jobType.isEmpty() || staffName.isEmpty() || 
            joiningDate.isEmpty() || qualification.isEmpty() || appointedBy.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled out.", 
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;  // Exit method if validation fails
            }

            // Create new full-time staff object
            FullTimeStaffHire fullTimeStaff = new FullTimeStaffHire(
                vacancyNumber, designation, jobType, staffName, joiningDate, 
                qualification, appointedBy, joined, salary, weeklyFractionalHours
            );

            // Add to staff list
            staffList.add(fullTimeStaff);

            // Update UI
            refreshTable();  // Update table with new staff
            clearFields();   // Clear input fields

            // Show success message
            JOptionPane.showMessageDialog(frame, staffName + " has been added as a full time staff!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            // Handle number format errors
            JOptionPane.showMessageDialog(frame, 
                "Please enter valid numbers for Vacancy Number, Salary, and Weekly Hours.",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a part time staff member
     * Collects data from form fields and creates a new PartTimeStaffHire object
     */
    private void addPartTimeStaff() {
        try {
            // Get values from text fields
            int vacancyNumber = Integer.parseInt(vacancyNumberField.getText());
            String designation = designationField.getText();
            String jobType = jobTypeField.getText();
            String staffName = staffNameField.getText();
            String joiningDate = joiningDateField.getText();
            String qualification = qualificationField.getText();
            String appointedBy = appointedByField.getText();
            boolean joined = joinedCheckBox.isSelected();
            int workingHour = Integer.parseInt(workingHoursField.getText());
            double wagesPerHour = Double.parseDouble(wagesPerHourField.getText());
            String shifts = shiftsField.getText();

            // Validate required fields
            if (designation.isEmpty() || jobType.isEmpty() || staffName.isEmpty() || 
                joiningDate.isEmpty() || qualification.isEmpty() || appointedBy.isEmpty() || 
                shifts.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled out.", 
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;  // Exit method if validation fails
            }

            // Create new part-time staff object
            PartTimeStaffHire partTimeStaff = new PartTimeStaffHire(
                vacancyNumber, designation, jobType, staffName, joiningDate, 
                qualification, appointedBy, joined, workingHour, wagesPerHour, shifts
            );

            // Add to staff list
            staffList.add(partTimeStaff);

            // Update UI
            refreshTable();  // Update table with new staff
            clearFields();   // Clear input fields

            // Show success message
            JOptionPane.showMessageDialog(frame, staffName + " has been added as a part time staff!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            // Handle number format errors
            JOptionPane.showMessageDialog(frame, 
                "Please enter valid numbers for Vacancy Number, Working Hour, and Wages Per Hour.",
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sets salary for a full time staff member
     * Finds staff by vacancy number and updates their salary
     */
    private void setSalaryFullTimeStaff() {
        try {
            // Get values from text fields
            int vacancyNum = Integer.parseInt(vacancyNumberField.getText());
            double newSalary = Double.parseDouble(salaryField.getText());

            boolean found = false;  // Flag to track if staff was found
            
            // Search for staff with matching vacancy number
            for (StaffHire staff : staffList) {
                if (staff.getVacancyNumber() == vacancyNum) {
                    // Check if it's a full-time staff
                    if (staff instanceof FullTimeStaffHire) {
                        // Cast to FullTimeStaffHire to access specific methods
                        FullTimeStaffHire fullTimeStaff = (FullTimeStaffHire) staff;
                        fullTimeStaff.setSalary(newSalary);  // Update salary
                        found = true;  // Staff found and updated
                        
                        // Show success message
                        JOptionPane.showMessageDialog(frame, "Salary updated successfully!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Staff found but not full-time
                        JOptionPane.showMessageDialog(frame, "This is not a Full Time Staff position.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;  // Exit loop after finding staff
                }
            }

            // If no staff found with that vacancy number
            if (!found) {
                JOptionPane.showMessageDialog(frame, "Staff with vacancy number " + vacancyNum + " not found.", 
                    "Not Found", JOptionPane.WARNING_MESSAGE);
            }

            // Update UI
            refreshTable();  // Refresh table to show updated information
            clearFields();   // Clear input fields

        } catch (NumberFormatException e) {
            // Handle number format errors
            JOptionPane.showMessageDialog(frame, "Please enter valid numbers for Vacancy Number and Salary.", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sets working shifts for a part time staff member
     * Finds staff by vacancy number and updates their shifts
     */
    private void setWorkingShiftsPartTimeStaff() {
        try {
            // Get values from text fields
            int vacancyNum = Integer.parseInt(vacancyNumberField.getText());
            String newShifts = shiftsField.getText();

            // Validate shifts field
            if (newShifts.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter shifts information.", 
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;  // Exit method if validation fails
            }

            boolean found = false;  // Flag to track if staff was found
            
            // Search for staff with matching vacancy number
            for (StaffHire staff : staffList) {
                if (staff.getVacancyNumber() == vacancyNum) {
                    // Check if it's a part-time staff
                    if (staff instanceof PartTimeStaffHire) {
                        // Cast to PartTimeStaffHire to access specific methods
                        PartTimeStaffHire partTimeStaff = (PartTimeStaffHire) staff;
                        partTimeStaff.setShifts(newShifts);  // Update shifts
                        found = true;  // Staff found and updated
                        
                        // Show success message
                        JOptionPane.showMessageDialog(frame, "Shifts updated successfully!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Staff found but not part-time
                        JOptionPane.showMessageDialog(frame, "This is not a Part Time Staff position.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;  // Exit loop after finding staff
                }
            }

            // If no staff found with that vacancy number
            if (!found) {
                JOptionPane.showMessageDialog(frame, "Staff with vacancy number " + vacancyNum + " not found.", 
                    "Not Found", JOptionPane.WARNING_MESSAGE);
            }

            // Update UI
            refreshTable();  // Refresh table to show updated information

        } catch (NumberFormatException e) {
            // Handle number format errors
            JOptionPane.showMessageDialog(frame, "Please enter a valid number for Vacancy Number.", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Terminates a part time staff member
     * Finds staff by vacancy number and terminates them
     */
    private void terminatePartTimeStaff() {
        try {
            // Get vacancy number from text field
            int vacancyNum = Integer.parseInt(vacancyNumberField.getText());

            boolean found = false;  // Flag to track if staff was found
            
            // Search for staff with matching vacancy number
            for (StaffHire staff : staffList) {
                if (staff.getVacancyNumber() == vacancyNum) {
                    // Check if it's a part-time staff
                    if (staff instanceof PartTimeStaffHire) {
                        // Cast to PartTimeStaffHire to access specific methods
                        PartTimeStaffHire partTimeStaff = (PartTimeStaffHire) staff;
                        partTimeStaff.terminateStaff();  // Terminate staff
                        found = true;  // Staff found and terminated
                        
                        // Show success message
                        JOptionPane.showMessageDialog(frame, "Staff terminated successfully!", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Staff found but not part-time
                        JOptionPane.showMessageDialog(frame, "This is not a Part Time Staff position.", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;  // Exit loop after finding staff
                }
            }

            // If no staff found with that vacancy number
            if (!found) {
                JOptionPane.showMessageDialog(frame, "Staff with vacancy number " + vacancyNum + " not found.", 
                    "Not Found", JOptionPane.WARNING_MESSAGE);
            }

            // Update UI
            refreshTable();  // Refresh table to show updated information

        } catch (NumberFormatException e) {
            // Handle number format errors
            JOptionPane.showMessageDialog(frame, "Please enter a valid number for Vacancy Number.", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays information for a specific staff member
     * Shows detailed information in a dialog window
     */
    private void displayStaffByNumber() {
        // Get display number with validation
        int displayNum = getDisplayNumber();
        if (displayNum != -1) {  // If valid display number
            // Get staff at the specified index
            StaffHire staff = staffList.get(displayNum);

            // Create dialog to display staff information
            JDialog dialog = new JDialog(frame, "Staff Information", true);  // Modal dialog
            dialog.setSize(500, 400);  // Set dialog size
            dialog.setLocationRelativeTo(frame);  // Center on parent frame

            // Create content panel with border layout
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Add padding

            // Redirect System.out to capture output from display method
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  // Output buffer
            PrintStream ps = new PrintStream(baos);  // Print stream to buffer
            PrintStream old = System.out;  // Store original System.out
            System.setOut(ps);  // Redirect output to our print stream

            // Call the display method of the staff object
            staff.display();  // This will print to our buffer instead of console

            // Reset System.out and get the captured output
            System.out.flush();  // Flush all output
            System.setOut(old);  // Restore original System.out
            String displayText = baos.toString();  // Get captured text

            // Create text area with the output
            JTextArea textArea = new JTextArea(displayText);
            textArea.setEditable(false);  // Make read-only
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));  // Set font
            JScrollPane scrollPane = new JScrollPane(textArea);  // Add scrolling

            // Add text area to panel
            panel.add(scrollPane, BorderLayout.CENTER);

            // Add close button at the bottom
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> dialog.dispose());  // Close dialog when clicked
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(closeButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            // Add panel to dialog and show
            dialog.add(panel);
            dialog.setVisible(true);  // Show dialog
        }
    }

    /**
     * Action listener implementation for button clicks
     * Handles all button events based on source
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Determine which button was clicked and call appropriate method
        if (e.getSource() == addFullTimeStaffButton) {
            addFullTimeStaff();  // Add full-time staff
        } else if (e.getSource() == addPartTimeStaffButton) {
            addPartTimeStaff();  // Add part-time staff
        } else if (e.getSource() == setSalaryButton) {
            setSalaryFullTimeStaff();  // Set salary for full-time staff
        } else if (e.getSource() == setShiftsButton) {
            setWorkingShiftsPartTimeStaff();  // Set shifts for part-time staff
        } else if (e.getSource() == terminateStaffButton) {
            terminatePartTimeStaff();  // Terminate part-time staff
        } else if (e.getSource() == displayButton) {
            displayStaffByNumber();  // Display staff information
        } else if (e.getSource() == clearButton) {
            clearFields();  // Clear all input fields
        }
    }

    /**
     * Main method to run the application
     * Entry point for the program
     */
    public static void main(String[] args) {
        // Set look and feel to system default for better integration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();  // Print stack trace if error occurs
        }

        // Create RecruitmentSystem instance on the Event Dispatch Thread
        // This ensures thread safety for Swing components
        SwingUtilities.invokeLater(() -> new RecruitmentSystem());
    }
    
}