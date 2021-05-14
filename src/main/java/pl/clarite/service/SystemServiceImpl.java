package pl.clarite.service;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
}