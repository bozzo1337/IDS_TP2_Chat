CC=javac
DESTINATION=classes
CLASSPATH=.:classes:classes/nf:classes/ui
EXEC=ChatServer ChatClientUI
SRC=$(wildcard **/*.java)

all: $(EXEC)

ChatServer: src/nf/ChatServer.java Chat.class ChatImpl.class RegistryClients.class RegistryClientsImpl.class
	$(CC) -d $(DESTINATION) -cp $(CLASSPATH) $<

ChatClientUI: ChatClientUI.java ChatBox.class NameBox.class Client_itf.class Chat.class RegistryClients.class
	$(CC) -d $(DESTINATION) -cp $(CLASSPATH) $<

ChatImpl.class: Chat.class RegistryClients.class Client_itf.class

NameBox.class: ChatBox.class

RegistryClients.class: Client_itf.class

RegistryClientsImpl.class: RegistryClients.class 

%.class: %.java
	$(CC) -d $(DESTINATION) -cp $(CLASSPATH) $^
