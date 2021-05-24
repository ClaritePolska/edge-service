package pl.clarite.service;

import pl.clarite.dto.RegisterResponse;

import java.util.Optional;

public interface SystemService {
    Optional<String> getStationId();
    void saveEdgeDetails(RegisterResponse registerResponse);
    void saveEdgeKeystore(String keystore);
    void saveEdgeTruststore(String truststore);
}
