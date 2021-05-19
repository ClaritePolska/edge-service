package pl.clarite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;
import java.time.ZonedDateTime;

@JsonPropertyOrder({ "stationId", "instant", "adc", "nh3", "oxidising", "reducing" })
@Data
@Builder
public class GasOutputMessage {
    @JsonProperty(value = "stationId", required = true)
    private String stationId;

    @JsonProperty(value = "instant", required = true)
    private String instant;

    @JsonProperty(value = "adc", required = true)
    private double adc;

    @JsonProperty(value = "nh3", required = true)
    private double nh3;

    @JsonProperty(value = "oxidising", required = true)
    private double oxidising;

    @JsonProperty(value = "reducing", required = true)
    private double reducing;
}
