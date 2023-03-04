package org.ir.analytics.model;

import java.text.MessageFormat;

public class Static {
    private String name;
    private String uscId;
    private String newsSite;
    private String numberOfThreads;

    public Static() {
    }

    public Static(String name, String uscId, String newsSite, String numberOfThreads) {
        this.name = name;
        this.uscId = uscId;
        this.newsSite = newsSite;
        this.numberOfThreads = numberOfThreads;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUscId() {
        return uscId;
    }

    public void setUscId(String uscId) {
        this.uscId = uscId;
    }

    public String getNewsSite() {
        return newsSite;
    }

    public void setNewsSite(String newsSite) {
        this.newsSite = newsSite;
    }

    public String getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(String numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Name: {0}\n", this.getName()) +
                MessageFormat.format("USC ID: {0}\n", this.getUscId()) +
                MessageFormat.format("News site crawled: {0}\n", this.getNewsSite()) +
                MessageFormat.format("Number of threads: {0}\n\n", this.getNumberOfThreads());
    }
}
