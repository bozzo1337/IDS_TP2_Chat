javac -d classes -classpath .:classes src/nf.ColorString.java
cd classes
jar cvf ../lib/nf.ColorString.jar nf.ColorString.class
cd ..

javac -d classes -classpath .:classes src/nf.RegistryClients.java
cd classes
jar cvf ../lib/nf.RegistryClients.jar nf.RegistryClients.class
cd ..

javac -d classes -classpath .:classes src/nf.Chat.java
cd classes
jar cvf ../lib/nf.Chat.jar nf.Chat.class
cd ..

javac -d classes -classpath .:classes src/nf.ChatImpl.java
cd classes
jar cvf ../lib/nf.ChatImpl.jar nf.ChatImpl.class
cd ..

javac -d classes -classpath .:classes src/nf.Client_itf.java
cd classes
jar cvf ../lib/nf.Client_itf.jar nf.Client_itf.class
cd ..

javac -d classes -classpath .:classes src/nf.RegistryClientsImpl.java
cd classes
jar cvf ../lib/nf.RegistryClientsImpl.jar nf.RegistryClientsImpl.class
cd ..

javac -d classes -cp .:classes:lib/nf.Chat.jar:/lib/nf.ChatImpl.jar:/lib/nf.RegistryClients.jar:/lib/nf.RegistryClientsImpl.jar:/lib/nf.ColorString.jar src/nf.ChatServer.java

javac -d classes -cp .:classes:/lib/nf.Client_itf.jar:/lib/nf.Chat.jar:/lib/nf.RegistryClients.jar src/nf.ChatClient.java