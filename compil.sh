javac -d classes -classpath .:classes src/RegistryClients.java
cd classes
jar cvf ../lib/RegistryClients.jar RegistryClients.class
cd ..

javac -d classes -classpath .:classes src/Chat.java
cd classes
jar cvf ../lib/Chat.jar Chat.class
cd ..

javac -d classes -classpath .:classes src/ChatImpl.java
cd classes
jar cvf ../lib/ChatImpl.jar ChatImpl.class
cd ..

javac -d classes -classpath .:classes src/Client_itf.java
cd classes
jar cvf ../lib/Client_itf.jar Client_itf.class
cd ..

javac -d classes -classpath .:classes src/RegistryClientsImpl.java
cd classes
jar cvf ../lib/RegistryClientsImpl.jar RegistryClientsImpl.class
cd ..

javac -d classes -cp .:classes:lib/Chat.jar:/lib/ChatImpl.jar:/lib/RegistryClients.jar:/lib/RegistryClientsImpl.jar src/ChatServer.java

javac -d classes -cp .:classes:/lib/Client_itf.jar:/lib/Chat.jar:/lib/RegistryClients.jar src/ChatClient.java