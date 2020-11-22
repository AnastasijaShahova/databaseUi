package ru.vmk.shahova.databaseUi.ui.page;

import java.sql.Date;

public class Contract {

    private Integer number;
    private int detailsCode;
    private Date date;

    public Contract(Integer number, int detailsCode, Date date) {
        this.number = number;
        this.detailsCode = detailsCode;
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getDetailsCode() {
        return detailsCode;
    }

    public void setDetailsCode(int detailsCode) {
        this.detailsCode = detailsCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
