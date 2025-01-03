cd core
../mvnw clean install package

cd ../ATM-server
../mvnw clean package

cd ../ATM-console
../mvnw clean package
cp ../core/target/core-1.0-SNAPSHOT.jar target/core.jar
