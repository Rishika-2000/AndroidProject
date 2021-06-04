package com.example.gasbookingapplication;

public class User {
    public String FullName,Address,phno,email,aadhar;
    public boolean f;
    public User()
    {}
    public User(String FullName,String Address, String phno,String email,String aadhar,boolean f)
    {
        this.FullName=FullName;
        this.Address=Address;
        this.phno=phno;
        this.email=email;
        this.aadhar=aadhar;
        this.f=f;
    }
}
