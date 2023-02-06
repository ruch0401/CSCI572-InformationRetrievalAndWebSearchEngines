import json
import csv

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
    with open("./resources/hw1.csv", 'w', ) as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow(["Queries", "Number of Overlapping Results", "Percentage Overlap", "Spearman Coefficient"])
        for d in data:
            writer.writerow(
                [d["queries"], d["overlapping_results"], d["percentage_overlap"], d["spearman_coefficient"]])


def write_observation_to_txt(avg_overlap, avg_spearman):
    file = open("./resources/hw1.txt", "x")
    observation_text = ""
    file.write(f"Observation: {observation_text}\nAverage Overlap: {avg_overlap * 10}\nAverage Spearman Coefficient: {avg_spearman}")
    return avg_overlap * 10, avg_spearman, 2


if __name__ == "__main__":
    calculate_overlap_and_ranks()
    spearman_coefficient, data, average_overlap, average_spearman = calculate_spearman_coefficient()
    write_data_to_csv()
    write_observation_to_txt(round(average_overlap, 3), round(average_spearman, 3))

