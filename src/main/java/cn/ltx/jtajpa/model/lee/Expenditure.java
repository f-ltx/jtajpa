package cn.ltx.jtajpa.model.lee;

import javax.persistence.*;

@Entity(name = "t_expenditure")
public class Expenditure {
    private Integer id;

    private Float money;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expenditure_seq")
    @SequenceGenerator(name = "expenditure_seq", initialValue = 1, allocationSize = 1, sequenceName = "EXPENDITURE_SEQUENCE")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}