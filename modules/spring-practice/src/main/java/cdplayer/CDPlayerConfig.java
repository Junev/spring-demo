package main.java.cdplayer;

import main.java.compactdisc.SgtPeppers;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = SgtPeppers.class)
public class CDPlayerConfig {
}
