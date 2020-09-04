/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mattsonHomeBuilderGUIApp;

/**
 *
 * @author jonese53
 */
public class Home {

    String homeType;
    String homeIDNumber;
    Double homePrice; 
    String custFirstName;
    String custLastName;

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }

    public String getHomeIDNumber() {
        return homeIDNumber;
    }

    public void setHomeIDNumber(String homeIDNumber) {
        this.homeIDNumber = homeIDNumber;
    }

    public Double getHomePrice() {
        return homePrice;
    }

    public void setHomePrice(Double homePrice) {
        this.homePrice = homePrice;
    }

    public String getCustFirstName() {
        return custFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    public String getCustLastName() {
        return custLastName;
    }

    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }
}
