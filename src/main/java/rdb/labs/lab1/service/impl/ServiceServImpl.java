package rdb.labs.lab1.service.impl;

import rdb.labs.lab1.model.Service;
import rdb.labs.lab1.repository.ServiceRepository;
import rdb.labs.lab1.service.ServiceServ;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServImpl implements ServiceServ {

    private final ServiceRepository serviceRepository;

    public ServiceServImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Optional<Service> findById(Integer id) {
        return serviceRepository.findById(id);
    }

    @Override
    public List<Service> findAllByPrice(Double price) {
        return serviceRepository.findAllByPrice(price);
    }

    @Override
    public List<Service> findByName(String name) {
        return serviceRepository.findAllByName(name);
    }

    @Override
    public void saveService(Service service) {
        serviceRepository.save(service);
    }
}
