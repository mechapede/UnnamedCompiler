#!/usr/bin/env python3
import os
import subprocess
import re

def test():
    accept_files = os.listdir("tests/accept")
    accept_tests = []
    for file in accept_files:
        matchobj = re.match("(\w+).ul$",file)
        if( matchobj and matchobj.group(1) not in accept_tests ):
            accept_tests.append(matchobj.group(1))

    reject_tests = os.listdir("tests/reject")
    #failed_keywords = ["failed","mismatched","missing","extraneous"], using stderr check instead
    passed_tests = 0
    total_tests = len(accept_tests) + len(reject_tests);

    print(accept_tests)

    for test in accept_tests:
        result = subprocess.run(["java","Compiler","tests/accept/" + test + ".ul"],capture_output=True,check=True)
        with open("tests/accept/" + test + ".expected","r") as fp:
            expected_result = fp.read();
        if( result.stdout.decode('ascii') != expected_result or result.stderr != b"" ):
            print("Failed test " + test +  ", expected accept. Dumping process object:")
            print(result)
        else:
            print("Passed accept test " + test)
            passed_tests += 1

    for test in reject_tests:
        result = subprocess.run(["java","Compiler","tests/reject/" + test],capture_output=True,check=True)
        if( result.stderr != b""):
                print("Passed reject test " + test)
                passed_tests += 1
        else:
            print("Failed reject test " + test + ", expected reject. Dumping process object:") #TODO REGEX HERE
            print(result)

    print("Alll tests run. Passed " + str(passed_tests) + "/" + str(total_tests) )

test()
