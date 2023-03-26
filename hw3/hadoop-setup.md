# Hadoop Installation Steps (Ubuntu 22.04)

Here are the steps to install Hadoop on Ubuntu:

### 1. Update your Ubuntu system:

```bash
sudo apt update
sudo apt upgrade
```

### 2. Install Java Development Kit (JDK) version 8 or later:

```bash
sudo apt install openjdk-8-jdk
```

### 3. Download the latest stable release of Hadoop from the Apache website:

```bash
wget https://downloads.apache.org/hadoop/common/hadoop-3.3.1/hadoop-3.3.1.tar.gz
```

### 4. Extract the downloaded file:

```bash
tar -xzf hadoop-3.3.1.tar.gz
```

### 5. Move the extracted directory to /usr/local directory:

```bash
sudo mv hadoop-3.3.1 /usr/local/hadoop
```

### 6. Set up environment variables by editing the .bashrc file:

```bash
nano ~/.bashrc
```

### 7. Add the following lines to the end of the file:

```bash
export HADOOP_HOME=/usr/local/hadoop
export PATH=$PATH:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
```
Save and exit the file.

### 8. Reload the .bashrc file:

```bash
source ~/.bashrc
```

### 9. Edit the Hadoop configuration files:

```bash
cd /usr/local/hadoop/etc/hadoop
```

#### 10. Edit the following files:

- core-site.xml: Add the following lines between the <configuration> and </configuration> tags.

```bash
<property>
    <name>fs.default.name</name>
    <value>hdfs://localhost:9000</value>
</property>

```

- hdfs-site.xml: Add the following lines between the <configuration> and </configuration> tags.

```bash
<property>
    <name>dfs.replication</name>
    <value>1</value>
</property>
```

- mapred-site.xml: Copy mapred-site.xml.template to mapred-site.xml. (you can skip this step if the source file does not exist)

```bash
cp mapred-site.xml.template mapred-site.xml
```

Add the following lines between the <configuration> and </configuration> tags.

```bash
<property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
</property>
```

- yarn-site.xml: Add the following lines between the <configuration> and </configuration> tags.

```bash
<property>
    <name>yarn.nodemanager.aux-services</name>
    <value>mapreduce_shuffle</value>
</property>
<property>
    <name>yarn.nodemanager.aux-services.mapreduce.shuffle.class</name>
    <value>org.apache.hadoop.mapred.ShuffleHandler</value>
</property>
```

### Provide $JAVA_HOME in $HADOOP_HOME/etc/hadoop/hadoop-env.sh

```text
# Many of the options here are built from the perspective that users
# may want to provide OVERWRITING values on the command line.
# For example:
#
  JAVA_HOME=<java-home-path-here>
#
# Therefore, the vast majority (BUT NOT ALL!) of these defaults
# are configured for substitution and not append.  If append
# is preferable, modify this file accordingly.
```

```bash 

### Format the Hadoop file system:

```bash
hdfs namenode -format
```

### Start the Hadoop daemons:

```bash
start-dfs.sh
start-yarn.sh
```

### Verify the Hadoop installation:

```bash
jps
```
The output should include NameNode, SecondaryNameNode, DataNode, ResourceManager, and NodeManager.

### You can access the Hadoop Cluster UI by clicking on the link below
http://localhost:9870/

Congratulations! You have successfully installed Hadoop on your Ubuntu machine.

> Note: If you want to stop the Hadoop daemons, run the following commands:
```bash
stop-dfs.sh
stop-yarn.sh
```

> Note: If the machine is rebooted, you need to start the Hadoop daemons again.

> Note: If you are not able to view the hadoop cluster UI, you can format the NameNode again and start the Hadoop daemons again.
