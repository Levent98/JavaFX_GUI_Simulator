
---

# JavaFX GUI Simulator (Multithreaded Serial Communication Interface)

## Overview

This project implements a JavaFX-based graphical user interface designed to simulate and interact with an external system in a structured and responsive manner.

The application demonstrates a clean separation between UI and communication layers, while leveraging multithreading to ensure a non-blocking user experience.

The system communicates with an external software over four independent serial (COM) ports, enabling parallel data exchange and simulation of multi-channel device interaction.

---

## Features

* JavaFX-based graphical user interface
* Multithreaded architecture for high responsiveness
* Asynchronous communication model
* Communication over 4 independent serial (COM) ports
* Parallel data handling across multiple channels
* Non-blocking UI design
* Clean separation of concerns (UI and communication logic)
* Real-time data exchange and visualization

---

## Architecture

The system follows a layered architecture:

### 1. UI Layer

* Built using JavaFX
* Handles user interactions and visual updates
* Runs on the JavaFX Application Thread

### 2. Communication Layer

* Manages serial communication with external software
* Handles sending and receiving data over COM ports
* Abstracts low-level communication from the UI

### 3. Concurrency Model

* Dedicated threads for serial communication
* Each COM port operates independently
* Background threads handle I/O operations
* UI updates are executed safely via `Platform.runLater`

---

## Serial Communication Design

* Supports 4 independent COM ports
* Parallel communication channels for simultaneous data exchange
* Thread-based handling for each communication channel
* Designed to simulate multi-device or multi-interface systems

---

## Technologies Used

* Java (JDK 17+)
* JavaFX
* Multithreading (Thread, Runnable)
* Serial Communication (COM ports)

---


## How It Works

1. User interacts with the GUI
2. Data is sent through the selected COM port
3. Communication threads handle transmission and reception
4. Incoming data is processed in background threads
5. UI is updated safely using JavaFX thread mechanisms

---

## Design Decisions

* Separation of UI and communication logic for maintainability
* Use of multithreading to prevent UI blocking
* Parallel handling of multiple serial channels
* Event-driven updates instead of blocking operations

---



---

## Note

Due to confidentiality constraints, not all components of the project are publicly available in this repository.

The provided implementation highlights the core architecture, multithreading model, and multi-channel serial communication design.

---

## Author

Levent Keskin
Embedded Software Engineer

---
