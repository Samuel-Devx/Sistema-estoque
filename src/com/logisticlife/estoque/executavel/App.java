package com.logisticlife.estoque.executavel;
import com.logisticlife.estoque.model.Produto;
import com.logisticlife.estoque.model.Estoque;
import com.logisticlife.estoque.mysqlconector.ConectorMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Estoque estoque = new Estoque();
        int op;
       
        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Adicionar produto");
            System.out.println("2 - Deletar produto");
            System.out.println("3 - Mostrar estoque");
            System.out.println("4 - Vender produto");
            System.out.println("5 - Controle de vendas");
            System.out.println("6 - Conferir 100% Vendidos");
            System.out.println("7 - Encerrar");
            System.out.print("Escolha uma opção: ");
          
            op = teclado.nextInt();
            teclado.nextLine(); // limpa o buffer do Enter

            switch (op) {
                case 1:
                    // Adicionar produto
                    System.out.println("\n--- Cadastro Produto---");
                    System.out.print("Nome: ");
                    String nome = teclado.nextLine();

                    System.out.print("Preço: ");
                    String precoStr = teclado.nextLine().replace(",", ".");
                    double preco = Double.parseDouble(precoStr);

                    System.out.print("Quantidade: ");
                    int quantidade = teclado.nextInt();

                    System.out.print("SKU: ");
                    int SKU = teclado.nextInt();
                    String skuStrin = String.valueOf(SKU);
                    if (skuStrin.length() < 8){
                        System.out.println("O SKU deve ter pelo menos 8 Digitos!");
                        break;
                    }
                    else {
                        Produto produto = new Produto(nome, preco, quantidade, SKU);
                        estoque.adicionarProduto(produto);
                        System.out.println("Produto adicionado com sucesso!");
                        //---------------------Conexão com my Sql------------------------
                        String sql = "INSERT INTO produtos (nome, preco, quantidade, SKU) VALUES (? , ?, ?, ?)";
                        try (Connection coon = ConectorMySql.getConexao();
                             PreparedStatement smtm = coon.prepareStatement(sql)) {
                            smtm.setString(1, nome);
                            smtm.setDouble(2, preco);
                            smtm.setInt(3, quantidade);
                            smtm.setInt(4, SKU);
                            int linhasAfetadas = smtm.executeUpdate();
                            if (linhasAfetadas > 0) {
                                System.out.println("Produto adionado no Banco de dados");
                            }
                        } catch (SQLException e) {
                            System.out.println("Erro ao conectar " + e.getMessage());
                        }



                    }
                    break;
                case 2: {
                    // Deletando Dados
                    System.out.print("Qual o nome do produto que deseja deletar: ");
                    String nomeProduto = teclado.next();
                    estoque.deletarDados(nomeProduto);
                }

                case 3:
                    // Mostrar estoque
                    estoque.mostraEstoqueBanco();
                    break;

                case 4:
                    // Vender produto
                    System.out.print("Digite o nome do produto: ");
                    String nomesString = teclado.next();

                    System.out.print("Digite a quantidade a ser vendida: ");
                    int qtd = teclado.nextInt();

                    estoque.venderProduto(nomesString, qtd);
                    break;
                case 5:
                    estoque.controleVendas();
                    break;
                case 6:
                    System.out.println("\n---Estoque 100% Vendido ---");
                    estoque.mostrarVendidos();
                    break;
                case 7:
                    // Sair
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (op != 7);
        
        teclado.close();
    }
}
