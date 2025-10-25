# Client-Server Dictionary Application

## Overview

This project implements a **multi-threaded dictionary server** in Java that allows multiple clients to **search, add, remove, and update words** concurrently. The system uses **TCP sockets** for reliable communication and a **custom worker pool architecture** to handle multiple client requests efficiently.

The design ensures **low resource consumption**, **fast responsiveness**, and **safe concurrency management**.

---

## Features

- **Search**: Clients can query the meaning of a word.
- **Add**: Clients can add a new word with its meaning to the dictionary.
- **Remove**: Clients can delete an existing word from the dictionary.
- **Update**: Clients can update the meaning of an existing word.

---

## Highlights

- **Concurrent Handling**: Multiple clients can connect simultaneously without performance degradation.
- **Worker Pool Architecture**: Uses a fixed number of reusable worker threads to process requests, instead of creating a new thread per connection.
- **Thread Safety**: Shared resources, like the client queue, are managed using **BlockingQueue** to prevent concurrency issues.
- **Reliable Transmission**: Communication between client and server uses TCP sockets.

---

## Requirements

- Java 8 or above
- TCP/IP capable environment

---

## How to Run (Without `.jar`)

### 1. Compile the Project

```bash
javac *.java
```

### 2. Run the Server

```bash
java DictionaryServer <port> <dictionary-file>
```

### 3. Run the Client

```bash
java DictionaryClient <server-address> <server-port>
```
