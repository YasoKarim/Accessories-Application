package com.example.test;

public class UserHelperClass {
    String username,email,phoneno,password;
    public UserHelperClass(){}
    public UserHelperClass( String username, String email, String phoneno, String password) {
        this.username = username;
        this.email = email;
        this.phoneno = phoneno;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public String getPassword() {
        return password;
    }
}
