package ru.vmk.shahova.databaseUi.ui.page;

import java.sql.Date;

public class Itog {
    private int detailCode;
    private String type;
    private String name;
    private String init;
    private int price;
    private Integer number;
    private int codeSupplier;
    private Date date;
    private Date startDate;
    private Date endDate;
    private int plan;
    private int unitPrice;

    public Itog(int detailCode, String type, String name, String init, int price, Integer number, int codeSupplier, Date date, Date startDate, Date endDate, int plan, int unitPrice) {
        this.detailCode = detailCode;
        this.type = type;
        this.name = name;
        this.init = init;
        this.price = price;
        this.number = number;
        this.codeSupplier = codeSupplier;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.plan = plan;
        this.unitPrice = unitPrice;
    }

    public int getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(int detailCode) {
        this.detailCode = detailCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getCodeSupplier() {
        return codeSupplier;
    }

    public void setCodeSupplier(int codeSupplier) {
        this.codeSupplier = codeSupplier;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}
