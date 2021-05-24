package pl.clarite.controller;

import io.quarkus.arc.log.LoggerName;
import io.quarkus.scheduler.Scheduled;
import org.jboss.logging.Logger;
import pl.clarite.dto.GasOutputMessage;
import pl.clarite.dto.PollutionInput;
import pl.clarite.dto.PollutionOutputMessage;
import pl.clarite.service.GasService;
import pl.clarite.service.PollutionService;
import pl.clarite.service.SystemService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@ApplicationScoped
public class TelemetryScheduler {

    @LoggerName("TelemetryScheduler")
    Logger log;

    @Inject
    GasService gasService;

    @Inject
    SystemService systemService;

    @Inject
    PollutionService pollutionService;

    @Inject
    TelemetrySender telemetrySender;

    @Scheduled(every = "{qiot.telemetry.gas.send.every}")
    void checkAndSendGasTelemetry() throws IOException {
        if (systemService.getStationId().isEmpty()) {
            log.info("Device is waiting for registration");
            return;
        }
        log.info("Collecting Gas data from input folder");
        List<Path> fileList = gasService.getDataList();
        log.info("Gas list size: " + fileList.size());
        fileList.forEach(file -> {
            try {
                GasOutputMessage out = gasService.getData(file);
                log.info("Gas data: " + out);
                telemetrySender.sendGasData(out);
            } catch (IOException e) {
                log.error("cannot read Gas data from file: " + file, e);
            }
        });
    }

    @Scheduled(every = "{qiot.telemetry.pollution.send.every}")
    void checkAndSendPollutionTelemetry() throws IOException {
        if (systemService.getStationId().isEmpty()) {
            log.info("Device is waiting for registration");
            return;
        }
        log.info("Collecting Pollution data from input folder");
        List<Path> fileList = pollutionService.getDataList();
        log.info("Pollution list size: " + fileList.size());
        fileList.forEach(file -> {
            try {
                PollutionOutputMessage out = pollutionService.getData(file);
                log.info("Pollution data: " + out);
                telemetrySender.sendPollutionData(out);
            } catch (IOException e) {
                log.error("cannot read Pollution data from file: " + file, e);
            }
        });
    }

}
