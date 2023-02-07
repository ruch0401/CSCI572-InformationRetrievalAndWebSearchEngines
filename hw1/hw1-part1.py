import json
import os
import re
import time
from random import randint
from pathlib import Path
from common import *
import requests
from bs4 import BeautifulSoup


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


# main method for python
if __name__ == "__main__":
    main()
