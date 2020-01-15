#!/usr/bin/env python3
import os
import subprocess

def test():
    accept_tests = os.listdir("tests/accept")
    reject_tests = os.listdir("tests/reject")
    passed_tests = 0
    total_tests = len(accept_tests) + len(reject_tests);
    CLASSPATH = "/usr/share/java/stringtemplate.jar:/usr/share/java/stringtemplate4.jar:/usr/share/java/antlr3.jar:/usr/share/java/antlr3-runtime.jar"

    for test in accept_tests:
        result = subprocess.run(["java","-cp",".:" + CLASSPATH,"Compiler","tests/accept/" + test],capture_output=True,check=True)
        if( result.stdout == b"" and result.stderr == b"" ):
            print("Passed accept test " + test)
            passed_tests += 1
        else:
            print("Failed test " + test +  ", expected accept. Dumping process object:")
            print(result)

    for test in reject_tests:
        result = subprocess.run(["java","-cp",".:" + CLASSPATH,"Compiler","tests/reject/" + test],capture_output=True,check=True)
        if( result.stdout == b"" and result.stderr == b"" ):
            print("Failed reject test " + test + ", expected reject. Dumping process object:")
            print(result)
        else:
            print("Passed reject test " + test)
            passed_tests += 1
    print("Alll tests run. Passed " + str(passed_tests) + "/" + str(total_tests) )

test()
