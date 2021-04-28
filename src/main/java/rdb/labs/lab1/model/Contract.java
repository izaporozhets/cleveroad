package rdb.labs.lab1.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Contract {
    @Id
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "client_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @JoinColumn(name = "service_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Service service;

    @Column(name = "commission")
    private Double commission;

    @Column(name = "total_sum")
    private Double totalSum;

    @Column(name = "date")
    private Date date;

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", client=" + client +
                ", service=" + service +
                ", commission=" + commission +
                ", totalSum=" + totalSum +
                '}';
    }
}
