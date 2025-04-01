#include <stdio.h>
#include <stdlib.h>

int main() {
    // Compile the Java program using javac
    int compileStatus = system("javac src/main/java/backend/entertainment/*.java lib/ src/main/java/frontend/controllers/*.java"); // entertainment package files
    if (compileStatus != 0) {
        printf("Error: Failed to compile Formatter.java\n");
        return 1;
    }

    // Run the Java program with input.txt as a command-line argument
    int runStatus = system("java Formatter input.txt");
    if (runStatus != 0) {
        printf("Error: Failed to execute Formatter with input.txt\n");
        return 1;
    }

    return 0;
}

// convert to exe -> gcc test.c -o test.exe
// excecute -> ./test.exe