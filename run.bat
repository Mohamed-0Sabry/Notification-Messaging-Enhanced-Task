@echo off
echo Compiling...
javac -d bin --module-path "C:\Users\a_sal\OneDrive\Desktop\Modern Gas\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml Main.java
echo Copying resources...
xcopy /Y /I *.fxml bin\
xcopy /Y /I *.css bin\
xcopy /Y /I *.png bin\
xcopy /Y /I *.jpg bin\
xcopy /Y /I *.gif bin\
xcopy /Y /I *.xml bin\
echo Running...
java -cp bin --module-path "C:\Users\a_sal\OneDrive\Desktop\Modern Gas\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml Main
pause 