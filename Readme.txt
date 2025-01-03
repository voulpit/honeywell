
==  Building the applications (for Linux) ==
	./build.sh
Or check the steps provided there.


== Running the applications (Linux command line) ==
Server:
	cd ATM-server; ../mvnw spring-boot:run
Client (new tab):
	cd ../ATM-console/target; java -cp ATM-console-1.0-SNAPSHOT.jar:core.jar com.honeywell.atm.console.Main

Or run the following classes in your IDE:
	com.honeywell.atm.server.AtmServerApplication
	com.honeywell.atm.console.Main


== Accounts for testing ==
2938473737474845 / 1234
9299111130128890 / 2025
