# IR HW2: Web Crawling

## Introduction

This project is a web crawler that crawls the web and generates a report of the analytics for the crawled results. The report contains the following information:


## Project details

## Try it yourself

### Prerequisites
You should have Java 11 installed on your system. You can refer [this](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html) document.


### Steps to run the project
- Download the [Project Jar File](jar/hw2-1.0.0.jar)
- The command template is as follows
```bash
java -cp <jar-name> <fully-qualified-name-of-the-class> <all-command-line-options>
```
- Explore what command line options are available by running the following command
```bash
java -cp hw2-1.0.0.jar org.ir.crawling.controller.CrawlerController --help
```

```bash
Initializing output files and directories
Attempting to parse command line arguments
Usage: <main class> [options]
  Options:
    --help, -h
      Displays all configurable options available.
      Default: true
    --max-depth
      The maximum depth of crawling. This is useful when you want to limit the 
      crawl to a certain depth.
      Default: 16
    --max-page-count
      The maximum number of pages to be crawled. This is useful when you want 
      to limit the crawl to a certain number of pages.
      Default: 20
  * --name
      Name using which the report will be created
      Default: Ruchit Bhardwaj
    --number-of-crawlers
      The number of crawlers to be used. This controls the internal thread 
      count 
      Default: 30
  * --output-dir
      This is the output directory where the crawl outputs are stored.
      Default: output
  * --output-fetch
      This is the output filename where the crawl's fetch outputs are stored.
      Default: fetch_latimes.csv
  * --output-report
      This is the output filename with which the analytics report will be 
      generated. 
      Default: CrawlReport_latimes.txt
  * --output-url
      This is the output filename where the crawl's url outputs are stored.
      Default: urls_latimes.csv
  * --output-visit
      This is the output filename where the crawl's visit outputs are stored.
      Default: visit_latimes.csv
    --politeness-delay-in-millis
      Controls the delay with which crawler4j crawls subsequent urls.
      Default: 1
    --root
      This is the crawled storage folder. This is useful when you want to 
      resume a crawl from a previous crawl.
      Default: data
  * --seed-url-https
      The seed url to start crawling from (https)
      Default: https://www.latimes.com/
  * --usc-id
      USC ID of the name using which the report will be created
      Default: 1111417799
```
- Run the following command to perform the crawl and generate the report
```bash
java -cp hw2-1.0.0.jar org.ir.crawling.controller.CrawlerController --seed-url-https <https-seed-url-here> --root <root-folder-for-temp-computation-name-here> --output-fetch <fetch-statistics-filename-here-in-csv> --output-visit <visit-statistics-filename-here-in-csv> --output-url <url-statistics-filename-here-in-csv> --output-report <analytics-report-filename-here-in-txt> --usc-id <usc-id-here> --name "<name-here>" --output-dir <output-directory-name-here>
```

- Example command to run the project
```bash
java -cp hw2-1.0.0.jar --add-exports java.management/sun.management=ALL-UNNAMED org.ir.crawling.controller.CrawlerController --seed-url-https https://www.latimes.com/ --root data --output-fetch fetch_latimes.csv --output-visit visit_latimes.csv --output-url urls_latimes.csv --output-report CrawlReport_latimes.txt --usc-id 1111417799 --name "Ruchit Bhardwaj" --output-dir output
```

- Sample output
```bash
Initializing output files and directories
Attempting to parse command line arguments
Successfully parsed command line arguments as follows:

HELP: false
NAME: 'Ruchit Bhardwaj'
USCID: '1111417799'
SEED_URL_HTTPS: 'https://www.latimes.com/'
SEED_URL_HTTP: 'http://www.latimes.com/'
ROOT: 'data'
OUTPUT_DIR: 'output'
OUTPUT_FETCH: 'fetch_latimes.csv'
OUTPUT_VISIT: 'visit_latimes.csv'
OUTPUT_URL: 'urls_latimes.csv'
OUTPUT_REPORT: 'CrawlReport_latimes.txt'
NUMBER_OF_CRAWLERS: 30
MAX_DEPTH: 16
MAX_PAGE_COUNT: 20
POLITENESS_DELAY_IN_MILLIS: 1
ROOT_PATH: /tmp/data/crawl/root

Configuring crawler to parse https://www.latimes.com/
Instantiating crawler for this crawl
Setting seed as https://www.latimes.com/
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by com.sleepycat.je.utilint.JVMSystemUtils (file:/home/ruchit/Desktop/test/hw2-1.0.0.jar) to method sun.management.BaseOperatingSystemImpl.getSystemLoadAverage()
WARNING: Please consider reporting this to the maintainers of com.sleepycat.je.utilint.JVMSystemUtils
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
Beginning the crawl. This is a blocking operation
Parsed URL #1 with response status code (200)
Parsed URL #2 with response status code (200)
Parsed URL #3 with response status code (200)
Parsed URL #4 with response status code (200)
Parsed URL #5 with response status code (200)
Parsed URL #6 with response status code (200)
Parsed URL #7 with response status code (200)
Parsed URL #8 with response status code (200)
Parsed URL #9 with response status code (200)
Parsed URL #10 with response status code (200)
Parsed URL #11 with response status code (200)
Parsed URL #12 with response status code (200)
Parsed URL #13 with response status code (200)
Parsed URL #14 with response status code (200)
Parsed URL #15 with response status code (200)
Parsed URL #16 with response status code (200)
Parsed URL #17 with response status code (200)
Parsed URL #18 with response status code (200)
Parsed URL #19 with response status code (200)
Parsed URL #20 with response status code (200)

Completed crawling 20 pages
Attempting to write crawled metrics to file: output/fetch_latimes.csv
Successfully completing writing fetch data to file output/fetch_latimes.csv
Attempting to write crawled metrics to file: output/visit_latimes.csv
Successfully completing writing visit data to file output/visit_latimes.csv
Attempting to write crawled metrics to file: output/urls_latimes.csv
Successfully completing writing url data to file output/urls_latimes.csv

Completed writing crawl outputs to files. Now generating analytics
Starting analyzing fetch data
Starting analyzing file size data
Starting analyzing outgoing url data
Starting analyzing outgoing status code data
Completed analysis. Writing results to output file [CrawlReport_latimes.txt]
```

## Developer's Corner

### Maven Dependency Graph

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