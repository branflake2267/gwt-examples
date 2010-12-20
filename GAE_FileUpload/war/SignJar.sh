#!/bin/sh
# export jar
#Right click > Export > Java/Jar File > Finish

# generate a key
#keytool -genkey -alias signFiles2 -keystore compstore -keypass kpi135 -dname "cn=Brandon Donnelson" -storepass password
#keytool -genkey -keystore testKeys -alias jdc

# sign jar with key
#jarsigner -keystore compstore -storepass password -keypass kpi135 -signedjar SSignedApplet.jar FileUpload.jar signFiles2
jarsigner -keystore testKeys FileUpload.jar jdc