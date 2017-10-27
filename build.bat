@echo off
set /p BUILD_NUMBER="Enter Build Number: "
set /p name="Enter Name(sailor,reco,xref,all): "

if %name%==sailor (
gradle applications:sailor-api:buildDocker -xtest
) 
 if %name%==reco (
gradle applications:recommendation-api:buildDocker -xtest

)
if %name%==xref (
gradle applications:crossreference-api:buildDocker -xtest
)

if %name%==all (
gradle build -xtest
)
