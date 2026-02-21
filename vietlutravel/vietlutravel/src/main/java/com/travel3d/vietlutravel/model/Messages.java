package com.travel3d.vietlutravel.model;

public class Messages {

    private Integer customerID;
    private Integer userID;
    private String content;
    private Boolean isFromAdmin;

    // Getter Setter
    public Integer getCustomerID() { return customerID; }
    public void setCustomerID(Integer customerID) { this.customerID = customerID; }

    public Integer getUserID() { return userID; }
    public void setUserID(Integer userID) { this.userID = userID; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Boolean getIsFromAdmin() { return isFromAdmin; }
    public void setIsFromAdmin(Boolean fromAdmin) { isFromAdmin = fromAdmin; }
}
