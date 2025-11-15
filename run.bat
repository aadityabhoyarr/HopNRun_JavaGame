@echo off
javac --module-path ".\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.graphics Main.java
java  --module-path ".\javafx-sdk-21.0.9\lib" --add-modules javafx.controls,javafx.graphics Main
pause
