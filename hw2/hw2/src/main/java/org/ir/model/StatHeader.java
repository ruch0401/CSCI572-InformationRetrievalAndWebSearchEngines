package org.ir.model;

public enum StatHeader {
    URL("url"),
    STATUS_CODE("status_code"),
    SIZE_OF_DOWNLOADED_PAGE("size_of_downloaded_page (in bytes)"),
    NUMBER_OF_OUTLINKS("number_of_outlinks"),
    CONTENT_TYPE("content_type"),
    INDICATOR("indicator");

    public final String header;

    StatHeader(String header) {
        this.header = header;
    }
}