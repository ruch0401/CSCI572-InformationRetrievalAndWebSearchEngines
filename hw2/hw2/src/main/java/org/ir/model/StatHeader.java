package org.ir.model;

public enum StatHeader {
    URL("url"),
    STATUS_CODE("status_code"),
    SIZE_OF_DOWNLOADED_PAGE("size_of_downloaded_page"),
    NUMBER_OF_OUTLINKS("number_of_outlinks"),
    CONTENT_TYPE("content_type");

    public final String header;

    StatHeader(String header) {
        this.header = header;
    }
}