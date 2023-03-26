# IR HW3: Inverted Index Creation

## Introduction

In this homework, you'll write code that indexes words from multiple text files, and outputs an inverted index.

## Project details

## Try it yourself

### Prerequisites
- Hadoop 3.3.1 should be installed on your machine. (Installation instructions can be found [here](hadoop-setup.md)
- Java 8 should be installed on your machine.
- Maven should be installed on your machine.

### Steps to run the project
- Once you have the prerequisites installed, clone this repository to your machine.
- Clone the repository to your machine. Navigate to the root directory of the project (hw3).
- Open the project in your favorite IDE.
- You will find the jar file present [here](src/main/resources/hw3-1.0-SNAPSHOT.jar).
- You will find the `setup.sh` file present [here](src/main/resources/setup.sh). This file contains the commands to create the input and output directories in HDFS.
- Execute this shell script on your machine using the following command:
```bash
sh setup.sh
```

> Please keep in mind of the location where you are trying to execute the script. The commands might change depending on the location.

> You can modify the shell script to accomodate source directories at different locations.

- Sample data files can be found [here](data-small)


## Developer's Corner

### Maven Dependency Graph

```maven
org.ir:hw3:jar:1.0-SNAPSHOT
+- org.apache.hadoop:hadoop-common:jar:3.3.3:compile
|  +- org.apache.hadoop.thirdparty:hadoop-shaded-protobuf_3_7:jar:1.1.1:compile
|  +- org.apache.hadoop:hadoop-annotations:jar:3.3.3:compile
|  +- org.apache.hadoop.thirdparty:hadoop-shaded-guava:jar:1.1.1:compile
|  +- com.google.guava:guava:jar:27.0-jre:compile
|  |  +- com.google.guava:failureaccess:jar:1.0:compile
|  |  +- com.google.guava:listenablefuture:jar:9999.0-empty-to-avoid-conflict-with-guava:compile
|  |  +- org.checkerframework:checker-qual:jar:2.5.2:compile
|  |  +- com.google.j2objc:j2objc-annotations:jar:1.1:compile
|  |  \- org.codehaus.mojo:animal-sniffer-annotations:jar:1.17:compile
|  +- commons-cli:commons-cli:jar:1.2:compile
|  +- org.apache.commons:commons-math3:jar:3.1.1:compile
|  +- org.apache.httpcomponents:httpclient:jar:4.5.13:compile
|  |  \- org.apache.httpcomponents:httpcore:jar:4.4.13:compile
|  +- commons-codec:commons-codec:jar:1.15:compile
|  +- commons-io:commons-io:jar:2.8.0:compile
|  +- commons-net:commons-net:jar:3.6:compile
|  +- commons-collections:commons-collections:jar:3.2.2:compile
|  +- javax.servlet:javax.servlet-api:jar:3.1.0:compile
|  +- jakarta.activation:jakarta.activation-api:jar:1.2.1:compile
|  +- org.eclipse.jetty:jetty-server:jar:9.4.43.v20210629:compile
|  |  +- org.eclipse.jetty:jetty-http:jar:9.4.43.v20210629:compile
|  |  \- org.eclipse.jetty:jetty-io:jar:9.4.43.v20210629:compile
|  +- org.eclipse.jetty:jetty-util:jar:9.4.43.v20210629:compile
|  +- org.eclipse.jetty:jetty-servlet:jar:9.4.43.v20210629:compile
|  |  +- org.eclipse.jetty:jetty-security:jar:9.4.43.v20210629:compile
|  |  \- org.eclipse.jetty:jetty-util-ajax:jar:9.4.43.v20210629:compile
|  +- org.eclipse.jetty:jetty-webapp:jar:9.4.43.v20210629:compile
|  |  \- org.eclipse.jetty:jetty-xml:jar:9.4.43.v20210629:compile
|  +- javax.servlet.jsp:jsp-api:jar:2.1:runtime
|  +- com.sun.jersey:jersey-core:jar:1.19:compile
|  |  \- javax.ws.rs:jsr311-api:jar:1.1.1:compile
|  +- com.sun.jersey:jersey-servlet:jar:1.19:compile
|  +- com.sun.jersey:jersey-json:jar:1.19:compile
|  |  +- org.codehaus.jettison:jettison:jar:1.1:compile
|  |  +- com.sun.xml.bind:jaxb-impl:jar:2.2.3-1:compile
|  |  +- org.codehaus.jackson:jackson-core-asl:jar:1.9.2:compile
|  |  +- org.codehaus.jackson:jackson-mapper-asl:jar:1.9.2:compile
|  |  +- org.codehaus.jackson:jackson-jaxrs:jar:1.9.2:compile
|  |  \- org.codehaus.jackson:jackson-xc:jar:1.9.2:compile
|  +- com.sun.jersey:jersey-server:jar:1.19:compile
|  +- commons-logging:commons-logging:jar:1.1.3:compile
|  +- ch.qos.reload4j:reload4j:jar:1.2.18.3:compile
|  +- commons-beanutils:commons-beanutils:jar:1.9.4:compile
|  +- org.apache.commons:commons-configuration2:jar:2.1.1:compile
|  +- org.apache.commons:commons-lang3:jar:3.12.0:compile
|  +- org.apache.commons:commons-text:jar:1.4:compile
|  +- org.slf4j:slf4j-api:jar:1.7.36:compile
|  +- org.slf4j:slf4j-reload4j:jar:1.7.36:compile
|  +- org.apache.avro:avro:jar:1.7.7:compile
|  |  \- com.thoughtworks.paranamer:paranamer:jar:2.3:compile
|  +- com.google.re2j:re2j:jar:1.1:compile
|  +- com.google.protobuf:protobuf-java:jar:2.5.0:compile
|  +- com.google.code.gson:gson:jar:2.8.9:compile
|  +- org.apache.hadoop:hadoop-auth:jar:3.3.3:compile
|  |  +- com.nimbusds:nimbus-jose-jwt:jar:9.8.1:compile
|  |  |  \- com.github.stephenc.jcip:jcip-annotations:jar:1.0-1:compile
|  |  +- net.minidev:json-smart:jar:2.4.7:compile
|  |  |  \- net.minidev:accessors-smart:jar:2.4.7:compile
|  |  |     \- org.ow2.asm:asm:jar:9.1:compile
|  |  +- org.apache.curator:curator-framework:jar:4.2.0:compile
|  |  \- org.apache.kerby:kerb-simplekdc:jar:1.0.1:compile
|  |     +- org.apache.kerby:kerb-client:jar:1.0.1:compile
|  |     |  +- org.apache.kerby:kerby-config:jar:1.0.1:compile
|  |     |  +- org.apache.kerby:kerb-common:jar:1.0.1:compile
|  |     |  |  \- org.apache.kerby:kerb-crypto:jar:1.0.1:compile
|  |     |  +- org.apache.kerby:kerb-util:jar:1.0.1:compile
|  |     |  \- org.apache.kerby:token-provider:jar:1.0.1:compile
|  |     \- org.apache.kerby:kerb-admin:jar:1.0.1:compile
|  |        +- org.apache.kerby:kerb-server:jar:1.0.1:compile
|  |        |  \- org.apache.kerby:kerb-identity:jar:1.0.1:compile
|  |        \- org.apache.kerby:kerby-xdr:jar:1.0.1:compile
|  +- com.jcraft:jsch:jar:0.1.55:compile
|  +- org.apache.curator:curator-client:jar:4.2.0:compile
|  +- org.apache.curator:curator-recipes:jar:4.2.0:compile
|  +- com.google.code.findbugs:jsr305:jar:3.0.2:compile
|  +- org.apache.zookeeper:zookeeper:jar:3.5.6:compile
|  |  +- org.apache.zookeeper:zookeeper-jute:jar:3.5.6:compile
|  |  +- org.apache.yetus:audience-annotations:jar:0.5.0:compile
|  |  +- io.netty:netty-handler:jar:4.1.42.Final:compile
|  |  |  +- io.netty:netty-common:jar:4.1.42.Final:compile
|  |  |  +- io.netty:netty-buffer:jar:4.1.42.Final:compile
|  |  |  +- io.netty:netty-transport:jar:4.1.42.Final:compile
|  |  |  |  \- io.netty:netty-resolver:jar:4.1.42.Final:compile
|  |  |  \- io.netty:netty-codec:jar:4.1.42.Final:compile
|  |  +- io.netty:netty-transport-native-epoll:jar:4.1.42.Final:compile
|  |  |  \- io.netty:netty-transport-native-unix-common:jar:4.1.42.Final:compile
|  |  +- org.slf4j:slf4j-log4j12:jar:1.7.25:compile
|  |  \- log4j:log4j:jar:1.2.17:compile
|  +- org.apache.commons:commons-compress:jar:1.21:compile
|  +- org.apache.kerby:kerb-core:jar:1.0.1:compile
|  |  \- org.apache.kerby:kerby-pkix:jar:1.0.1:compile
|  |     +- org.apache.kerby:kerby-asn1:jar:1.0.1:compile
|  |     \- org.apache.kerby:kerby-util:jar:1.0.1:compile
|  +- com.fasterxml.jackson.core:jackson-databind:jar:2.13.2.2:compile
|  |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.13.2:compile
|  |  \- com.fasterxml.jackson.core:jackson-core:jar:2.13.2:compile
|  +- org.codehaus.woodstox:stax2-api:jar:4.2.1:compile
|  +- com.fasterxml.woodstox:woodstox-core:jar:5.3.0:compile
|  +- dnsjava:dnsjava:jar:2.1.7:compile
|  \- org.xerial.snappy:snappy-java:jar:1.1.8.2:compile
\- org.apache.hadoop:hadoop-mapreduce-client-core:jar:3.3.3:compile
   +- org.apache.hadoop:hadoop-yarn-client:jar:3.3.3:compile
   |  +- org.eclipse.jetty.websocket:websocket-client:jar:9.4.43.v20210629:compile
   |  |  +- org.eclipse.jetty:jetty-client:jar:9.4.43.v20210629:compile
   |  |  \- org.eclipse.jetty.websocket:websocket-common:jar:9.4.43.v20210629:compile
   |  |     \- org.eclipse.jetty.websocket:websocket-api:jar:9.4.43.v20210629:compile
   |  +- org.apache.hadoop:hadoop-yarn-api:jar:3.3.3:compile
   |  \- org.jline:jline:jar:3.9.0:compile
   +- org.apache.hadoop:hadoop-yarn-common:jar:3.3.3:compile
   |  +- javax.xml.bind:jaxb-api:jar:2.2.11:compile
   |  +- com.sun.jersey:jersey-client:jar:1.19:compile
   |  +- com.google.inject:guice:jar:4.0:compile
   |  |  +- javax.inject:javax.inject:jar:1:compile
   |  |  \- aopalliance:aopalliance:jar:1.0:compile
   |  +- com.sun.jersey.contribs:jersey-guice:jar:1.19:compile
   |  +- com.fasterxml.jackson.module:jackson-module-jaxb-annotations:jar:2.13.2:compile
   |  |  \- jakarta.xml.bind:jakarta.xml.bind-api:jar:2.3.3:compile
   |  \- com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider:jar:2.13.2:compile
   |     \- com.fasterxml.jackson.jaxrs:jackson-jaxrs-base:jar:2.13.2:compile
   +- org.apache.hadoop:hadoop-hdfs-client:jar:3.3.3:compile
   |  \- com.squareup.okhttp:okhttp:jar:2.7.5:compile
   |     \- com.squareup.okio:okio:jar:1.6.0:compile
   +- javax.ws.rs:javax.ws.rs-api:jar:2.1.1:compile
   +- com.google.inject.extensions:guice-servlet:jar:4.0:compile
   \- io.netty:netty:jar:3.10.6.Final:compile
```