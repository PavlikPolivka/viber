package com.ppolivka.viber.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "account")
@SequenceGenerator(name="account_id_seq", sequenceName = "account_id_seq", allocationSize=1)
public class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="account_id_seq")
    protected Integer id;

    @Column(name = "name")
    @NotEmpty
    protected String name;

    @Column(name = "password")
    @NotEmpty
    protected String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
