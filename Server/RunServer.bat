@ECHO ON

cd "C:\Users\kiran\OneDrive - The University of Texas at Dallas\CS 6378 ( Advanced Operating Systems )\Projects\Tree Based Quorum\Server"
javac -cp ".\bin" -d ".\bin" .\src\*.java

FOR /L %%A IN (1,1,7) DO (
  ECHO %%A
  start "%%A" cmd.exe /k "java -cp ".\bin" InvokeMain %%A"
)
cmd /k