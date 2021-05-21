package pl.clarite.service;

import pl.clarite.dto.RegisterResponse;

public interface SystemService {
    void saveEdgeDetails(RegisterResponse registerResponse);
    void saveEdgeKeystore(String keystore);
    void saveEdgeTruststore(String truststore);
}
