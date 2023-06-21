package com.agency.travel2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {

    private String name;
    private String environment;
    private boolean enabled;
    private List<String> servers = new ArrayList<>();

    // standard getters and setters

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEnvironment() {
        return this.environment;
    }
    public void setEnvironment(String environment) {
        this.environment = environment;
    }
    public boolean getEnabled() {
        return this.enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public List<String> getServers() {
        return this.servers;
    }
    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    private String version;
    public String getVersion() {
        return this.version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    @Override
    public String toString() {
        return "YAMLConfig [name=" + name + ", version=" + version + ", environment=" + environment + ", enabled=" + enabled + ", servers="
                + servers + "]";
    }

}