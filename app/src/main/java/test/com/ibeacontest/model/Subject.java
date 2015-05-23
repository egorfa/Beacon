package test.com.ibeacontest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Egor on 23.05.2015.
 */
public class Subject  implements Parcelable {

    private String Name;
    private String Day;
    private String Time;
    private String Audience;
    private String TeacherID;

    public Subject() {
    }

    public Subject(String name, String day, String time, String audience, String teacherID) {
        Name = name;
        Day = day;
        Time = time;
        Audience = audience;
        TeacherID = teacherID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAudience() {
        return Audience;
    }

    public void setAudience(String audience) {
        Audience = audience;
    }

    public String getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(String teacherID) {
        TeacherID = teacherID;
    }

    //Parcelable методы


    public Subject(Parcel in){
        Name = in.readString();
        Day = in.readString();
        Time = in.readString();
        Audience = in.readString();
        TeacherID = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(Name);
        parcel.writeString(Day);
        parcel.writeString(Time);
        parcel.writeString(Audience);
        parcel.writeString(TeacherID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

}
