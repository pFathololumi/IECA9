1) Install HSQL (hsqldb.org)
2) Start HSQL Server
	cd lib/hsqldb
	java -cp ./hsqldb.jar org.hsqldb.server.Server
3) Start HSQL Database Manager
	java -cp hsqldb.jar org.hsqldb.util.DatabaseManagerSwing
4) Connect to the server
	Set 'type' to 'HSQL Database Engine Server'
5) Build and Deploy the project
	./build&deploy.sh
6) Start Tomcat
	./startTomcat.sh
6) configure database by calling followin URL
	http://localhost:8080/stockmarket/confdb

