package interfaces;

import io.smallrye.config.ConfigMapping;

import java.util.Map;

@ConfigMapping(prefix = "endpoints")
public interface Endpoints {
    Map<String, String> get();
    Map<String, String> post();
    Map<String, String> update();
}
