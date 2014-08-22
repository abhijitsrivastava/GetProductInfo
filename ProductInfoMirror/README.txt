Steps to setup source:

Ensure your system meets the following prerequisites:
1. Java 1.6 or above
2. Apache Maven - used for part of the build process.

Follow these steps to create a mirror project and run it. (only instead of downloading code from github use this source code)
https://developers.google.com/glass/develop/mirror/quickstart/java

Currently these credentials are being used:
gmail Account: mirrorproductinfo@gmail.com
password: mirrorproduct

API project created on developer console for google:
https://console.developers.google.com/project/1066507831079

Client ID: 1066507831079-ns6ocfah56a5f07rt7581c2sq314taul.apps.googleusercontent.com
Client secret : 72ghUtkucOS8LMOQUHcUrKdh



Server Setup:
1. Install java : follow steps from here : https://www.digitalocean.com/community/tutorials/how-to-install-java-on-ubuntu-with-apt-get

2. Install maven : http://www.mkyong.com/maven/how-to-install-maven-in-ubuntu/

3. Install Git: https://www.digitalocean.com/community/tutorials/how-to-install-git-on-ubuntu-14-04

Checkout code from git by using : git clone https://github.com/abhijitsrivastava/GetProductInfo.git

4. Go inside folder : /GetProductInfo/ProductInfoMirror

5. execute command: mvn install

6. execute command : mvn jetty:run (this will start the server)

7. Now you can access the app in the browser at http://<servername>:8080



