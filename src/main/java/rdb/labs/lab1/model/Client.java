package rdb.labs.lab1.model;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Client {

    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "employment")
    private String employment;
    @Column(name = "address")
    private String address;
    @Column(name = "contact")
    private String contact;

    public Client(String name, String surname, String employment, String address, String contact) {
        this.name = name;
        this.surname = surname;
        this.employment = employment;
        this.address = address;
        this.contact = contact;
    }

    public Client() {

    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", employment='" + employment + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
