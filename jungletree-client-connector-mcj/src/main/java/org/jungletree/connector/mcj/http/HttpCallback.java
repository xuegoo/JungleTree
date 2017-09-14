package org.jungletree.connector.mcj.http;

public interface HttpCallback {

    void done(String response);

    void error(Throwable t);
}
