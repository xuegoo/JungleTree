package im.octo.jungletree.network.http;

public interface HttpCallback {

    void done(String response);

    void error(Throwable t);
}
