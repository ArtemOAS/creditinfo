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

    @Column(name = "sum_credit")
    private String sumCredit;
    @Column(name = "period_credit")
    private String periodCredit;
    @Column(name = "percent_sum")
    private String percentSum;

    public Data(Consumer<Data> data) {
        data.accept(this);
    }

    public Data() {
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

    public String getPercentSum() {
        return percentSum;
    }

    public void setPercentSum(String percentSum) {
        this.percentSum = percentSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (sumCredit != null ? !sumCredit.equals(data.sumCredit) : data.sumCredit != null) return false;
        if (periodCredit != null ? !periodCredit.equals(data.periodCredit) : data.periodCredit != null) return false;
        return percentSum != null ? percentSum.equals(data.percentSum) : data.percentSum == null;
    }

    @Override
    public int hashCode() {
        int result = sumCredit != null ? sumCredit.hashCode() : 0;
        result = 31 * result + (periodCredit != null ? periodCredit.hashCode() : 0);
        result = 31 * result + (percentSum != null ? percentSum.hashCode() : 0);
        return result;
    }
}
