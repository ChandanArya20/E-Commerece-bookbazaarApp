# E-Commerce Bookstore Backend

This is the backend service for the E-Commerce Bookstore application. It is developed using Spring Boot and serves as the backbone of the bookstore application.
It provides a lot of API to handle HTTP request and also manage databse.

## Live Demo

Frontend App Link: https://booksbazaar.netlify.app/

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 17 or higher installed.
- You can use Eclipse or intelliJ IDEA as IDE as per your preference
- Apache Maven installed for building and managing dependencies.
- A database server MySQL installed and configured.
  
  Note: You can use any SQL database but you have to make changes little bit in properties file of this project


### Installation

1. Clone the repository:

   ```shell
   git clone https://github.com/ChandanArya20/E-Commerece-bookbazaarApp.git
   ```
2. Now import this spring boot project in IDE and you are good to go
   
3. Use IDE to further build and run this project


## Usage
Provide information on how to use your backend service, including any important instructions, endpoints, and examples. You can also include details on how to integrate it with your React frontend.

## API Documentation
For API documentation, You can access through swagger link because this application is configured with swagger I have provided two links the first one is for only when this project is running on local machine and the second live link you can use to just see a demo of the api documentation which is live because this project is deployed somewhere, go and visite and have a look

Local Link : http://localhost:8080/swagger-ui.html

Live Link : http://booksbazaar.up.railway.app/swagger-ui.html

Note : You can change the host name in local link as per your requirement and need.

## Configuration

- Before running actual springboot project make sure You have replaced url and password of you DBMS in properties file of project according to enviroment

- This project includes different properties file for different enviroment so by default it will start in development enviroment but if you put 'ENV=prod' in application.properties file that tim3 production enviroment will start to running

## Contributing
If you would like to contribute to the project, please follow the guidelines in CONTRIBUTING.md.

## License
This project is licensed under the MIT License. You are free to use, modify, and distribute this code as per the terms of the license.
   Ram
