class FileReader:

    def __init__(self):
        self.path = "resources/"

    def readLines(self, name):
        f = open(self.path + name + ".txt", "r")
        file = f.read()
        return file.split('\n')


    def readLinesAsInts(self, name):
        x = self.readLines(name)
        return [int(i) for i in x]
