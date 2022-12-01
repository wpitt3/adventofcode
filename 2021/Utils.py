

def addFreq(freq, key):
    if key not in freq:
        freq[key] = 0
    freq[key] += 1

def addFreqWithValue(freq, key, value):
    if key not in freq:
        freq[key] = 0
    freq[key] += value