# 🛒 E-Commerce Backend API

A backend system for an e-commerce web application, built with Java Spring Boot. This project handles user authentication, product listing, cart management, order placement, review system, and more.

## 📌 1. Features

-  User authentication (JWT)
-  Role-based authorization (ADMIN / USER)
-  Product & category management
-  Shopping cart and checkout
-  Order placement with multiple items
-  Review and rating system
-  Address & payment method handling
-  PDF invoice generation (optional)
-  Cloudinary for image storage
-  Admin-only product/category control

---

##  2. Tech Stack

- **Backend:** Java 17, Spring Boot, Spring Security, JPA
- **Database:** MySQL
- **Libraries:** Lombok, MapStruct, JWT, iTextPDF, Cloudinary SDK
- **Build Tool:** Maven
- **Documentation & Testing:** Postman, Swagger (if configured)

---

## 3. ERD – Entity Relationship Diagram

![ERD](https://raw.githubusercontent.com/Karot1712/e-commerce/main/docs/erd.png)

> ERD includes: User, Product, Category, Order, OrderItem, Review, Payment, Address.

---

## 🚀 4. Getting Started

### 4.1 Clone and Build

```bash
git clone https://github.com/Karot1712/e-commerce.git
cd e-commerce
./mvnw clean install
