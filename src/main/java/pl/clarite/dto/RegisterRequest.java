package pl.clarite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@JsonPropertyOrder({ "serial", "name", "longitude", "latitude",
        "keyStorePassword" })
@Data
@Builder
public class RegisterRequest {
    @JsonProperty(value = "serial", required = true)
    private String serial;

    @JsonProperty(value = "name", required = true)
    @NotNull
    @Pattern(regexp = "[a-z0-9]([-a-z0-9]*[a-z0-9])?(\\.[a-z0-9]([-a-z0-9]*[a-z0-9])?)*")
    private String name;

    @JsonProperty(value = "longitude", required = true)
    @DecimalMin(value="-180.0")
    @DecimalMax(value="180.0")
    @NotNull
    private double longitude;

    @JsonProperty(value = "latitude", required = true)
    @DecimalMin(value="-90.0")
    @DecimalMax(value="90.0")
    @NotNull
    private double latitude;

    @JsonProperty("keyStorePassword")
    private String keyStorePassword;
}
