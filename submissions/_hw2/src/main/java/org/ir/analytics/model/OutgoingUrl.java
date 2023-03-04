package org.ir.analytics.model;

import java.text.MessageFormat;

public class OutgoingUrl {
    private long totalUrlsExtracted;
    private long uniqueUrlsExtractedCount;
    private long uniqueUrlsWithinNewsSiteCount;
    private long uniqueUrlsOutsideNewsSiteCount;

    public OutgoingUrl() {
    }

    public OutgoingUrl(long totalUrlsExtracted, long uniqueUrlsExtractedCount, long uniqueUrlsWithinNewsSiteCount, long uniqueUrlsOutsideNewsSiteCount) {
        this.totalUrlsExtracted = totalUrlsExtracted;
        this.uniqueUrlsExtractedCount = uniqueUrlsExtractedCount;
        this.uniqueUrlsWithinNewsSiteCount = uniqueUrlsWithinNewsSiteCount;
        this.uniqueUrlsOutsideNewsSiteCount = uniqueUrlsOutsideNewsSiteCount;
    }

    public long getTotalUrlsExtracted() {
        return totalUrlsExtracted;
    }

    public void setTotalUrlsExtracted(long totalUrlsExtracted) {
        this.totalUrlsExtracted = totalUrlsExtracted;
    }

    public long getUniqueUrlsExtractedCount() {
        return uniqueUrlsExtractedCount;
    }

    public void setUniqueUrlsExtractedCount(long uniqueUrlsExtractedCount) {
        this.uniqueUrlsExtractedCount = uniqueUrlsExtractedCount;
    }

    public long getUniqueUrlsWithinNewsSiteCount() {
        return uniqueUrlsWithinNewsSiteCount;
    }

    public void setUniqueUrlsWithinNewsSiteCount(long uniqueUrlsWithinNewsSiteCount) {
        this.uniqueUrlsWithinNewsSiteCount = uniqueUrlsWithinNewsSiteCount;
    }

    public long getUniqueUrlsOutsideNewsSiteCount() {
        return uniqueUrlsOutsideNewsSiteCount;
    }

    public void setUniqueUrlsOutsideNewsSiteCount(long uniqueUrlsOutsideNewsSiteCount) {
        this.uniqueUrlsOutsideNewsSiteCount = uniqueUrlsOutsideNewsSiteCount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Outgoing URLs: \n");
        sb.append("================\n");
        sb.append(MessageFormat.format("Total URLs extracted: {0}\n", this.getTotalUrlsExtracted()));
        sb.append(MessageFormat.format("# unique URLs extracted: {0}\n", this.getUniqueUrlsExtractedCount()));
        sb.append(MessageFormat.format("# unique URLs within News Site: : {0}\n", this.getUniqueUrlsWithinNewsSiteCount()));
        sb.append(MessageFormat.format("# unique URLs outside News Site:: {0}\n\n", this.getUniqueUrlsOutsideNewsSiteCount()));
        return sb.toString();
    }
}
