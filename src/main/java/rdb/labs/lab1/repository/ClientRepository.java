package rdb.labs.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rdb.labs.lab1.model.Client;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    //SELECT * FROM client WHERE client.name LIKE ? OR client.surname LIKE ?
    @Query("select c from Client c where c.name like %:name% or c.surname like %:name%")
    List<Client> findClientByNameOrSurname(@Param("name") String name);
    //SELECT * FROM client WHERE client.surname = ?
    List<Client> findClientBySurname(String surname);
    //SELECT * FROM client WHERE client.name = ? AND client.surname = ?
    List<Client> findClientByNameAndSurname(String name, String surname);
}
