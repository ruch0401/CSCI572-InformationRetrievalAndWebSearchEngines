package org.ir.crawling.model;

public class UrlCrawlStat extends CrawlStat {
    private String sameDomainIndicator;

    public UrlCrawlStat(String url, String sameDomainIndicator) {
        super(url);
        this.sameDomainIndicator = sameDomainIndicator;
    }

    public String getSameDomainIndicator() {
        return sameDomainIndicator;
    }

    public void setSameDomainIndicator(String sameDomainIndicator) {
        this.sameDomainIndicator = sameDomainIndicator;
    }

    @Override
    public String toString() {
        return String.format("%s,%s", super.getUrl(), this.getSameDomainIndicator());
    }
}
