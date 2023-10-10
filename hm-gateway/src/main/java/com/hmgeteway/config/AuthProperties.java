package com.hmgeteway.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "hm.auth")
@Component
public class AuthProperties {
    private List<String> includePaths;
    private List<String> excludePaths;
}
