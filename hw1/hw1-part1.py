import json
import re
import time
import csv
from random import randint
from pathlib import Path
import os
import sys

import requests
from bs4 import BeautifulSoup

# input variables
USER_AGENT = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                  'Chrome/61.0.3163.100 Safari/537.36'}
SEARCH_URL = "https://www.duckduckgo.com/html/?q="
SEARCH_SELECTOR = ["a"]
SEARCH_ATTRS = {"class": "result__a"}
FILENAME = "./resources/100QueriesSet4.txt"
FILTER_PATTERN = "http(s)?\://(www.)?"

# Output variables
JSON_OP = "./output/hw1.json"
CSV_OP = "./output/hw1.csv"
TXT_OP = "./output/hw1.txt"


def create_file_if_not_exists_and_write_data(path, data):
    path1 = Path(path)
    path1.parent.mkdir(parents=True, exist_ok=True)
    with path1.open("w") as f:
        f.write(data)


# for every query, dict [{"query": "abc", "rank_google": 1, "rank_ddg": 4, "overlap": 3}]
stats = []


def calculate_overlap_and_ranks():
    file1 = open(input_json_)
    file2 = open(input_google_)
    custom_dict = json.loads(file1.read())
    google_dict = json.loads(file2.read())
    for query in custom_dict.keys():
        custom_response = custom_dict[query]
        google_response = google_dict[query]

        count = 0
        google_rank = []
        ddg_rank = []
        for i in range(0, len(google_response)):
            for j in range(0, len(custom_response)):
                if google_response[i] == custom_response[j]:
                    count += 1
                    google_rank.append(i)
                    ddg_rank.append(j)

        stat = {
            "query": query,
            "rank_google": google_rank,
            "rank_ddg": ddg_rank,
            "overlap": count
        }

        stats.append(stat)


# noinspection PyGlobalUndefined
def calculate_spearman_coefficient():
    global calculated_spearman_coefficient, temp
    custom_data = []
    sum_overlap = 0
    sum_spearman = 0
    for (index, stat) in enumerate(stats):
        temp = {}
        rank_google = stat["rank_google"]
        rank_ddg = stat["rank_ddg"]
        n = stat["overlap"]

        calculated_spearman_coefficient = 0
        if n == 0:
            calculated_spearman_coefficient = 0
        elif n == 1:
            if rank_google[0] != rank_ddg[0]:
                calculated_spearman_coefficient = 0
            elif rank_google[0] == rank_ddg[0]:
                calculated_spearman_coefficient = 1
        else:
            diff_sqrd = 0
            for i in range(0, len(rank_google)):
                diff = rank_google[i] - rank_ddg[i]
                diff_sqrd += pow(diff, 2)
            calculated_spearman_coefficient = get_spearman_coefficient(diff_sqrd, n)

        temp = {
            "queries": f"Query {index + 1}",
            "overlapping_results": n,
            "percentage_overlap": (n / 10) * 100.0,
            "spearman_coefficient": round(calculated_spearman_coefficient, 2)
        }
        sum_overlap += n
        sum_spearman += round(calculated_spearman_coefficient, 2)

        custom_data.append(temp)

    return calculated_spearman_coefficient, custom_data, sum_overlap / 100.0, sum_spearman / 100.0


def get_spearman_coefficient(diff_sqrd, n):
    return 1 - ((6 * diff_sqrd) / (n * (pow(n, 2) - 1)))


def write_data_to_csv():
    csv_exists = os.path.exists(output_csv_)
    mode = "w" if csv_exists else "x"

    with open(output_csv_, mode) as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(["Queries", "Number of Overlapping Results", "Percentage Overlap", "Spearman Coefficient"])
        for d in data:
            writer.writerow(
                [d["queries"], d["overlapping_results"], d["percentage_overlap"], d["spearman_coefficient"]])


def write_observation_to_txt(avg_overlap, avg_spearman):
    avg_overlap_ = avg_overlap * 10
    observation_text = f"The Spearman Rank Correlation Coefficient is a measure of whether two continuous or discrete " \
                       "variables are positively related or negatively related. The assignment calls for making a " \
                       "comparison between DuckDuckGo and Google search engines (considering Google as a baseline). " \
                       f"The values of average percentage overlap of {avg_overlap_}% means that " \
                       f"there are almost {abs(round(avg_overlap))} (out of the top 10) " \
                       f"same queries are returned by both DuckDuckGo " \
                       f"and Google.\nOn the other hand, the average spearman rank correlation coefficient of " \
                       f"{avg_spearman} is negative which means that DuckDuckGo ranks important links and " \
                       f"useful links lower when compared to the ranks provided by google for the same results. This is" \
                       f" the reason that DuckDuckGo performs worse than Google."

    text_content = f"Observation:\n{observation_text}\n\nAverage Percentage Overlap: {avg_overlap_}%\n" \
                   f"Average Spearman Coefficient: {avg_spearman}\n"
    create_file_if_not_exists_and_write_data(output_txt_, text_content)
    return avg_overlap_, avg_spearman


