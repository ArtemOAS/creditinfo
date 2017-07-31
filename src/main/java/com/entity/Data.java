package com.entity;

import javax.persistence.*;
import java.util.function.Consumer;

/**
 * Created by Artem on 20.05.2017.
 */
@Entity
@Table(name = "creditinfo")
@NamedQuery(name = "Data.getdAll", query = "FROM Data c")
public class Data {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;

    @Column(name = "name_company")
    private String nameCompany;
    @Column(name = "sum_credit")
    private String sumCredit;
    @Column(name = "period_credit")
    private String periodCredit;
    @Column(name = "old_percent_sum")
    private String oldPercentSum;
    @Column(name = "new_percent_sum")
    private String newPercentSum;
    @Column(name = "difference_percent_sum")
    private String differencePercentSum;

    public Data(Consumer<Data> data) {
        data.accept(this);
    }

    public Data() {
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getSumCredit() {
        return sumCredit;
    }

    public void setSumCredit(String sumCredit) {
        this.sumCredit = sumCredit;
    }

    public String getPeriodCredit() {
        return periodCredit;
    }

    public void setPeriodCredit(String periodCredit) {
        this.periodCredit = periodCredit;
    }

    public String getOldPercentSum() {
        return oldPercentSum;
    }

    public void setOldPercentSum(String percentSum) {
        this.oldPercentSum = percentSum;
    }

    public String getNewPercentSum() {
        return newPercentSum;
    }

    public void setNewPercentSum(String newPercentSum) {
        this.newPercentSum = newPercentSum;
    }

    public String getDifferencePercentSum() {
        return differencePercentSum;
    }

    public void setDifferencePercentSum(String differencePercentSum) {
        this.differencePercentSum = differencePercentSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (id != null ? !id.equals(data.id) : data.id != null) return false;
        if (nameCompany != null ? !nameCompany.equals(data.nameCompany) : data.nameCompany != null) return false;
        if (sumCredit != null ? !sumCredit.equals(data.sumCredit) : data.sumCredit != null) return false;
        if (periodCredit != null ? !periodCredit.equals(data.periodCredit) : data.periodCredit != null) return false;
        if (oldPercentSum != null ? !oldPercentSum.equals(data.oldPercentSum) : data.oldPercentSum != null)
            return false;
        if (newPercentSum != null ? !newPercentSum.equals(data.newPercentSum) : data.newPercentSum != null)
            return false;
        return differencePercentSum != null ? differencePercentSum.equals(data.differencePercentSum) : data.differencePercentSum == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nameCompany != null ? nameCompany.hashCode() : 0);
        result = 31 * result + (sumCredit != null ? sumCredit.hashCode() : 0);
        result = 31 * result + (periodCredit != null ? periodCredit.hashCode() : 0);
        result = 31 * result + (oldPercentSum != null ? oldPercentSum.hashCode() : 0);
        result = 31 * result + (newPercentSum != null ? newPercentSum.hashCode() : 0);
        result = 31 * result + (differencePercentSum != null ? differencePercentSum.hashCode() : 0);
        return result;
    }
}
