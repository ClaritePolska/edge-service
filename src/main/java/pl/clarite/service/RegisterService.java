package pl.clarite.service;

import pl.clarite.dto.RegisterRequest;
import pl.clarite.dto.RegisterResponse;

public interface RegisterService {
    RegisterResponse register(RegisterRequest registerRequest);
}
