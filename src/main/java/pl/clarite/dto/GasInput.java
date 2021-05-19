package pl.clarite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@JsonPropertyOrder({ "dateTime", "Oxidising", "Reducing", "NH3" })
@Data
@Builder
public class GasInput {
    @JsonProperty(value = "dateTime", required = true)
    private OffsetDateTime dateTime;

    @JsonProperty(value = "Oxidising", required = true)
    private double oxidising;

    @JsonProperty(value = "Reducing", required = true)
    private double reducing;

    @JsonProperty(value = "NH3", required = true)
    private double nh3;
}
