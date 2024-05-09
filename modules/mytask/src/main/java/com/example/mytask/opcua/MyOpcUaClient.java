package com.example.mytask.opcua;

import com.example.mytask.config.MyOpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Configuration
public class MyOpcUaClient {
    @Autowired
    public MyOpcUaClientConfig config;


    public UaClient connect() {
        OpcUaClientConfig opcUaClientConfig = null;
        while (opcUaClientConfig == null) {
            try {
                opcUaClientConfig = config.findOpcEndpointConfig();
            } catch (Exception e) {
                System.out.println("Exception: find Opc Endpoint");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        OpcUaClient opcUaClient = null;
        try {
            opcUaClient = OpcUaClient.create(opcUaClientConfig);
        } catch (UaException e) {
            System.out.println("create OpcUaClient error");
            e.printStackTrace();
        }


        UaClient uaClient = this.connect(opcUaClient);
        return uaClient;
    }

    private UaClient connect(OpcUaClient opcUaClient) {
        UaClient uaClient = null;
        while (uaClient == null) {
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
