package mqtttesting.demo;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.core.GenericHandler;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
public class MqttTestingApplication {

	@Value("${mqtt.server}")
	private String ipServerBroker;
	@Value("${mqtt.port}")
	private String brokerPort;

	public static void main(String[] args) {
		SpringApplication.run(MqttTestingApplication.class, args);
	}
	@Primary
	@Bean
	MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();
		options.setServerURIs(new String[] {"tcp://"+ipServerBroker+":"+brokerPort});
		factory.setConnectionOptions(options);
		return factory;
	}

	@Configuration
	class OutConfiguration {
		@Primary
		@Bean
		RouterFunction<ServerResponse> http(MessageChannel out) {
			return route()
					.GET("/send/{name}", request -> {
						var name = request.pathVariable("name");
						var message = MessageBuilder.withPayload("Hey, HiveMQ!" + " "+ name).build();
						out.send(message);
						System.out.println(message);
						return ServerResponse.ok().build();
					})
					.build();
		}
		@Primary
		@Bean
		public MessageChannel mqttOutboundChannel() {
			return MessageChannels.direct().getObject();
		}
		@Bean
		MqttPahoMessageHandler outBoundAdapter(MqttPahoClientFactory Factory) {
			var mh = new MqttPahoMessageHandler("Producer", Factory);
			mh.setDefaultTopic("mytopic");
			return mh;
		}
		@Bean
		IntegrationFlow outboundFlow(MessageChannel out,
									 MqttPahoMessageHandler outboundAdapter) {
			return IntegrationFlow
					.from(out)
					.handle(outboundAdapter)
					.get();
		}
	}


	//----SUBS
	@Configuration
	class InConfiguration {
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
			return new MqttPahoMessageDrivenChannelAdapter("JHONATAN", Factory, "Sensores");
		}


		/*
		* New Message!
		Valor Prueba: 55,18
		mqtt_receivedRetained=false
		mqtt_id=0
		mqtt_duplicate=false
		id=096d4401-2b02-109c-9a32-101775b57127
		mqtt_receivedTopic=Sensores
		mqtt_receivedQos=0
		timestamp=1701962870561
		* */

	}

}
