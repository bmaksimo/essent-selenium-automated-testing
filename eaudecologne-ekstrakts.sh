#!/usr/bin/env bash
allcukes=allcukes-processed.txt

echo 'Pouring Gherkin steps patterns (cologne extract) to '"${allcukes}"
find src/ -name '*.java' -exec cat {} \; | grep '^\s*\(@Given\|@When\|@Then\|@And\)' | sed -n "s/^[[:blank:]]*@[[:alpha:]]*(/(/p" | sort >> "${allcukes}"
