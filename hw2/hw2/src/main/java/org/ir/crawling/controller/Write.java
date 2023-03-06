package org.ir.crawling.controller;

import org.ir.crawling.model.CrawlStat;

import java.util.List;

public interface Write {
    <T extends CrawlStat> void write(List<T> stat);
}
