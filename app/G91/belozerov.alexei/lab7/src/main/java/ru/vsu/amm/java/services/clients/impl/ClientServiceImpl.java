package ru.vsu.amm.java.services.clients.impl;

import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entities.ClientEntity;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;
import ru.vsu.amm.java.repositories.impl.ClientRepository;
import ru.vsu.amm.java.services.clients.ClientService;

import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl() {
        this.clientRepository = new ClientRepository();
    }

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void login(ClientLoginRequest request) {
        try {
            Optional<ClientEntity> clientOpt = clientRepository.findByEmail(request.email());

            if (clientOpt.isEmpty()) {
                throw new WrongUserCredentialsException("Client with email:" + request.email() + " not found");
            }
            if (!BCrypt.checkpw(request.password(), clientOpt.get().getPassword())) {
                throw new WrongUserCredentialsException("Incorrect password");
            }
        } catch (SQLException e) {
            log.error("Error logging in client with email={}", request.email(), e);
            throw new DataAccessException("Database error", e);
        }
    }

    @Override
    public void register(ClientRegisterRequest request) {
        try {
            Optional<ClientEntity> clientOpt = clientRepository.findByEmail(request.email());
            if (clientOpt.isPresent()) {
                throw new WrongUserCredentialsException("Client with email " + request.email() + " already exists");
            }

            ClientEntity client = new ClientEntity(
                request.name(),
                request.email(),
                BCrypt.hashpw(request.password(), BCrypt.gensalt())
            );
            clientRepository.save(client);
        } catch (SQLException e) {
            log.error("Error registering client with email={}", request.email(), e);
            throw new DataAccessException("Database error", e);
        }
    }

    @Override
    public Optional<Long> getClientIdByEmail(String email) {
        try {
            return clientRepository.findByEmail(email).map(ClientEntity::getClientId);
        } catch (SQLException e) {
            log.error("Error fetching client id", e);
            throw new DataAccessException("Failed to fetch client id", e);
        }
    }
}
