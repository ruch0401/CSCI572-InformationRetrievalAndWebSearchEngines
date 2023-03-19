#!/bin/bash

# remove the /hw3-test/ directory from HDFS
hadoop fs -rm -r -skipTrash /hw3-test/

# remove the /unigram_index.txt file from HDFS
hadoop fs -rm -r -skipTrash /unigram_index.txt

# copy the local ./hw3-test/ directory to HDFS root directory
hadoop fs -put src/main/resources/hw3-test /

