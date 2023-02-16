package org.ir.model;

public class AlphaCrawlerPojo {
    private String seedUrl;

    public AlphaCrawlerPojo(String host) {
        this.seedUrl = host;
    }

    public String getSeedUrl() {
        return seedUrl;
    }

    public void setSeedUrl(String seedUrl) {
        this.seedUrl = seedUrl;
    }
}
