@echo off
echo Compiling...
javac -d bin --module-path "C:\javafx-sdk-21.0.7\lib" --add-modules javafx.controls,javafx.fxml Main.java
echo Copying resources...
xcopy /Y /I *.fxml bin\
xcopy /Y /I *.css bin\
xcopy /Y /I *.png bin\
xcopy /Y /I *.jpg bin\
xcopy /Y /I *.gif bin\
echo Running...
java -cp bin --module-path "C:\javafx-sdk-21.0.7\lib" --add-modules javafx.controls,javafx.fxml Main
pause 