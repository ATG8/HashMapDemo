package HashMapDemo;

import java.text.DecimalFormat;

/**
 * Date: 8/7/16
 * Class:
 * Author: ATG8
 * Purpose: This is the Student class that will define student records.
 */
public class Student{
    
    // set variables
    private String name, major;
    private int totalQualityPoints, totalCredits;
    
    
    // constructor
    public Student(String name, String major){
        this.name = name;
        this.major = major;
        totalQualityPoints = 0;
        totalCredits = 0;
    }
    
    // courseCompleted method for accepting grade and credits to compute GPA
    public void courseCompleted(int grade, int credit){
        totalQualityPoints += grade;
        totalCredits += credit;
    }
    
    // toString method to return student information
    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("0.0");
        String base = "Name: " + name + "\nMajor: " + major + "\nGPA: ";
        if (totalCredits==0){return base + "4.0";}else{return base + 
                df.format((double)totalQualityPoints/totalCredits);}
        }
    
    // getName method
    public String getName(){
        return name;
    }
    
    // getMajor method
    public String getMajor(){
        return major;
    }
} //end Student class
