package org.ir.model;

public class FetchCrawlStat {
    private String url;
    private int statusCode;

    private String urlHeaderName = "URL";
    private String statusCodeHeaderName = "Status Code";

    public FetchCrawlStat(String url, int statusCode) {
        this.url = url;
        this.statusCode = statusCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getUrlHeaderName() {
        return urlHeaderName;
    }

    public void setUrlHeaderName(String urlHeaderName) {
        this.urlHeaderName = urlHeaderName;
    }

    public String getStatusCodeHeaderName() {
        return statusCodeHeaderName;
    }

    public void setStatusCodeHeaderName(String statusCodeHeaderName) {
        this.statusCodeHeaderName = statusCodeHeaderName;
    }

    @Override
    public String toString() {
        return String.format("%s,%d", url, statusCode);
    }
}
