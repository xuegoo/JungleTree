package org.jungletree.clientconnector.mcb.packet.handshake;

public class ConnectionInfo {

    private Chains tokenChain;
    private String clientDataToken;

    public Chains getTokenChain() {
        return tokenChain;
    }

    public void setTokenChain(Chains tokenChain) {
        this.tokenChain = tokenChain;
    }

    public String getClientDataToken() {
        return clientDataToken;
    }

    public void setClientDataToken(String clientDataToken) {
        this.clientDataToken = clientDataToken;
    }

    public static class Chains {

        private String[] chain;

        public String[] getChain() {
            return chain;
        }

        public void setChain(String[] chain) {
            this.chain = chain;
        }
    }
}
