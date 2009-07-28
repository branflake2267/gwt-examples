package com.gawkat.jdo.server.data;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Employee {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private long id;
  
  @Persistent
  private String name;
  
  @Persistent
  private String nameLast;
  
  @Persistent
  private int telephoneNumber = 0;

  public Employee(String name) {
    this.name = name;
  }
  
  public Employee(String name, String lastname) {
    this.name = name;
    this.nameLast = lastname;
  }
  
  public Employee(String name, String lastname, int telephoneNumber) {
  this.name = name;
  this.nameLast = lastname;
  this.telephoneNumber = telephoneNumber;
}
  
}
