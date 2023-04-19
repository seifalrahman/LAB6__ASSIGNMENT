@ echo off 

javac Main.java
REM NORMAL CASE
echo testing normmal case.... 
java Main a.arxml
echo ***************************************************************************
REM notvalid autosar file case 
echo Testing not valid Autosar file case...
java Main WA.txt
echo ***************************************************************************
REM Empty file case 
echo testing empty file 
java Main Empty.arxml 
echo ***************************************************************************
pause