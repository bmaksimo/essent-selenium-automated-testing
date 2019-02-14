#!/usr/bin/env bash
ekstrakts=allcukes-ekstrakts.txt

echo 'Pouring Gherkin steps definitions (cologne extract) to '"${ekstrakts}"
find src/ -name '*.java' -exec cat {} \; | grep '^\s*\(@Given\|@When\|@Then\|@And\)' | sed -n "s/^[[:blank:]]*@[[:alpha:]]*(/(/p" | sort >> "${ekstrakts}" 
