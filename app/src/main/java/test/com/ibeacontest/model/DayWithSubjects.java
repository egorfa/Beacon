package test.com.ibeacontest.model;

import java.util.ArrayList;

/**
 * Created by Egor on 23.05.2015.
 */
public class DayWithSubjects {

    private String Day;
    private ArrayList<Subject> subjects;

    public DayWithSubjects() {
    }

    public DayWithSubjects(String day, ArrayList<Subject> subjects) {
        Day = day;
        this.subjects = subjects;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }
}