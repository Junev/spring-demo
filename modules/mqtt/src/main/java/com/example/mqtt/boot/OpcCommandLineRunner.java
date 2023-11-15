package com.example.mqtt.boot;

import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@Component
public class OpcCommandLineRunner implements CommandLineRunner {
    @Autowired
    private UaClient client;

    @Autowired
    private OpcService opcService;

    @Override
    public void run(String... args) throws Exception {

        try {
            final CountDownLatch eventCountDownLatch = new CountDownLatch(1);
            this.opcService.subscribeNode();
            eventCountDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readValues() throws ExecutionException, InterruptedException {
        int namespaceIndex = 2;
        String identifier = "GX.GX.DB57,REAL976";
        NodeId nodeId = new NodeId(namespaceIndex, identifier);
        System.out.println(
                client.readValue(0.0, TimestampsToReturn.Neither, nodeId).get().getValue());
    }
}
