# Walletman
This is my diploma project. It consists of two systems - HRMS(Human Resources Management System) and E-wallet.

# HRMS
It consists of two modules: main & salary-payer.
In the main module as a company you have ability to add, remove, update your employees.

Salary-payer pays salary by schedule(It reads companies info from main module using Feign and send TransferMoneyEvent(companyId, employeeId, salary) to E-Wallet);

# E-Wallet
In the E-Wallet you can look at your recent transactions, send money to another account, or exchange currency;


When in HRMS creates new employee or company - system creates an Account in E-Wallet

# Details of implementation
I'm using microservices architecture, Spring(Boot, Cloud, Data), PostgreSQL, RabbitMQ.

[![Medium](https://travis-ci.org/yeaxi/e-wallet.svg?branch=master)]