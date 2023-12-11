package mqtttesting.demo.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import mqtttesting.demo.exception.ResourceNotFoundException;
import mqtttesting.demo.model.SensorTemperatura;
import mqtttesting.demo.model.request.STemperaturaRequest;
import mqtttesting.demo.repository.STRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class STService {
    @Autowired
    STRepository stRepository;
    @Autowired
    ObjectMapper objectMapper;

    private static final Logger logger = Logger.getLogger(STService.class);

    public boolean existsComponentById(Integer id) {
        Optional<SensorTemperatura> temperatura = stRepository.findById(id);
        return temperatura.isPresent();
    }
    private void checkIfProductoExistsOrThrow(Integer id) {
        if (!existsComponentById(id)) {
            throw new ResourceNotFoundException(
                    "El sensor de temperatura con el id [%s] NO EXISTE".formatted(id)
            );
        }
    }



    public STemperaturaRequest sensorTemperatura(STemperaturaRequest temperatura,
                                                 String payload,
                                                 MqttPahoMessageDrivenChannelAdapter testing
    ){
        System.out.println(inboundFlow(testing));
        return STemperaturaRequest.builder()
                .modelo("RD232")
                .valor(22.3)
                .build();
    }


    IntegrationFlow inboundFlow (MqttPahoMessageDrivenChannelAdapter inboundAdapter) {
        return IntegrationFlow
                .from(inboundAdapter)
                .handle((GenericHandler<String>)(payload, headers) ->{
                    headers.forEach((k, v) -> System.out.println(k + "=" + v));
                    return null;
                })
                .get();
    }

    MqttPahoMessageDrivenChannelAdapter inbounAdapter(MqttPahoClientFactory Factory) {
        return new MqttPahoMessageDrivenChannelAdapter("mqtt-api", Factory, "Sensores");
    }


}
