# Sistema de Estoque em Java

Um sistema simples de controle de estoque desenvolvido em Java. Permite cadastrar produtos, listar o estoque, vender produtos e atualizar informações, tudo via console. Ideal para aprendizado de programação orientada a objetos (POO) e manipulação de coleções em Java.

---

## Funcionalidades

- Adicionar novos produtos ao estoque  
- Listar todos os produtos cadastrados  
- Vender produtos com verificação de quantidade em estoque  
- Atualizar preço ou quantidade de produtos (via métodos na classe com.logisticlife.estoque.model.Produto)  
- Menu interativo no console  
- Percentual de vendas
- Conexão com banco de dados com CRUD completo 
---

## Tecnologias

- Java SE  
- Coleções (`ArrayList`)  
- Programação Orientada a Objetos (POO)  
- Scanner para interação com o usuário  
- MySql
---

## Estrutura do Projeto

- `com.logisticlife.estoque.executavel.App.java` → Classe principal com o menu interativo.
- `com.logisticlife.estoque.model.Estoque.java` → Classe que gerencia a lista de produtos.
- `com.logisticlife.estoque.model.Produto.java` → Classe que representa um produto (nome, preço, quantidade, SKU).  
- `com.logisticlife.estoque.mysqlconector.ConectorMySql` → Classe que faz a conexão com o banco de dados MySql.
---

## Como Executar

1. Clone este repositório:
```bash
git clone https://github.com/Samuel-Devx/Sistema-estoque
