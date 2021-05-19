package pl.clarite.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SerialNumberResponseDto {
    private String serialNumber;
}
