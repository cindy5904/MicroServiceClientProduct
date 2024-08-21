package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.entity.Client;
import org.example.repository.ClientRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClientService {
    @Inject
    ClientRepository clientRepository;

    @Transactional
    public Client createClient(String name, String email, String phone) {
        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        client.setPhone(phone);
        clientRepository.persist(client);
        return client;
    }

    public List<Client> getAllClient() {
        return clientRepository.listAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findByIdOptional(id);
    }
    @Transactional
    public boolean deleteById(Long id) {
        clientRepository.deleteById(id);
        return false;
    }
    @Transactional
    public Client updateClient(Long id, String name, String email, String phone) {
        Optional<Client> clientUpdate = clientRepository.findByIdOptional(id);
        if (clientUpdate.isPresent()) {
            Client client = clientUpdate.get();
            client.setName(name);
            client.setEmail(email);
            client.setPhone(phone);
            clientRepository.persist(client);
            return client;
        } else {
            throw new EntityNotFoundException("Client non trouvé");
        }
    }
}
