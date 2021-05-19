package pl.clarite.controller;

import io.quarkus.arc.log.LoggerName;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;
import pl.clarite.dto.GasOutputMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;

@ApplicationScoped
public class TelemetrySender {

    @LoggerName("TelemetrySender")
    Logger log;

    @Inject @Channel("gas-producer")
    Emitter<GasOutputMessage> gasEmitter;

    public void sendGasData(GasOutputMessage data) {
        log.info("invoked sendGasData()");
        gasEmitter.send(data);
    }
}
