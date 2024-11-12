# PDV (Ponto de Venda) - Projeto em Java com Spring

## Índice

- [Descrição do Projeto](#descrição-do-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Funcionalidades](#funcionalidades)
- [Pré-requisitos](#pré-requisitos)
- [Como Executar o Projeto](#como-executar-o-projeto)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Contribuição](#contribuição)

## Descrição do Projeto

Este projeto é uma aplicação de Ponto de Venda (PDV) desenvolvida em Java utilizando o framework Spring. O objetivo principal é revisar e consolidar conceitos fundamentais de programação em Java e as práticas do Spring, além de implementar funcionalidades comuns em sistemas de PDV, como gerenciamento de produtos e vendas.

## Tecnologias Utilizadas

- **Java 17** ou superior
- **Spring Boot** (v3.x)
- **Spring Data JPA**
- **MYSQL** (banco de dados)
- **Maven** (gerenciador de dependências)
- **JWT** 

## Funcionalidades

- Cadastro de usuário
- Login
- Cadastro de produtos
- Registro de vendas

## Pré-requisitos

Antes de começar, verifique se você possui os seguintes pré-requisitos:

- Java 17 ou superior instalado
- Maven instalado
- IDE de sua escolha (como IntelliJ IDEA, Eclipse ou Spring Tool Suite)

## Como Executar o Projeto

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/joeltonken/pdv
   ```
2. Navegue até o diretório do projeto:

   ```bash
    cd pdv
   ```
   
3. Compile e inicie a aplicação:

   ```bash
    mvn spring-boot:run
   ```
   
Acesse a aplicação: Abra o navegador e vá até http://localhost:8080.

## Estrutura do Projeto

```
pdv-java-spring/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── estudos.br/
│   │   │           └── pdv/
│   │   │               ├── config/
│   │   │               ├── controllers/
│   │   │               ├── dtos/
│   │   │               ├── entities/
│   │   │               ├── exceptions/
│   │   │               ├── repositories/
│   │   │               ├── security/
│   │   │               ├── services/
│   │   │               └── Application.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── com.estudos.br.pdv/
│                   └── PdvApplicationTests
└── pom.xml
```

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para enviar um pull request ou abrir um issue para discutir melhorias ou correções.
