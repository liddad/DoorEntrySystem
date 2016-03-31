#include <stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <netdb.h>
#include "client.h"

int setupClient(char* ipAdd){
    /* variables for server comms */ 
    int sock;
    struct sockaddr_in server;
    struct hostent *serv;
    
    /*create socket*/
    sock = socket(AF_INET , SOCK_STREAM , 0);
    if(-1 == sock) {
	printf("Could not create socket");
	return 1;
    }
    puts("Socket created");

    serv = gethostbyname("192.168.1.94");
    if(serv == NULL){
	printf("no such host");
	return 1;
    }
    bzero((char *) &server, sizeof(server));
    bcopy((char *) serv->h_addr,
    (char *)&server.sin_addr.s_addr,
    serv->h_length);
    //CHANGE THIS FOR DEMO
    
    server.sin_addr.s_addr = inet_addr(ipAdd);
    server.sin_family = AF_INET;
    server.sin_port = htons(6100);
    

    //Connect
    if ( connect(sock , (struct sockaddr *)&server , sizeof(server)) < 0 ) {
	perror("connect failed");
	return 1;
    }
    puts("Connected\n");

    return sock;
}

char* sendAndRecieve(char* server_reply, char* ndefMsg, int sock){
    //Send the read message
    if ( send(sock , ndefMsg , strlen(ndefMsg), 0) <0) {
	puts("Send failed");
    }

    //Receive reply from server
    if( recv(sock , server_reply , 1000 , 0) < 0 ){
	puts("recv failed");
    }
    return server_reply;
}