file = open("L6_1_965381.csv", "r")
w_file = open("small_50m.csv", "w")
print "Entering for loop"
count = 0
# 160 lines == 1M
# Change end as per above
end = 8000
for line in file.readlines():
    count +=1
    w_file.write(line)
    if count >= end:
        break
print count / (6 * 1024)
print "Closing File"
file.close()
w_file.close()
