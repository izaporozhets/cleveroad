package rdb.labs.lab1.service.impl;

import org.springframework.stereotype.Service;
import rdb.labs.lab1.model.Client;
import rdb.labs.lab1.repository.ClientRepository;
import rdb.labs.lab1.service.ClientService;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findClientByNameOrSurname(String name) {
        return clientRepository.findClientByNameOrSurname(name);
    }

    @Override
    public List<Client> findClientByNameAndSurname(String name, String surname) {
        return clientRepository.findClientByNameAndSurname(name, surname);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

}
