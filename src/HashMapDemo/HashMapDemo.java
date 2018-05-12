package HashMapDemo;

// import packages
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.*;

/**
 * Date: 8/7/16
 * Class:
 * Author: ATG8
 * Purpose: This is the main method that will run Project 4.
 */
public class HashMapDemo extends JFrame{
    
    // set main window size
    private static final int WIN_WIDTH = 320, WIN_HEIGHT = 200;
    
    // create main panel user interfaces
    private final JLabel id = new JLabel(" Id:");
    private final JTextField enterID = new JTextField("");
    private final JLabel name = new JLabel(" Name:");
    private final JTextField enterName = new JTextField("");
    private final JLabel major = new JLabel(" Major:");
    private final JTextField enterMajor = new JTextField("");
    private final JLabel select = new JLabel(" Choose Selection:");
    private final String [] combo = {"Insert", "Delete", "Find", "Update"};
    private final JComboBox comboSelect = new JComboBox(combo);
    private final JButton process = new JButton("Process Request");
    private final JOptionPane popup = new JOptionPane();
    
    // create subpanel combo box variables
    Object [] grades = {'F', 'D', 'C', 'B', 'A'};
    Object [] credits = {3, 6};
    
    // create hashmap of student information
    HashMap<Integer, Student> proj4DB;
    @SuppressWarnings("unchecked")
    
    
    // create constructor
    public HashMapDemo (){
        
        // main panel setup
        super("Project 4");
        setFrame(WIN_WIDTH, WIN_HEIGHT);
        setResizable(false);
        JPanel mainPanel = new JPanel();
        add(mainPanel);
        mainPanel.setLayout(new GridLayout(6,2));
        
        // add buttons and input boxes to panel
        mainPanel.add(id); //add Id: label
        mainPanel.add(enterID); //add Id: text field
        mainPanel.add(name); //add Name: label
        mainPanel.add(enterName); //add Name: text field
        mainPanel.add(major); //add Major: label
        mainPanel.add(enterMajor); //add Major: text field
        mainPanel.add(select); //add Choose Selection: label
        mainPanel.add(comboSelect); //add Choose Selection: combo box
        mainPanel.add(process); //add Process Request button
        
        // create hash map, add listeners, set panel closing
        proj4DB = new HashMap<Integer, Student>();
        process.addActionListener(new processButton());
        enterID.addFocusListener(new FocusListener(){
            @Override
            public void focusGained(FocusEvent fe){
            enterID.setText("");
            }
            @Override
            public void focusLost(FocusEvent fe){
                if(keyExists(idIsInt())){
                    Student found = proj4DB.get(idIsInt());
                    enterName.setText(found.getName());
                    enterName.setEditable(false);
                    enterMajor.setText(found.getMajor());
                    enterMajor.setEditable(false);                
                }else{
                    enterName.setText("");
                    enterName.setEditable(true);
                    enterMajor.setText("");
                    enterMajor.setEditable(true);
                }
            } //end focusLost method
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } //end constructor
    
    class processButton implements ActionListener{
        @Override
        // action listener to perform process based on action
        public void actionPerformed(ActionEvent e){
            // get process request
            String action = (String)comboSelect.getSelectedItem();

            // set action success message
            String actionVerify;
                if(action.equals("Insert")){actionVerify = "inserted into";}
                else{actionVerify = "deleted from";}
            String successMessage = enterName.getText() + " has been "
                    + "successfully " + actionVerify + " the database.";

            // set cannot find student ID message
            String idNotFound = enterID.getText() + " cannot be found in the "
                    + " database. Please try again with new ID.";
            
            try{
                switch (action) {
                    case "Insert":
                        Student student = new Student(enterName.getText(),
                                    enterMajor.getText());
                        if(!keyExists(idIsInt())){
                            proj4DB.put(idIsInt(), student);
                            JOptionPane.showMessageDialog(popup, successMessage);
                            resetInput();
                        }else{
                            JOptionPane.showMessageDialog(popup, enterID.getText()
                                    + " already exists in the database.  Please "
                                    + "select a new ID.", "ID already exists.",
                                    JOptionPane.ERROR_MESSAGE);
                            enterID.setText("");
                            enterID.requestFocus();
                        } //end if keyExists check
                        break;
                    case "Delete":
                        if(keyExists(idIsInt())){
                            Student delete = proj4DB.get(idIsInt());
                            int optionButton = JOptionPane.YES_NO_OPTION;
                            int optionResult = JOptionPane.showConfirmDialog(popup,
                                    "Review and confirm deletion of record below:\nID: "
                                            + enterID.getText() + "\nName: " + delete.getName() +
                                            "\nMajor: " + delete.getMajor(), "Confirm Record " +
                                                    "Deletion", optionButton);
                            if(optionResult == 0){
                                proj4DB.remove(idIsInt());
                                JOptionPane.showMessageDialog(popup, successMessage);
                                resetInput();
                            }else{
                                JOptionPane.showMessageDialog(popup, "You have "
                                        + "chosen to abort record deletion.", "Deletion "
                                                + "Aborted", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }else{
                            JOptionPane.showMessageDialog(popup, idNotFound,
                                    "ID does not exist.", JOptionPane.ERROR_MESSAGE);
                            enterID.setText("");
                            enterID.requestFocus();
                        }   break;
                    case "Find":
                        if(keyExists(idIsInt())){
                            Student find = proj4DB.get(idIsInt());
                            JOptionPane.showMessageDialog(popup, "ID: " +
                                    enterID.getText() + "\n" + find.toString(),
                                    "Student Record",
                                    JOptionPane.INFORMATION_MESSAGE);
                            resetInput();
                        }else{
                            JOptionPane.showMessageDialog(popup, idNotFound,
                                    "ID does not exist.", JOptionPane.ERROR_MESSAGE);
                            enterID.setText("");
                            enterID.requestFocus();
                        }   break;
                    case "Update":
                        if(keyExists(idIsInt())){
                            Student update = proj4DB.get(idIsInt());
                            char showGrades = (char) JOptionPane.showInputDialog(
                                    popup, "Choose grade:", "Grade Input",
                                    JOptionPane.QUESTION_MESSAGE, null, grades, 'A');
                            int showCredits = (int) JOptionPane.showInputDialog(
                                    popup, "Choose credits:", "Credit Input",
                                    JOptionPane.QUESTION_MESSAGE, null, credits, 3);
                            int grade = Arrays.asList(grades).indexOf(showGrades);
                            int credit = showCredits;
                            update.courseCompleted(grade*credit, credit);
                            JOptionPane.showMessageDialog(popup, update.toString(),
                                    "Student Record", JOptionPane.INFORMATION_MESSAGE);
                            resetInput();
                        }else{
                            JOptionPane.showMessageDialog(popup, idNotFound,
                                    "ID does not exist.", JOptionPane.ERROR_MESSAGE);
                            resetInput();
                        }   break;
                    default:
                        break;
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(popup, ex.getMessage(),
                        "Input error",JOptionPane.ERROR_MESSAGE);
            }
        } //end action listener class
    }
    
    // check if key already exists
    public boolean keyExists(int enterID){
        Set keys = proj4DB.keySet();
        Iterator itr = (Iterator) keys.iterator();
        int key;
        Student record = null;
        
        while(itr.hasNext()){
            key = (int) itr.next();
            
            if(key==enterID)record=(Student)proj4DB.get(key);
        } //end while
        return record != null;
    } //end key check
    
    
    // check input is integer
    public int idIsInt(){
        try{
            return Integer.parseInt(enterID.getText());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(popup, "Please enter an " +
                    "ID in the form of an integer.", "ID Value Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
        resetInput();
        return 0;
    } //end integer check
    
    // clear user input and reset focus
    public void resetInput(){
        enterID.setText("");
        enterName.setText("");
        enterMajor.setText("");
        enterID.requestFocus();
    } //end inputBox clear and refocus
    
    
    // set main window size
    private void setFrame(int width, int height){
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } //end main window sizing
    
    
    // display main window and focus on inputBox
    public void display(){
        setVisible(true);
        enterID.requestFocus();
    }
    
    

    // Main
    public static void main(String[] args) {
        HashMapDemo start = new HashMapDemo();
        start.display();
    } //end Main
} //end program
