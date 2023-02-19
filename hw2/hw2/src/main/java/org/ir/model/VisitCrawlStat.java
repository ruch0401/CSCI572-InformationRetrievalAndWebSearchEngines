package org.ir.model;

public class VisitCrawlStat {
    private String url;
    private long downloadedSize;
    private int numberOfOutlinks;
    private String contentType;

    public VisitCrawlStat(String url, long downloadedSize, int numberOfOutlinks, String contentType) {
        this.url = url;
        this.downloadedSize = downloadedSize;
        this.numberOfOutlinks = numberOfOutlinks;
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

    public int getNumberOfOutlinks() {
        return numberOfOutlinks;
    }

    public void setNumberOfOutlinks(int numberOfOutlinks) {
        this.numberOfOutlinks = numberOfOutlinks;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return String.format("%s,%d,%d,%s", this.getUrl(), this.getDownloadedSize(), this.getNumberOfOutlinks(), this.getContentType());
    }
}
