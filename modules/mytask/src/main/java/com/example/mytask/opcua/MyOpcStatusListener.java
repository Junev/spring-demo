package com.example.mytask.opcua;

import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription;
import org.eclipse.milo.opcua.sdk.client.subscriptions.ManagedSubscription.StatusListener;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;

public class MyOpcStatusListener implements StatusListener {
    private OpcService opcService;

    public MyOpcStatusListener(OpcService opcService) {
        this.opcService = opcService;
    }

    public void onSubscriptionTransferFailed(ManagedSubscription subscription,
                                             StatusCode statusCode) {
        System.out.println("onSubscriptionTransferFailed");
        try {
            opcService.subscribeNode();
        } catch (UaException e) {
            e.printStackTrace();
        }
    }
}
