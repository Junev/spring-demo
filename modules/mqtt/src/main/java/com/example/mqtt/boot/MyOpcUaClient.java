package com.example.mqtt.boot;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Configuration
public class MyOpcUaClient {
    @Autowired
    public OpcUaClientConfig config;

    @Bean
    public UaClient opcUaClient() {
        OpcUaClient opcUaClient = null;
        try {
            opcUaClient = OpcUaClient.create(this.config);
        } catch (UaException e) {
            System.out.println("create OpcUaClient error");
            e.printStackTrace();
        }

        UaClient uaClient = this.connect(opcUaClient);
        return uaClient;
    }

    private UaClient connect(OpcUaClient opcUaClient) {
        UaClient uaClient = null;
        for (int i = 3; i > 0; i--) {
            try {
                uaClient = opcUaClient.connect().get(5000L, TimeUnit.MILLISECONDS);
                if (uaClient != null) {
                    break;
                }
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        return uaClient;
    }
}
