package com.bcp.project.reto.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
  private String dni;
  private String name;

  @Id
  @Column(name = "dni")
  public String getDni() {
    return dni;
  }

  public void setDni(String dni) {
    this.dni = dni;
  }

  @Column(name = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
