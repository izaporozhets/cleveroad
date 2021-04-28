package rdb.labs.lab1.service;

import rdb.labs.lab1.model.Service;
import java.util.List;
import java.util.Optional;

public interface ServiceServ {
    List<Service> findAll();
    Optional<Service> findById(Integer id);
    List<Service> findAllByPrice(Double price);
    List<Service> findByName(String name);
    void saveService(Service service);
}
