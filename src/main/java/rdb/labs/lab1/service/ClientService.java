package rdb.labs.lab1.service;

import rdb.labs.lab1.model.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> findAll();
    Optional<Client> findById(Integer id);
    List<Client> findClientByNameOrSurname(String name);
    List<Client> findClientByNameAndSurname(String name, String surname);
    void saveClient(Client client);
}
