#!/usr/bin/env python3
import os
import subprocess
import re

def test():
    parse_passed, parse_total = parse_tests()
    type_passed, type_total = typecheck_tests()
    type_array_passed, type_array_total = typecheck_array_tests()
    print("All tests run. Passed " + str(parse_passed + type_passed + type_array_passed) + "/" + str(parse_total + type_total + type_array_total) )

def parse_tests():
    test_path = "tests/parsing/"
    accept_files = os.listdir(test_path + "accept")
    accept_tests = []
    for file in accept_files:
        matchobj = re.match("(\w+).ul$",file)
        if(matchobj and matchobj.group(1) not in accept_tests):
            accept_tests.append(matchobj.group(1))
    reject_tests = os.listdir(test_path + "reject")
    #failed_keywords = ["failed","mismatched","missing","extraneous"], using stderr check instead
    passed_tests = 0
    total_tests = len(accept_tests) + len(reject_tests)

    for test in accept_tests:
        result = subprocess.run(["java","Compiler", test_path + "accept/" + test + ".ul","-p"],capture_output=True,check=True)
        with open(test_path + "accept/" + test + ".expected","r") as fp:
            expected_result = fp.read();
        if( result.stdout.decode('ascii') != expected_result or result.stderr != b"" ):
            print("Failed test " + test +  ", expected accept. Dumping process object:")
            print(result)
        else:
            print("Passed accept test " + test)
            passed_tests += 1

    for test in reject_tests:
        result = subprocess.run(["java","Compiler", test_path + "reject/" + test,"-p"],capture_output=True,check=True)
        if( result.stderr != b""):
                print("Passed reject test " + test)
                passed_tests += 1
        else:
            print("Failed reject test " + test + ", expected reject. Dumping process object:") #TODO REGEX HERE
            print(result)

    print("Alll Parse tests run. Passed " + str(passed_tests) + "/" + str(total_tests) )
    return (passed_tests,total_tests);


def typecheck_tests():
    test_path = "tests/typechecking/withoutSubtypes/"
    accept_tests = os.listdir(test_path + "accept")
    reject_tests = os.listdir(test_path + "reject")
    total_tests = len(accept_tests) + len(reject_tests)
    passed_tests = 0
    for test in accept_tests:
        result = subprocess.run(["java","Compiler", test_path + "accept/" + test],capture_output=True,check=True)
        if(result.stderr == b"" and result.stdout == b""):
            print("Passed accept test " + test)
            passed_tests += 1
        else:
            print("Failed test " + test +  ", expected accept. Dumping process object:")
            print(result)
    
    for test in reject_tests:
        result = subprocess.run(["java","Compiler", test_path + "reject/" + test],capture_output=True,check=True)
        if(result.stderr == b"" and result.stdout == b""):
            print("Failed test " + test +  ", expected reject. Dumping process object:")
            print(result)
        else:
            print("Passed reject test " + test)
            passed_tests += 1
            
    print("Alll Basic Type tests run. Passed " + str(passed_tests) + "/" + str(total_tests) )
    return (passed_tests, total_tests)


def typecheck_array_tests():
    test_path = "tests/typechecking/withSubtypes/"
    accept_tests = os.listdir(test_path + "accept")
    reject_tests = os.listdir(test_path + "reject")
    total_tests = len(accept_tests) + len(reject_tests)
    passed_tests = 0
    for test in accept_tests:
        result = subprocess.run(["java","Compiler", test_path + "accept/" + test],capture_output=True,check=True)
        if(result.stderr == b"" and result.stdout == b""):
            print("Passed accept test " + test)
            passed_tests += 1
        else:
            print("Failed test " + test +  ", expected accept. Dumping process object:")
            print(result)
    
    for test in reject_tests:
        result = subprocess.run(["java","Compiler", test_path + "reject/" + test],capture_output=True,check=True)
        if(result.stderr == b"" and result.stdout == b""):
            print("Failed test " + test +  ", expected reject. Dumping process object:")
            print(result)
        else:
            print("Passed reject test " + test)
            passed_tests += 1
            
    print("Alll Array Type tests run. Passed " + str(passed_tests) + "/" + str(total_tests) )
    return (passed_tests, total_tests)


test()
