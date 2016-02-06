%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Software requirement For this Project:
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
Java 7,
Eclipse,
Maven,
Tomcat 7+
Mongodb 2.6.0+


%%%%%%%%%%%%%%%%%%%%%%%%
Steps to run Musicplayer
%%%%%%%%%%%%%%%%%%%%%%%%

Step 1:Clone project.
Step 2:Import the project in eclipse and update maven dependencies.
Step 3:Configure tomcat server to run the web application.
Step 4:Download and configure mongodb in port 27017.[If you want some custom port or remote server, change the dbHosts value in DatabaseUtil.java.]
Step 5:Import song.json in song collection and musicplayer db which is present in Docs folder.
Step 6:Import user.json in user collection and musicplayer db which is present in Docs folder.
*Note: Command to import date: mongodbimpoort -d musicplayer -c user/song --file user/song.json*
Step 7:Start Tomcat server and open http://hostname:port/musicplayer  in browser.

@@@@@@@@@@@@NOTE:Default username and passsowrd is "sid"@@@@@@@@@@@@@

