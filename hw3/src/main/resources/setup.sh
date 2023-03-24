#!/bin/bash

# remove the /data directory from HDFS
echo "========================================================================================"
echo "Removing /data directory from HDFS"
echo "========================================================================================"
hadoop fs -rm -r -skipTrash /data

# remove the /output folder from HDFS
echo "========================================================================================"
echo "Removing /output folder from HDFS"
echo "========================================================================================"
hadoop fs -rm -r -skipTrash /output

# add the /data directory to HDFS
echo "========================================================================================"
echo "Adding /data directory to HDFS as seed data"
echo "========================================================================================"
hadoop fs -put data /

# add the /output folder to HDFS
echo "========================================================================================"
echo "Add /output folder to HDFS"
echo "========================================================================================"
hadoop fs -put output /

# homework requirement start
# execute the MapReduce job to generate unigram inverted index on original full data
echo "=============================================================================================="
echo "[1/8] Executing the MapReduce job to generate unigram inverted index on original full data"
echo "=============================================================================================="
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.UnigramRunner /data/original/fulldata/ /output/unigram_index.txt

# execute the MapReduce job to generate bigram inverted index on original dev data
echo "=============================================================================================="
echo "[2/8] Executing the MapReduce job to generate bigram inverted index on original dev data"
echo "=============================================================================================="
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.BigramRunner /data/original/devdata/ /output/selected_bigram_index.txt
# homework requirement end

# additional executions start
# execute the MapReduce job to generate unigram inverted index on original dev data
echo "=============================================================================================="
echo "[3/8] Executing the MapReduce job to generate unigram inverted index on original dev data"
echo "=============================================================================================="
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.UnigramRunner /data/original/devdata/ /output/unigram_index_original_devdata.txt

# execute the MapReduce job to generate bigram inverted index on original full data
echo "=============================================================================================="
echo "[4/8] Executing the MapReduce job to generate bigram inverted index on original full data"
echo "=============================================================================================="
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.BigramRunner /data/original/fulldata/ /output/selected_bigram_index_original_fulldata.txt

# execute the MapReduce job to generate unigram inverted index on small dev data
echo "=============================================================================================="
echo "[5/8] Executing the MapReduce job to generate unigram inverted index on small dev data"
echo "=============================================================================================="
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.UnigramRunner /data/small/devdata/ /output/unigram_index_small_devdata.txt

# execute the MapReduce job to generate unigram inverted index on small full data
echo "=============================================================================================="
echo "[6/8] Executing the MapReduce job to generate unigram inverted index on small full data"
echo "=============================================================================================="
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.UnigramRunner /data/small/fulldata/ /output/unigram_index_small_fulldata.txt

# execute the MapReduce job to generate bigram inverted index on small dev data
echo "=============================================================================================="
echo "[7/8] Executing the MapReduce job to generate bigram inverted index on small dev data"
echo "=============================================================================================="
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.BigramRunner /data/small/devdata/ /output/selected_bigram_index_small_devdata.txt

# execute the MapReduce job to generate bigram inverted index on small full data
echo "=============================================================================================="
echo "[8/8] Executing the MapReduce job to generate bigram inverted index on small full data"
echo "=============================================================================================="
hadoop jar src/main/resources/hw3-1.0-SNAPSHOT.jar org.ir.runner.BigramRunner /data/small/fulldata/ /output/selected_bigram_index_small_fulldata.txt
# additional executions end