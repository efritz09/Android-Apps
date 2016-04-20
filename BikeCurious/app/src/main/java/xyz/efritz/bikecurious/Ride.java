package xyz.efritz.bikecurious;

/**
 * Created by Eric on 4/20/2016.
 */
public class Ride {
//    String ID;
    String location;
    String date;
    int imageID;


    int _id;
    String _name;
    String _password;


    // constructor
    public Ride(){

    }
    public Ride(String location, String date, int imageID){
        this.location = location;
        this.date = date;
        this.imageID = imageID;
    }

//    public Ride(String location, String date){
//        this.location = location;
//        this.date = date;
//    }

    // getting location
    public String getLocation(){
        return this.location;
    }

    // setting location
    public void setLocation(String l){
        this.location = l;
    }

    // getting date
    public String getDate(){
        return this.date;
    }

    // setting date
    public void setDate(String d){
        this.date = d;
    }

    // getting imageID
    public int getImageID(){
        return this.imageID;
    }

    // setting imageID
    public void setImageID(int ID){
        this.imageID = ID;
    }

}
