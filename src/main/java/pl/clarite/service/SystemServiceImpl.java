package pl.clarite.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.logging.Logger;
import pl.clarite.dto.RegisterResponse;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SystemServiceImpl implements SystemService {

    private static final Logger logger = Logger.getLogger(SystemServiceImpl.class);

    /**
     * Retrieves the serial number of edge device.
     *
     * @return Serial number of edge device or Optional.empty()
     */
    public Optional<String> getSerialNumber() {
        try {
            Process process;
            ProcessBuilder processBuilder = new ProcessBuilder()
                    .redirectErrorStream(false);

            logger.info("Starting a new getStationId process...");

            process = processBuilder
                    .command("/bin/sh", "-c", "cat /sys/firmware/devicetree/base/serial-number")
                    .start();

            logger.info("Is the getStationId process started? " + process.isAlive());

            BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = output.lines().collect(Collectors.joining());

            int exitVal = process.waitFor();
            if (exitVal >= 0) {
                return Optional.of(result);
            }

            return Optional.empty();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveEdgeDetails(RegisterResponse registerResponse) {
        if (Objects.isNull(registerResponse)) {
            throw new RuntimeException("RegisterResponse cannot be null.");
        }

        try {
            FileOutputStream outputStream = new FileOutputStream("/var/qiot/config/edge.json");
            ObjectMapper objectMapper = new ObjectMapper();
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
            FileOutputStream outputStream = new FileOutputStream("/var/qiot/config/edge.ks");
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
            FileOutputStream outputStream = new FileOutputStream("/var/qiot/config/edge.ts");
            byte[] strToBytes = truststore.getBytes();
            outputStream.write(strToBytes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}