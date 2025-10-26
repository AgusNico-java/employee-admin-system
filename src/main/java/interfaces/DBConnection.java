package interfaces;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "database")
public interface DBConnection {
    int port();
    String host();
    String db();
    String user();
    String password();
}
