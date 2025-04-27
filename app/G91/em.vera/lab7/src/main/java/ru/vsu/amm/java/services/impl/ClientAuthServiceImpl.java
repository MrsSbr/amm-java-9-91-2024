package ru.vsu.amm.java.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entities.Client;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;
import ru.vsu.amm.java.repository.impl.ClientRepository;
import ru.vsu.amm.java.services.ClientAuthService;

import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class ClientAuthServiceImpl implements ClientAuthService {
    private final ClientRepository clientRepository;

    public ClientAuthServiceImpl() {
        this.clientRepository = new ClientRepository();
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
}
