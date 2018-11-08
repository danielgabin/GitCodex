#Write line above every json format prod to get valid Elasticsearch bulk load file
with open("items.json", "r") as in_file:
    buf = in_file.readlines()

count=1
with open("elasticitems.json", "w") as out_file:
    for line in buf:
        bulkdata = '{"index":{"_index":"elasticfiles","_type":"elasticfiles","_id":"'
        scount = str(count)
        bulkdata = bulkdata + scount + '"}}\n'
        out_file.write(bulkdata)
        out_file.write(line)
        count = count+1
