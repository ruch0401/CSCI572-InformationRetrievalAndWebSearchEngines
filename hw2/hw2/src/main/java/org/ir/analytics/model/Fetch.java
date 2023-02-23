package org.ir.analytics.model;

import java.text.MessageFormat;

public class Fetch {
    private int fetchCountAttempted;
    private int fetchCountSucceeded;
    private int fetchCountFailedOrAborted;

    public Fetch() {
    }

    public Fetch(int fetchCountAttempted, int fetchCountSucceeded, int fetchCountFailed) {
        this.fetchCountAttempted = fetchCountAttempted;
        this.fetchCountSucceeded = fetchCountSucceeded;
        this.fetchCountFailedOrAborted = fetchCountFailed;
    }

    public int getFetchCountAttempted() {
        return fetchCountAttempted;
    }

    public void setFetchCountAttempted(int fetchCountAttempted) {
        this.fetchCountAttempted = fetchCountAttempted;
    }

    public int getFetchCountSucceeded() {
        return fetchCountSucceeded;
    }

    public void setFetchCountSucceeded(int fetchCountSucceeded) {
        this.fetchCountSucceeded = fetchCountSucceeded;
    }

    public int getFetchCountFailedOrAborted() {
        return fetchCountFailedOrAborted;
    }

    public void setFetchCountFailedOrAborted(int fetchCountFailedOrAborted) {
        this.fetchCountFailedOrAborted = fetchCountFailedOrAborted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fetch Statistics: \n");
        sb.append("================\n");
        sb.append(MessageFormat.format("# fetches attempted: {0}\n", this.getFetchCountAttempted()));
        sb.append(MessageFormat.format("# fetches succeeded: {0}\n", this.getFetchCountSucceeded()));
        sb.append(MessageFormat.format("# fetches failed or aborted: {0}\n\n", this.getFetchCountFailedOrAborted()));
        return sb.toString();
    }
}
