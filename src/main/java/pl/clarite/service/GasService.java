package pl.clarite.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.arc.log.LoggerName;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import pl.clarite.dto.GasInput;
import pl.clarite.dto.GasOutputMessage;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class GasService {

    @LoggerName("GasService")
    Logger log;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    SystemService systemService;

    @ConfigProperty(name = "qiot.telemetry.gas.input.path")
    String gasInputPath;

    public List<Path> getDataList() throws IOException {
        try (final Stream<Path> fileStream = Files.list(Paths.get(gasInputPath))) {
            return fileStream
                    .map(Path::toFile)
                    .sorted(Comparator.comparing(File::lastModified))
                    .map(File::toPath)
                    .collect(Collectors.toList());
        }
    }

    public GasOutputMessage getData(Path inputFile) throws IOException {
        log.info("read Gas data from file: " + inputFile);
        GasInput inputData = objectMapper.readValue(inputFile.toFile(), GasInput.class);

        String stationId = systemService.getStationId().orElseThrow(() -> new RuntimeException("Cannot retrieve station id of registered the edge device."));

        GasOutputMessage output = GasOutputMessage.builder().stationId(stationId).instant(inputData.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME))
                .nh3(inputData.getNh3()).oxidising(inputData.getOxidising()).reducing(inputData.getReducing()).build();
        Files.delete(inputFile);
        return output;
    }
}
