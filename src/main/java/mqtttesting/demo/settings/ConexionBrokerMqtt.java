package mqtttesting.demo.settings;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
@Configuration
public class ConexionBrokerMqtt {

    MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] {"tcp://192.168.10.110:1883"});
        options.setUserName("admin");
        options.setPassword("admin".toCharArray());

        factory.setConnectionOptions(options);

        return factory;
    }



}
