package com.vjmartinez.petagram;

/**
 * @author Victor Martinez
 */
public class Contacto {

    private String name;
    private String phone;
    private String email;

    /**
     * Default constructor
     * @param name
     * @param phone
     */
    public Contacto(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    public Contacto(String name, String phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
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

    @Override
    public String toString() {
        return this.name;
    }
}
