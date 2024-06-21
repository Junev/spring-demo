package com.example.mytask.service.caculate;

import com.example.mytask.opcua.MyOpcUaClient;
import com.example.mytask.service.OpcValueListener;
import com.example.mytask.service.ScanOpcService;
import com.example.repository.model.PdsEquipproperty;
import org.eclipse.milo.opcua.sdk.client.api.UaClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class CaculateAbstractListener implements OpcValueListener {
    private static final Logger logger
            = LoggerFactory.getLogger(ScanOpcService.class);
    @Autowired
    private MyOpcUaClient myUaClient;
    private UaClient client;

    private HashMap<String, FixedSizeQueue<PdsEquipproperty>> map = new HashMap<>();

    public void init() {
        client = myUaClient.connect();
    }

    @Override
    public void update(Map<String, PdsEquipproperty> eps, String unitId) {
//        if (unitId.startsWith("6150005203ZSC02")) {
            for (Map.Entry<String, PdsEquipproperty> entry : eps.entrySet()) {
                String key = entry.getKey();
                PdsEquipproperty ep = eps.get(key);
                map.computeIfAbsent(key, k -> new FixedSizeQueue<>(30));

                if (ep != null) {
                    FixedSizeQueue<PdsEquipproperty> queue = map.get(key);
                    queue.enqueue(ep);
                }
            }
//        }

        CompletableFuture<StatusCode> future = client.writeValue(
                NodeId.parse("ns=2;s=通道 1.设备 1.test0001"), DataValue.valueOnly(new Variant((float) 0)));
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("write value error ", e);
        }
    }
}
