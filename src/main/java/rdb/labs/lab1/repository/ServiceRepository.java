package rdb.labs.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rdb.labs.lab1.model.Service;
import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    //SELECT * FROM service WHERE service.name = ?
    Optional<Service> findFirstByName(String name);

    //SELECT * FROM service WHERE service.name LIKE '%?%'
    @Query("select s from Service s where s.name like %:name%")
    List<Service> findAllByName(@Param("name") String name);

    //SELECT * FROM service WHERE service.price = ?
    List<Service> findAllByPrice(Double price);
}
