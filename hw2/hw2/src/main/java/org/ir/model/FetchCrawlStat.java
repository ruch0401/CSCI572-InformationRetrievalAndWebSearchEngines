package org.ir.model;

public class FetchCrawlStat extends CrawlStat {
    private int statusCode;

    public FetchCrawlStat(final String url, final int statusCode) {
        super(url);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public String toString() {
        return String.format("%s,%d", super.getUrl(), statusCode);
    }
}
