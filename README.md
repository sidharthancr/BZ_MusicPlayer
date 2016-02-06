# BZ MUSICPLAYER
BZ Musicplayer is a web application, user can login to player from anywhere in network and listen music.

# Features
* User Authentication
* Can able to sort songs by Title,Rating,Album
* The player resumes from last played song. It stores last played  info for an user.
* Its best viewed in chrome browser.

# Software requirement For this Project:
 - Java 7
 - Eclipse
 - Maven
 - Tomcat 7+
 - Mongodb 2.6.0+


# Steps to run Musicplayer


  - Clone project.
  - Import the project in eclipse and update maven dependencies.
  - Configure tomcat server to run the web application.
  - Download and configure mongodb in port 27017.[If you want some customport or remote server, change the dbHosts value in DatabaseUtil.java.]
  - Import song.json in song collection and musicplayer db which is present in Docs folder.
  - Import user.json in user collection and musicplayer db which is present in Docs folder.
  - Start Tomcat server and open http://hostname:port/musicplayer  in browser.
    
  **Command to import data into mongodb:**
```sh
mongodbimpoort -d musicplayer -c user/song --file user/song.json
```


**NOTE** : Default username and passsowrd of application is **sid**
