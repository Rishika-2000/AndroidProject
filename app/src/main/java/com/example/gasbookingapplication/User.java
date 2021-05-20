package com.example.gasbookingapplication;

public class User {
    public String FullName,Address,phno,email,aadhar;
    public User()
    {}
    public User(String FullName,String Address, String phno,String email,String aadhar)
    {
        this.FullName=FullName;
        this.Address=Address;
        this.phno=phno;
        this.email=email;
        this.aadhar=aadhar;
    }
}
