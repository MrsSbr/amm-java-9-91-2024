package ru.vsu.amm.java.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entities.Client;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.mappers.ClientMapper;
import ru.vsu.amm.java.models.dto.ClientDto;
import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;
import ru.vsu.amm.java.repository.impl.ClientRepository;
import ru.vsu.amm.java.services.ClientService;

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
            Optional<Client> clientOpt = clientRepository.findByEmail(request.email());

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
            Optional<Client> clientOpt = clientRepository.findByEmail(request.email());
            if (clientOpt.isPresent()) {
                throw new WrongUserCredentialsException("Client with email " + request.email() + " already exists");
            }

            Client newClient = Client.builder()
                    .email(request.email())
                    .name(request.name())
                    .password(BCrypt.hashpw(request.password(), BCrypt.gensalt()))
                    .build();

            clientRepository.save(newClient);

        } catch (SQLException e) {
            log.error("Error registering client with email={}", request.email(), e);
            throw new DataAccessException("Database error", e);
        }
    }

    @Override
    public Optional<ClientDto> getClientByEmail(String email) {
        try {
            return clientRepository.findByEmail(email).map(ClientMapper::toClientDto);
        } catch (SQLException e) {
            log.error("Error fetching all psychologists", e);
            throw new DataAccessException("Failed to fetch psychologists", e);
        }
    }

    @Override
    public Optional<ClientDto> getClientById(Long id) {
        try {
            return clientRepository.findById(id).map(ClientMapper::toClientDto);
        } catch (SQLException e) {
            log.error("Error fetching all psychologists", e);
            throw new DataAccessException("Failed to fetch psychologists", e);
        }
    }
}
