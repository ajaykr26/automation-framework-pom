@echo off
title Run Encryptor
set fileSeparator=\
set envDir=%~dp0src%fileSeparator%test%fileSeparator%resources%fileSeparator%config%fileSeparator%environments%fileSeparator%
java -cp %CD%\lib\runner\Encryptor.jar;..\lib\* com.naqe.Encryptor %envDir%
pause