package main.java.compactdisc;

import org.springframework.stereotype.Component;

@Component
public class SgtPeppers implements CompactDisc {
    @Override
    public void play() {
        System.out.println("Playing Sgt.peppers Lonely Hearts Club Band");
    }
}
