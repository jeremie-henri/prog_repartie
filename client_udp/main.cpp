#include <arpa/inet.h>
#include <stdio.h>
#include <sys/socket.h>
#include <unistd.h>
#include <iostream>
#include <string.h>
#include <time.h>
#include <stdlib.h>

#define BACKLOG 50
#define NB_CLIENTS 100
#define TAILLE_BUFFER 5

using namespace std;

[[noreturn]]void exitErreur(const char * msg) {
    perror(msg);
    exit( EXIT_FAILURE);

}

int main(int argc, char * argv[]) {

    char* server_name = argv[1];
    int server_port = 1;
    server_port = atoi(argv[2]);

    struct sockaddr_in server_address;
    memset(&server_address, 0, sizeof(server_address));
    server_address.sin_family = AF_INET;

    inet_pton(AF_INET, server_name, &server_address.sin_addr);

    server_address.sin_port = htons(server_port);

    int sock;
    if ((sock = socket(PF_INET, SOCK_DGRAM, 0)) < 0) {
        printf("impossible de crée le socket \n");
        return 1;
    }

    string msg;
    cin >> msg;

    int len = sendto(sock, msg.c_str(), msg.size(), 0,(struct sockaddr*)&server_address, sizeof(server_address));

    char buffer[100];
    recvfrom(sock, buffer, len, 0, NULL, NULL);

    buffer[len] = '\0';
    printf("recu: '%s'\n", buffer);

    close(sock);
    return 0;
}
