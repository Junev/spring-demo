package com.example.mytask.utils;

import com.example.repository.model.PdsEquipproperty;
import org.eclipse.milo.opcua.stack.core.UaRuntimeException;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;

import java.util.List;
import java.util.stream.Collectors;

public class PdsEquipproptyUtils {

    public static List<NodeId> getNodeIds(List<PdsEquipproperty> allUnitEps) {
        List<NodeId> allUnitNodeIds = allUnitEps.stream()
                .map(c -> {
                    try {
                        return NodeId.parse(c.getTagaddress());
                    } catch (UaRuntimeException e) {
                        System.out.println("Tag Address parse Exception: " + c.getPropertyid() +
                                "_" + c.getTagaddress());
//                        e.printStackTrace();
                        return NodeId.parseOrNull(c.getTagaddress());
                    } catch (NullPointerException e) {
                        System.out.println("Null tag Address: " + c.getPropertyid());
                        return null;
                    }
                })
                .collect(Collectors.toList());
        return allUnitNodeIds;
    }
}
