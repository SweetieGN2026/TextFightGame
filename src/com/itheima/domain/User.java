package com.itheima.domain;

import java.util.Random;

public class User {
    private  String id;
    private  String Username;
    private  String Password;
    private  boolean Status;

    public User() {
        id=creatId();
        Status=true;
    }

    public User( String username, String password) {
        id=creatId();
        Username = username;
        Password = password;
        Status=true;
    }
    public  String creatId() {
      StringBuilder sb = new StringBuilder("heima");
      Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            int num = rand.nextInt(10);
            sb.append(num);
        }
        return sb.toString();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }
}
