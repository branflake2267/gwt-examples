#!/bin/sh

BASE="/var/lib/tomcat6/webapps/ROOT/WEB-INF";

# classes
MYCP="$BASE/classes:"

# jars
MYCP="$MYCP$BASE/lib:"
MYCP="$MYCP/usr/share/java/RXTXcomm.jar:"

# debug output
echo "debug classes: "
echo $MYCP
echo ""

# run the java
java -cp $MYCP org.gonevertical.loc.server.buttons.Run_SerialReader

exit;