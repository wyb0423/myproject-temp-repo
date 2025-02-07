package com.myproject.jobapp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="user_type")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id")
    private int id;

    @Column(name="user_type_name")
    private String typeName;

    @OneToMany(mappedBy = "userTypeId", cascade = CascadeType.ALL)
    List<Users> users;

    public UserType() {
    }

    public UserType(int id, String typeName, List<Users> users) {
        this.id = id;
        this.typeName = typeName;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "typeName='" + typeName + '\'' +
                ", id=" + id +
                '}';
    }
}
