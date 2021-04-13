package com.mashibing.eureka.pojo;

/***********************
 * @Description: TODO 类描述<BR>
 * @author: zhao.song
 * @since: 2021/4/14 1:12
 * @version: 1.0
 ***********************/
public class Person {

    private int id;

    private String name;

    public Person(int id, String name) {

        this.id = id;
        this.name = name;
    }

    public Person(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
