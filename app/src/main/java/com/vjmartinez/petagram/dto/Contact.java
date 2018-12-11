package com.vjmartinez.petagram.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Victor Martinez
 */
public class Contact implements Serializable {

    private String name;
    private String phone;
    private String email;
    private int photo;
    private int age;
    private String sex;
    private String address;
    private Date birthDate;
    private int likes;

    public Contact (){
        //Default constructor
    }

    /**
     * Default constructor
     * @param name The name
     * @param phone The phone
     */
    public Contact(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    /**
     *
     * @param name The name
     * @param phone The phone
     * @param email The email
     * @param photo The photo
     * @param birthDate The birth date
     * @param sex The sex
     * @param address The address
     */
    public Contact(String name, String phone, String email, int photo, Date birthDate, String sex,
                    String address, int likes){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
        this.birthDate = birthDate;
        this.sex = sex;
        this.address = address;
        if( this.birthDate != null ){
            Calendar cal = Calendar.getInstance();
            cal.setTime(birthDate);
            int age = Calendar.getInstance().get(Calendar.YEAR) - cal.get(Calendar.YEAR);
            if(Calendar.getInstance().get(Calendar.DAY_OF_YEAR) > cal.get(Calendar.DAY_OF_YEAR)){
                age--;
            }
            setAge(age);
        }
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
