@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION
CALL :ConfigRun

:ConfigRun
cls
CALL :GetPathForConfig
if exist %pathForConfig% (

For /F "tokens=1* delims==" %%i IN (%pathForConfig%) DO (
        if [%%i]==[test] (
                set cmd%%iOption=-D%%i=%%j
            ) ELSE if [%%i]==[suiteXmlFile] (
                set cmd%%iOption=-D%%i=%%j
            ) ELSE (
                set cmd%%iOption=-Dcukes.%%i=%%j
            )
)
) else (
    echo "file doesn't exist"
)
CALL :FinalStep
EXIT /B 0

:FinalStep
set finalRunCmd=mvn clean install %cmdtestOption% %cmdenvironmentOption% %cmdtechstackOption% %cmdsuiteXmlFileOption% -Dorg.apache.logging.log4j.level=DEBUG -Dcukes.selenium.defaultFindRetries=2
cls
echo %finalRunCmd%
CALL %finalRunCmd%
PAUSE
EXIT

:GetPathForConfig
set fileSeperator=\
set workingDir=%CD%
set pathForConfig=%workingDir%%fileSeperator%ConfigTestRunner.properties
EXIT /B 0

endLocal