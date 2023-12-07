package mqtttesting.demo.service;


import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Service;

@Service
public class TemperaturaService {

    IntegrationFlow inboundFlow (MqttPahoMessageDrivenChannelAdapter inboundAdapter) {
        return IntegrationFlow
                .from(inboundAdapter)
                .handle((GenericHandler<String>)(payload, headers) ->{
                    System.out.println("New Message!");
                    System.out.println("Valor Prueba: " + payload);
                    headers.forEach((k, v) -> System.out.println(k + "=" + v));
                    return null;
                })
                .get();
    }

    MqttPahoMessageDrivenChannelAdapter inbounAdapter(MqttPahoClientFactory Factory) {
        return new MqttPahoMessageDrivenChannelAdapter("jhonatan-IT", Factory, "Prueba");
    }
}
