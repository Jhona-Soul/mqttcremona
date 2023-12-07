package mqtttesting.demo.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
@Configuration
public class TemperaturaService {
    @Bean
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

    @Bean
    MqttPahoMessageDrivenChannelAdapter inbounAdapter(MqttPahoClientFactory Factory) {
        return new MqttPahoMessageDrivenChannelAdapter("JHONATAN", Factory, "Nivel");
    }
}
