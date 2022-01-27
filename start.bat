set DIR_PROJECT=src/main/classes
del /s %DIR_BIN%\*.class >NUL
cd src/main/javaFiles
chcp 65001
javac -d ../classes *.java
cd..
cd classes
java src.main.javaFiles.Dict
pause