package com.SelfHome;

public class Redis {
  public String name;
  
  public String papi_name;
  
  public String before_value;
  
  public Redis(String name, String papi_name, String before_value) {
    this.name = name;
    this.papi_name = papi_name;
    this.before_value = before_value;
  }
}
