package com.vjmartinez.petagram;

/**
 * @author Victor Martinez
 */
public class Contacto {

    private String name;
    private String phone;
    private String email;
    private int photo;
    private short age;
    private String sex;
    private String address;

    /**
     * Default constructor
     * @param name
     * @param phone
     */
    public Contacto(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public Contacto(String name, String phone, String email, int photo){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.photo = photo;
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

    public short getAge() {
        return age;
    }

    public void setAge(short age) {
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

    @Override
    public String toString() {
        return this.name;
    }
}
