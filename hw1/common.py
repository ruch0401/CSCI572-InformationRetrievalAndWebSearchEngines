from pathlib import Path

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
