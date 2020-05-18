package com.example.assignment1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


public class bookings implements Parcelable {

    private String DriverLicence;
    private String date;
    private int time;

    public bookings(String DL, String d, int t){
        setDate(d);
        setDriverLicence(DL);
        setTime(t);
    }

    public bookings(){

    }


    protected bookings(Parcel in) {
        DriverLicence = in.readString();
        date = in.readString();
        time = in.readInt();
    }


    public static final Creator<bookings> CREATOR = new Creator<bookings>() {
        @Override
        public bookings createFromParcel(Parcel in) {
            return new bookings(in);
        }

        @Override
        public bookings[] newArray(int size) {
            return new bookings[size];
        }
    };

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public void setDriverLicence(String driverLicence) {
        DriverLicence = driverLicence;
    }
     public String getDriverLicence(){
        return DriverLicence;
     }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(DriverLicence);
        dest.writeString(date);
        dest.writeInt(time);
    }

    public int createBookint(String DriverL, String date, int time, ArrayList<bookings> BookingList){
        int flag = 0;
        if(!BookingList.isEmpty()){
            int count = 0;
            for(int i = 0; i< BookingList.size(); i++){
                String D = BookingList.get(i).getDate();
                String DL = BookingList.get(i).getDriverLicence();
                int t = BookingList.get(i).getTime();
                //Log.i("INDEX", String.valueOf(slots.indexOf(slots.get(i))));
                //Log.i("dl", dl);
                //Log.i("DL",DL);
                //Log.i("date",date);

                //check if the given driver licence has already booked a timeslot
                if(D.equals(date) && DriverL.equals(DL)) {
                    flag = 1;//false
                    break;
                }
                //check if the number of the selected timeslot is 10
                else if(time == t && D.equals(date)){
                    count++;
                    if(count <10){
                        flag = 0;//true
                        //.i("check", String.valueOf(count));
                    }
                    else {
                        flag = 2;//false
                        break;
                    }

                }
                else {
                    flag = 0;//true
                    //Log.i("COUNT", String.valueOf(count));
                }
            }

        }
        else flag = 0;//true

        return flag;
    }

}
