package com.example.projectgcdp.Model;

import android.content.Context;
import android.widget.ImageView;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String email;
    public String profileName;
    public int age;
    public String gender;
    public ImageView profile;
    public String result ;
    MyCallback myCallback = null;
    Context context;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String profileName,String email , int age, String gender) {
        this.email = email;
        this.profileName = profileName;
        this.age = age;
        this.gender = gender;

    }

    public User(MyCallback myCallback){
        this.myCallback = myCallback;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ImageView getProfile() {
        return profile;
    }

    public void setProfile(ImageView profile) {
        this.profile = profile;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public interface MyCallback {
        // Declaration of the template function for the interface
         void updateMyText(String myString);
    }

    public void doSomething() {
        // Do something to get String
        String myString = profileName;

        if (myCallback != null) {
            myCallback.updateMyText(myString);
        }
    }
}
// [END blog_user_class]
