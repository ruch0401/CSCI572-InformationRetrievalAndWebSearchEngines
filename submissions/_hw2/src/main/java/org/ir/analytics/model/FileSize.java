package org.ir.analytics.model;

import java.text.MessageFormat;

public class FileSize {
    private int lessThanOneKB;
    private int betweenOneAndTenKB;
    private int betweenTenAndHundredKB;
    private int betweenHundredAndThousandKB;
    private int moreThanEqualToThousandKB;

    public FileSize() {
    }

    public FileSize(int lessThanOneKB, int betweenOneAndTenKB, int betweenTenAndHundredKB, int betweenHundredAndThousandKB, int moreThanEqualToThousandKB) {
        this.lessThanOneKB = lessThanOneKB;
        this.betweenOneAndTenKB = betweenOneAndTenKB;
        this.betweenTenAndHundredKB = betweenTenAndHundredKB;
        this.betweenHundredAndThousandKB = betweenHundredAndThousandKB;
        this.moreThanEqualToThousandKB = moreThanEqualToThousandKB;
    }

    public int getLessThanOneKB() {
        return lessThanOneKB;
    }

    public void setLessThanOneKB(int lessThanOneKB) {
        this.lessThanOneKB = lessThanOneKB;
    }

    public int getBetweenOneAndTenKB() {
        return betweenOneAndTenKB;
    }

    public void setBetweenOneAndTenKB(int betweenOneAndTenKB) {
        this.betweenOneAndTenKB = betweenOneAndTenKB;
    }

    public int getBetweenTenAndHundredKB() {
        return betweenTenAndHundredKB;
    }

    public void setBetweenTenAndHundredKB(int betweenTenAndHundredKB) {
        this.betweenTenAndHundredKB = betweenTenAndHundredKB;
    }

    public int getBetweenHundredAndThousandKB() {
        return betweenHundredAndThousandKB;
    }

    public void setBetweenHundredAndThousandKB(int betweenHundredAndThousandKB) {
        this.betweenHundredAndThousandKB = betweenHundredAndThousandKB;
    }

    public int getMoreThanEqualToThousandKB() {
        return moreThanEqualToThousandKB;
    }

    public void setMoreThanEqualToThousandKB(int moreThanEqualToThousandKB) {
        this.moreThanEqualToThousandKB = moreThanEqualToThousandKB;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("File Sizes: \n");
        sb.append("===========\n");
        sb.append(MessageFormat.format("< 1KB: {0}\n", this.getLessThanOneKB()));
        sb.append(MessageFormat.format("1KB ~ <10KB: {0}\n", this.getBetweenOneAndTenKB()));
        sb.append(MessageFormat.format("10KB ~ <100KB: {0}\n", this.getBetweenTenAndHundredKB()));
        sb.append(MessageFormat.format("100KB ~ <1MB: {0}\n", this.getBetweenHundredAndThousandKB()));
        sb.append(MessageFormat.format(">= 1MB: {0}\n\n", this.getMoreThanEqualToThousandKB()));
        return sb.toString();
    }
}
