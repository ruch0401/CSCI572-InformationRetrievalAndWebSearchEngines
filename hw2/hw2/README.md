# IR HW2: Web Crawling

## Introduction


## Maven Dependency Graph

```maven
org.ir:hw2:jar:1.0-SNAPSHOT
+- edu.uci.ics:crawler4j:jar:4.4.0:compile
|  +- org.slf4j:slf4j-api:jar:1.7.22:compile
|  +- ch.qos.logback:logback-classic:jar:1.1.7:runtime
|  |  \- ch.qos.logback:logback-core:jar:1.1.7:runtime
|  +- com.google.guava:guava:jar:24.0-jre:compile
|  |  +- com.google.code.findbugs:jsr305:jar:1.3.9:compile
|  |  +- org.checkerframework:checker-compat-qual:jar:2.0.0:compile
|  |  +- com.google.errorprone:error_prone_annotations:jar:2.1.3:compile
|  |  +- com.google.j2objc:j2objc-annotations:jar:1.1:compile
|  |  \- org.codehaus.mojo:animal-sniffer-annotations:jar:1.14:compile
|  +- org.apache.httpcomponents:httpclient:jar:4.5.3:compile
|  |  \- org.apache.httpcomponents:httpcore:jar:4.4.6:compile
|  +- com.sleepycat:je:jar:5.0.84:compile
|  \- org.apache.tika:tika-parsers:jar:1.16:compile
|     +- org.apache.tika:tika-core:jar:1.16:compile
|     +- org.apache.james:apache-mime4j-core:jar:0.8.1:compile
|     +- org.apache.james:apache-mime4j-dom:jar:0.8.1:compile
|     +- org.ccil.cowan.tagsoup:tagsoup:jar:1.2.1:compile
|     +- commons-io:commons-io:jar:2.5:compile
|     +- org.slf4j:jul-to-slf4j:jar:1.7.24:compile
|     \- org.slf4j:jcl-over-slf4j:jar:1.7.24:compile
+- org.apache.commons:commons-csv:jar:1.9.0:compile
+- commons-httpclient:commons-httpclient:jar:3.1:compile
|  +- commons-logging:commons-logging:jar:1.0.4:compile
|  \- commons-codec:commons-codec:jar:1.2:compile
+- com.beust:jcommander:jar:1.78:compile
\- org.projectlombok:lombok:jar:1.18.24:provided
```