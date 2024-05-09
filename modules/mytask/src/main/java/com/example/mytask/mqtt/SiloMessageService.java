package com.example.mytask.mqtt;

import com.example.mytask.mapper.SiloMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SiloMessageService {
    @Autowired
    private SiloMessageMapper siloMessageMapper;

    public SiloMessageMapper getSiloMessageMapper() {
        return siloMessageMapper;
    }

    public void setSiloMessageMapper(SiloMessageMapper siloMessageMapper) {
        this.siloMessageMapper = siloMessageMapper;
    }
}
