package pl.clarite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@JsonPropertyOrder({ "id", "truststore", "keystore" })
@Data
@Builder
public class RegisterResponse {
    @JsonProperty("id")
    private String id;
    @JsonProperty("truststore")
    private String truststore;
    @JsonProperty("keystore")
    private String keystore;
}
