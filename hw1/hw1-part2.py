import json

# for every query, dict [{"query": "abc", "rank_google": 1, "rank_ddg": 4, "overlap": 3}]
stats = []


def calculate_overlap_and_ranks():
    file1 = open("./resources/hw1.json")
    file2 = open("./resources/Google_Result4.json")
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
        return stat["overlap"]


def calculate_spearman_coefficient():
    for stat in stats:
        rank_google = stat["rank_google"]
        rank_ddg = stat["rank_ddg"]
        n = stat["overlap"]

        if n == 0:
            return 0
        if n == 1:
            if rank_google[0] != rank_ddg[0]:
                return 0
            elif rank_google[0] == rank_ddg[0]:
                return 1
        else:
            diff_sqrd = 0
            for i in range(0, len(rank_google)):
                diff = rank_google[i] - rank_ddg[i]
                diff_sqrd += pow(diff, 2)
            return get_spearman_coefficient(diff_sqrd, n)


def get_spearman_coefficient(diff_sqrd, n):
    return 1 - ((6 * diff_sqrd) / n * (pow(n, 2) - 1))


if __name__ == "__main__":
    overlap = calculate_overlap_and_ranks()
    spearman_coefficient = calculate_spearman_coefficient()
    print(f"Overlap: {overlap}, Spearman Coefficient: {spearman_coefficient}")
