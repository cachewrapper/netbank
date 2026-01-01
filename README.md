# 🏦 Bank System - CQRS, Event Sourcing, Event-Driven, Minecraft Integration 🎮

This project is a **banking system** implemented using **CQRS (Command Query Responsibility Segregation)**, **Event Sourcing**, and an **Event-Driven architecture**, with full **integration into Minecraft**.  

---

## ✨ Features
- ⚡ Separation of **commands** (write operations) and **queries** (read operations)  
- 📜 Immutable **event store** capturing all changes to aggregates  
- 🔄 Asynchronous **event-driven updates** to read models  
- 📈 Scalable design allowing independent scaling of read and write sides  
- 🎮 Direct **integration with Minecraft**, enabling in-game banking operations and player interactions  

---

## 🏗 Architecture Overview
- 📝 **Commands** trigger actions in aggregates  
- ⚡ Aggregates generate **events** representing state changes  
- 🗄 **Event Store** persists events in an immutable log  
- 👁 **Read models** are updated asynchronously from events for optimized queries  
- 🚀 **Event Bus** (Kafka) propagates events between services  
- 🎮 **Minecraft plugin layer** interacts with the system, translating in-game actions into commands and events  

---

## 💻 Technology Stack
- ☕ Java, Spring Boot  
- 🐘 Postgres for event storage  
- 🦄 Kafka for event-driven communication  
- 🎮 Minecraft server integration  
- 🗃 Optional read model databases for optimized queries  

---

## 🚀 Getting Started
1. Clone the repository  
2. Configure Postgres and Kafka connections  
3. Deploy the Minecraft plugin on a server  
4. Run the application and use **REST endpoints** or **in-game commands** for banking operations  

---

## 📜 License
MIT
