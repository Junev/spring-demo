package com.example.mytask.config;

import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MyOpcUaClientConfig {
    @Autowired
    private OpcUaClientConfigUrl configUrl;

    public OpcUaClientConfig findOpcEndpointConfig() throws Exception {
        List<EndpointDescription> endpointDescriptions =
                DiscoveryClient.getEndpoints(configUrl.getUrl())
                        .get();
        EndpointDescription endpoint = endpointDescriptions
                .stream()
                .filter(c -> c.getSecurityPolicyUri()
                        .equals("http://opcfoundation.org/UA/SecurityPolicy#None"))
                .findFirst().orElseThrow(() -> new Exception("Opc endpoint not found."));

        OpcUaClientConfigBuilder builder = new OpcUaClientConfigBuilder();
        builder.setApplicationName(LocalizedText.english("opcua:silo"))
                .setApplicationUri("urn:eclipse:milo:examples:client")
                .setEndpoint(endpoint)
                .setKeepAliveFailuresAllowed(UInteger.valueOf(0))
                .setIdentityProvider(new UsernameProvider(configUrl.getUsername(),
                        configUrl.getPassword()));
        return builder.build();
    }

}
