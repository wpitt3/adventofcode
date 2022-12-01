#!/bin/bash
mkdir -p src/main/groovy/$1
mkdir -p src/main/resources/$1
mkdir -p src/test/groovy/$1

touch src/main/groovy/$1/d$2a.groovy
touch src/main/groovy/$1/d$2b.groovy
touch src/main/resources/$1/$2.txt
touch src/test/groovy/$1/Test$2.groovy