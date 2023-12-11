package mqtttesting.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Table
@Entity
@Data
@AllArgsConstructor
@Builder
public class SensorTemperatura {
    @Id
    @SequenceGenerator(name="sensorTemperatura_sequence", sequenceName = "sensorTemperatura_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sensorTemperatura_sequence")
    private Integer idSensorTemp;
    private String modelo;
    private Float valor;
    private LocalDate fechaInicio;

    public SensorTemperatura() {
        fechaInicio = LocalDate.now();
    }

}

