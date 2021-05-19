package pl.clarite.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.arc.log.LoggerName;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import pl.clarite.dto.GasInput;
import pl.clarite.dto.GasOutputMessage;
import pl.clarite.dto.PollutionInput;
import pl.clarite.dto.PollutionOutputMessage;

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
public class PollutionService {
    @LoggerName("GasService")
    Logger log;

    @Inject
    ObjectMapper objectMapper;

    @ConfigProperty(name = "qiot.telemetry.pollution.input.path")
    String gasInputPath;

    @Getter
    @ConfigProperty(name = "qiot.telemetry.serialid")
    String stationId;

    public List<Path> getDataList() throws IOException {
        try (final Stream<Path> fileStream = Files.list(Paths.get(gasInputPath))) {
            return fileStream
                    .map(Path::toFile)
                    .sorted(Comparator.comparing(File::lastModified))
                    .map(File::toPath)
                    .collect(Collectors.toList());
        }
    }

    public PollutionOutputMessage getData(Path inputFile) throws IOException {
        log.info("read Pollution data from file: " + inputFile);
        PollutionInput inputData = objectMapper.readValue(inputFile.toFile(), PollutionInput.class);
        PollutionOutputMessage output = PollutionOutputMessage.builder().stationId(stationId).instant(inputData.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME))
                .pm1_0(inputData.getPm1_0()).pm2_5(inputData.getPm2_5()).pm10(inputData.getPm10()).pm1_0_atm(inputData.getPm1_0_atm())
                .pm2_5_atm(inputData.getPm2_5_atm()).pm10_atm(inputData.getPm10_atm()).gt0_3um(inputData.getGt0_3um())
                .gt0_5um(inputData.getGt0_5um()).gt1_0um(inputData.getGt1_0um()).gt2_5um(inputData.getGt2_5um())
                .gt5_0um(inputData.getGt5_0um()).gt10um(inputData.getGt10um()).build();
        Files.delete(inputFile);
        return output;
    }

}
