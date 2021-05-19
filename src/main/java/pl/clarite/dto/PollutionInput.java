package pl.clarite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@JsonPropertyOrder({ "dateTime", "pm1_0", "pm2_5", "pm10", "pm1_0_atm", "pm2_5_atm", "pm10_atm", "gt0_3um", "gt0_5um"
        ,"gt1_0um", "gt2_5um", "gt5_0um", "gt10um"})
@Data
@Builder
public class PollutionInput {
    @JsonProperty(value = "dateTime", required = true)
    private OffsetDateTime dateTime;

    @JsonProperty(value = "pm1_0", required = true)
    private int pm1_0;

    @JsonProperty(value = "pm2_5", required = true)
    private int pm2_5;

    @JsonProperty(value = "pm10", required = true)
    private int pm10;

    @JsonProperty(value = "pm1_0_atm", required = true)
    private int pm1_0_atm;

    @JsonProperty(value = "pm2_5_atm", required = true)
    private int pm2_5_atm;

    @JsonProperty(value = "pm10_atm", required = true)
    private int pm10_atm;

    @JsonProperty(value = "gt0_3um", required = true)
    private int gt0_3um;

    @JsonProperty(value = "gt0_5um", required = true)
    private int gt0_5um;

    @JsonProperty(value = "gt1_0um", required = true)
    private int gt1_0um;

    @JsonProperty(value = "gt2_5um", required = true)
    private int gt2_5um;

    @JsonProperty(value = "gt5_0um", required = true)
    private int gt5_0um;

    @JsonProperty(value = "gt10um", required = true)
    private int gt10um;
}
