#!/bin/bash

# remove the /data directory from HDFS
echo "Removing /data directory from HDFS"
hadoop fs -rm -r -skipTrash /data

# remove the /selected_bigram_index.txt file from HDFS
echo "Removing /selected_bigram_index.txt file from HDFS"
hadoop fs -rm -r -skipTrash /unigram_index.txt

# remove the /selected_bigram_index.txt file from HDFS
echo "Removing /selected_bigram_index.txt file from HDFS"
hadoop fs -rm -r -skipTrash /selected_bigram_index.txt

# add the /data directory to HDFS
echo "Adding /data directory to HDFS as seed data"
hadoop fs -put data /

# homework requirement start
# execute the MapReduce job to generate unigram inverted index on original full data
echo "Executing the MapReduce job to generate unigram inverted index on original full data"
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.UnigramRunner /data/original/fulldata/ /unigram_index.txt

# execute the MapReduce job to generate bigram inverted index on original dev data
echo "Executing the MapReduce job to generate bigram inverted index on original dev data"
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.BigramRunner /data/original/devdata/ /selected_bigram_index.txt
# homework requirement end

# additional executions start
# execute the MapReduce job to generate unigram inverted index on original dev data
echo "Executing the MapReduce job to generate unigram inverted index on original dev data"
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.UnigramRunner /data/original/devdata/ /unigram_index_original_devdata.txt

# execute the MapReduce job to generate bigram inverted index on original full data
echo "Executing the MapReduce job to generate bigram inverted index on original full data"
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.BigramRunner /data/original/fulldata/ /selected_bigram_index_original_fulldata.txt

# execute the MapReduce job to generate unigram inverted index on small dev data
echo "Executing the MapReduce job to generate unigram inverted index on small dev data"
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.UnigramRunner /data/small/devdata/ /unigram_index_small_devdata.txt

# execute the MapReduce job to generate unigram inverted index on small full data
echo "Executing the MapReduce job to generate unigram inverted index on small full data"
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.UnigramRunner /data/small/fulldata/ /unigram_index_small_fulldata.txt

# execute the MapReduce job to generate bigram inverted index on small dev data
echo "Executing the MapReduce job to generate bigram inverted index on small dev data"
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.BigramRunner /data/small/devdata/ /selected_bigram_index_small_devdata.txt

# execute the MapReduce job to generate bigram inverted index on small full data
echo "Executing the MapReduce job to generate bigram inverted index on small full data"
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.BigramRunner /data/small/fulldata/ /selected_bigram_index_small_fulldata.txt
# additional executions end