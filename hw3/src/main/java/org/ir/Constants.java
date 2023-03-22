package org.ir;

import java.util.List;

public class Constants {
    public static final String REGEX_FILTER_ALL_NON_ALPHABETS = "[^a-zA-Z\\s]";
    public static final String SPACE_CHARACTER = " ";
    public static final String TAB_CHARACTER = "\t";
    public static final String ONE_OR_MORE_SPACE_REGEX = "\\s+";
    public static final String COLON_CHARACTER = ":";
    public static final String BIGRAM_COMPUTER_SCIENCE = "computer science";
    public static final String BIGRAM_INFORMATION_RETRIEVAL = "information retrieval";
    public static final String BIGRAM_POWER_POLITICS = "power politics";
    public static final String BIGRAM_LOS_ANGELES = "los angeles";
    public static final String BIGRAM_BRUCE_WILLIS = "bruce willis";
    public static final List<String> CHOSEN_BIGRAMS = List.of(BIGRAM_COMPUTER_SCIENCE, BIGRAM_INFORMATION_RETRIEVAL, BIGRAM_POWER_POLITICS, BIGRAM_LOS_ANGELES, BIGRAM_BRUCE_WILLIS);

}
