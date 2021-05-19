package pl.clarite.controller;

import io.quarkus.arc.log.LoggerName;
import io.quarkus.scheduler.Scheduled;
import org.jboss.logging.Logger;
import pl.clarite.dto.GasOutputMessage;
import pl.clarite.service.GasService;

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
    TelemetrySender telemetrySender;

    @Scheduled(every = "{qiot.telemetry.gas.send.every}")
    void checkAndSendGasTelemetry() throws IOException {
        if (gasService.getStationId().equals("none")) {
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
}
