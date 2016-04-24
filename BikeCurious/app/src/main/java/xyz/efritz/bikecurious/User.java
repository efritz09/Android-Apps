package xyz.efritz.bikecurious;

/**
 * Created by Eric on 4/23/2016.
 */
public class User {
    String username;
    String password;
    int imageID;
    int ID;

    // constructor
    public User(){

    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, int imageID){
        this.username = username;
        this.password = password;
        this.imageID = imageID;
    }



    // getting username
    public String getUsername(){
        return this.username;
    }

    // setting username
    public void setUsername(String u){
        this.username = u;
    }

    // getting password
    public String getPassword(){
        return this.password;
    }

    // setting password
    public void setPassword(String p){
        this.password = p;
    }

    // getting imageID
    public int getImageID(){
        return this.imageID;
    }

    // setting imageID
    public void setImageID(int ID){
        this.imageID = ID;
    }

    // getting ID
    public int getID(){
        return this.ID;
    }

    // setting ID
    public void setID(int ID){
        this.ID = ID;
    }
}
