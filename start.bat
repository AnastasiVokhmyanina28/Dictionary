if exist src/main/classes\ (
set DIR_PROJECT=src/main/classes
del /s %DIR_BIN%\*.class >NUL
) 
chcp 65001
javac -d src/main/classes -sourcepath src/main/java src/main/java/*.java
cd src/main/classes
java Dict
pause