class SearchEngine:
    @staticmethod
    def search(query, sleep=True):
        if sleep:  # Prevents loading too many pages too soon
            time.sleep(randint(10, 25))
            temp_url = '+'.join(query.split())  # for adding + between words for the query
            url = SEARCH_URL + temp_url
            soup = BeautifulSoup(requests.get(url, headers=USER_AGENT).text, "html.parser")
            new_results = SearchEngine.scrape_search_result(soup)
            return new_results

    @staticmethod
    def scrape_search_result(soup):
        raw_results = soup.find_all(SEARCH_SELECTOR, SEARCH_ATTRS)
        results = []

        # implement a check to get only 10 results and also check that URLs must not be duplicated
        for result in raw_results:
            link = result.get('href')
            results.append(link)

            # removing the duplicates from search results
        new_results = SearchEngine.remove_duplicate_urls(results)
        print(f"Length of the result sets after removing duplicates: {len(new_results)}")

        # limiting (or increasing) the search results to 10
        final_results = []
        count = 0
        results_needed = 10
        for new_result in new_results:
            count += 1
            if count > results_needed:
                break
            final_results.append(new_result)

        return final_results

    @staticmethod
    def remove_duplicate_urls(urls):
        effective_urls = set()
        complete_urls = []
        duplicate_count = 0

        for url in urls:
            start_index, end_index = SearchEngine.get_actual_url_index_ranges(url)

            len_before = len(effective_urls)
            effective_urls.add(url[start_index:end_index])
            complete_urls.append(url)
            len_after = len(effective_urls)

            if len_after - len_before <= 0:
                duplicate_count += 1
                complete_urls.remove(url)

        print(f"Removed {duplicate_count} duplicate URLs...")
        return complete_urls

    @staticmethod
    def get_actual_url_index_ranges(url):
        match = re.search(FILTER_PATTERN, url)
        start_index = 0
        if match:
            start_index = match.end()
        end_index = len(url) - 1 if url[-1] == '/' else len(url)
        return start_index, end_index

    @staticmethod
    def read_file(filename):
        with open(filename) as file:
            custom_list = [line.rstrip() for line in file]
            return custom_list


def main():
    # read the file
    custom_list = SearchEngine.read_file(FILENAME)
    print(f"{len(custom_list)} queries parsed...")

    response_json = {}
    count = 0
    for item in custom_list:
        print(item)
        result = SearchEngine.search(item)
        response_json[item] = result
        count += 1
        if count > 100:
            break

    print(json.dumps(response_json))
    create_file_if_not_exists_and_write_data(JSON_OP, json.dumps(response_json))


# noinspection PyGlobalUndefined
def calculate_metrics():
    global data
    calculate_overlap_and_ranks()
    spearman_coefficient, data, average_percentage_overlap, average_spearman = calculate_spearman_coefficient()
    write_data_to_csv()
    write_observation_to_txt(round(average_percentage_overlap, 3), round(average_spearman, 3))


def should_calculate_metrics():
    if calculate_metrics_ == 'false':
        print('Skipping metrics calculation. Exiting.')
        exit(1)
    else:
        print('Performing metrics calculation...')
        calculate_metrics()
        print('Metrics calculation complete. Exiting.')


# main method for python
if __name__ == "__main__":

    # sample arguments look like the following (Pass it manually or using intellij parameters) -
    # --crawl_web
    # false
    # --search_engine
    # DuckDuckGo
    # --calculate_metrics
    # true
    # --input_json
    # ./output/hw1.json
    # --input_google
    # ./others/Google_Result1.json
    # --output_csv
    # ./others/op1.csv
    # --output_txt
    # ./others/op2.txt

    args = sys.argv
    crawl_web_ = args[args.index("--crawl_web") + 1]
    search_engine_ = args[args.index("--search_engine") + 1]
    calculate_metrics_ = args[args.index("--calculate_metrics") + 1]
    input_json_ = args[args.index("--input_json") + 1]
    input_google_ = args[args.index("--input_google") + 1]
    output_csv_ = args[args.index("--output_csv") + 1]
    output_txt_ = args[args.index("--output_txt") + 1]

    print(f"You selected the following: \nCrawl Web: {crawl_web_}\nSearch Engine: {search_engine_}\n"
          f"Calculate Metrics: {calculate_metrics_}\nInput JSON: {input_json_}\n"
          f"Input Google: {input_google_}\nOutput CSV: {output_csv_}\nOutput TXT: {output_txt_}\n")

    if crawl_web_ == 'true':
        if search_engine_ != 'DuckDuckGo':
            print('Web Crawling only available for DuckDuckGo. Exiting.')
            exit(1)
        else:
            print(f'Performing Web Crawling for {search_engine_}')
            main()
            should_calculate_metrics()
    else:
        should_calculate_metrics()
