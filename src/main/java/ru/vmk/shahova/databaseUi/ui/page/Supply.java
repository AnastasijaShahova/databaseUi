package ru.vmk.shahova.databaseUi.ui.page;

import java.sql.Date;

public class Supply {

    private int number;
    private int detailCode;
    private String init;
    private Date start;
    private Date end;
    private int plan;
    private int price;

    public Supply(int number, int detailCode, String init, Date start, Date end, int plan, int price) {
        this.number = number;
        this.detailCode = detailCode;
        this.init = init;
        this.start = start;
        this.end = end;
        this.plan = plan;
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(int detailCode) {
        this.detailCode = detailCode;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
