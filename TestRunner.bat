@echo off
echo provide the details here:

echo Select environment
echo 1. UAT
echo 2. SIT
echo 3. STG
::set choice=
set /p choice=Enter the number to select environment:
if '%choice%'=='1' set environment=UAT
if '%choice%'=='2' set environment=SIT
if '%choice%'=='3' set environment=STG

echo Select TechStack
echo 1. LOCAL CHROME
echo 2. LOCAL FIREFOX
echo 3. LOCAL IEXPLORER
echo 4. REMOTE HTMLUNIT CHROME
echo 5. REMOTE HTMLUNIT FIREFOX
echo 6. REMOTE HTMLUNIT IEXPLORER
::set choice=
set /p choice=Enter the number to select TechStack:
if '%choice%'=='1' set teckStack=LOCAL_CHROME
if '%choice%'=='2' set teckStack=LOCAL_FIREFOX
if '%choice%'=='3' set teckStack=LOCAL_IEXPLORER
if '%choice%'=='4' set teckStack=REMOTE_HTMLUNIT_CHROME
if '%choice%'=='5' set teckStack=REMOTE_HTMLUNIT_FIREFOX
if '%choice%'=='6' set teckStack=REMOTE_HTMLUNIT_IEXPLORER

echo Select Test Suite
echo 1. Smoke Suite
echo 2. Regression Suite

::set choice=
set /p choice=Enter the number to select Test Suite:
if '%choice%'=='1' set testscripts=SmokeSuite.xml
if '%choice%'=='2' set testscripts=RegressionSuite.xml

mvn clean test -Denvironment=%environment% -DtechStack=%teckStack% -DsuiteXmlFile=%testscripts%
