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
    private HashMap<String, Integer> delayCount = new HashMap<>();

    public void init() {
        client = myUaClient.connect();
    }

    @Override
    public void update(Map<String, PdsEquipproperty> eps, String unitId) {
        String key1 = "u900201pt0201";
        PdsEquipproperty key1ep = eps.get(key1);
        
        map.computeIfAbsent(key1, k -> new FixedSizeQueue<>(30));
        // queue存储前30秒数据
        FixedSizeQueue<PdsEquipproperty> queue = map.get(key1);

        String key2 = "u900201pt0202";
        PdsEquipproperty key2ep = eps.get(key2);
        

        if (key1ep == null || key2ep == null) {
            return;
        }
        Float key1epValue = (Float) key1ep.getValue();
        Float key2epValue = (Float) key2ep.getValue();

        if (key1ep.getValue().equals(0) && key2ep.getValue().equals(0)) {
            map.remove(key1);
        }

        // 计算数据对齐延时
        //
        if (queue.size() != 0) {
            PdsEquipproperty lastEp = queue.getLast();
            if (lastEp != null) {
                if (lastEp.getValue().equals(0F) && !key1ep.getValue().equals(0F)) {
                    delayCount.put(key1, 0);
                } else {
                    // delayCount少1
                    if (!key1ep.getValue().equals(0F) && key2ep.getValue().equals(0F)) {
                        Integer i = delayCount.get(key1);
                        if (i != null) {
                            delayCount.put(key1, i + 1);
                        }
                    }
                }
            }
        }

        Float result = 0F;
        if (delayCount.get(key1) != null) {
            int ep1ccIndex = queue.size() - delayCount.get(key1) - 2;
            if (ep1ccIndex > -1 && ep1ccIndex < queue.size()) {
                PdsEquipproperty ep1acc = queue.get(ep1ccIndex);
                Float ep1accValue = (Float) ep1acc.getValue();
                result = key2epValue / ep1accValue;
            }
        }
        queue.enqueue(key1ep);

        System.out.println("key1: " + key1epValue + " key2: "+ key2epValue +
                " delay： " + delayCount.get(key1) +
                " result: " + result);
        if (key1ep.getValue() != null) {
            CompletableFuture<StatusCode> future = client.writeValue(
                    NodeId.parse("ns=2;s=simu.simu.test0001"), DataValue.valueOnly(new Variant(result)));
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("write value error ", e);
            }
        }
    }
}
