package mqtttesting.demo.model.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class STemperaturaRequest {
    private Integer idSensorTemp;
    private String modelo;
    private Double valor;
}
