package com.example.flylsb.myyy;

public class User {
    private String name;
    private String age;
    private String en;
    private String cn;
    private String id;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public  String getEn(){

        return en;
    }
    public void setEn(String en) {
        this.en = en;
    }

    public String getCn(){
        return cn;
    }
    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getId(){
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "User [name=" + name + ", age=" + age + cn + en +id +"]";
    }


}
