package pl.clarite.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import pl.clarite.dto.RegisterResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.*;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SystemServiceImpl implements SystemService {

    private static final Logger logger = Logger.getLogger(SystemServiceImpl.class);

    @ConfigProperty(name = "qiot.folder.config")
    String configDirectory;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public Optional<String> getStationId() {
        try {
            InputStream inputStream = new FileInputStream(configDirectory + "/edge.json");
            StringBuilder stringBuilder = new StringBuilder();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine = "";
            while ((readLine = bufferedReader.readLine()) != null){
                stringBuilder.append(readLine);
            }
            inputStream.close();

            RegisterResponse result = objectMapper.readValue(stringBuilder.toString(), RegisterResponse.class);
            return Optional.of(result.getId());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void saveEdgeDetails(RegisterResponse registerResponse) {
        if (Objects.isNull(registerResponse)) {
            throw new RuntimeException("RegisterResponse cannot be null.");
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(configDirectory + "/edge.json");
            byte[] strToBytes = objectMapper.writeValueAsBytes(registerResponse);
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveEdgeKeystore(String keystore) {
        if (Objects.isNull(keystore)) {
            throw new RuntimeException("Keystore cannot be null.");
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(configDirectory + "/edge.ks");
            byte[] strToBytes = keystore.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveEdgeTruststore(String truststore) {
        if (Objects.isNull(truststore)) {
            throw new RuntimeException("Truststore cannot be null.");
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(configDirectory + "/edge.ts");
            byte[] strToBytes = truststore.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}