@echo off
set /p BUILD_NUMBER="Enter Build Number: "
set /p name="Enter Name(sailor,reco,xref,all): "

if %name%==sailor (
gradle applications:sailor-api:buildDocker -xtest -Ppush=false
) 
 if %name%==reco (
gradle applications:recommendation-api:buildDocker -xtest -Ppush=false

)
if %name%==xref (
gradle applications:crossreference-api:buildDocker -xtest -Ppush=false
)

if %name%==all (
gradle build -xtest -Ppush=false
)
