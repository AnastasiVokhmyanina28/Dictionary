chcp 1251 
if exist src/main/classes\ (
set DIR_PROJECT=src/main/classes/com
del /s %DIR_BIN%\*.class >NUL
) 
javac -d src/main/classes -sourcepath src/main/java src/main/java/com/*.java
cd src/main/classes
java com.Dict
pause