package com.logisticlife.estoque.model;
import com.logisticlife.estoque.model.Produto;
import com.logisticlife.estoque.mysqlconector.ConectorMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Estoque {
    //Atributos
    private ArrayList<Produto> produtos = new ArrayList<>();
    private ArrayList<Produto> produtosVendidos = new ArrayList<>();
    private double percentualVendas;
    private int totalVendas = 0;
    public int totalAdicionados = 0;
    //adicionar produtos ao estoque
    public void adicionarProduto(Produto p) {
        totalAdicionados += p.getQuantidade();
        produtos.add(p);
    }
    //Procurar produto pelo SKU
    public Produto procurarProduto (int SKU) {
        for (Produto p : produtos) {
            if (p.getSKU() == SKU){
                System.out.println(p);
                return p;
            }
        }
        return null;
    }
    //Vender produto
    public void venderProduto(String nome, int quantidade) {
    for (Produto p : produtos) {
        if (p.getNome().equalsIgnoreCase(nome)) {
            if (p.getQuantidade() >= quantidade) {
                p.setQuantidade(p.getQuantidade() - quantidade);
                atualizarProdutoBanco(p);
                this.totalVendas += quantidade;
                System.out.println("Venda realizada com sucesso!");
            if (p.getQuantidade() == 0){
                produtos.remove(p);
                produtosVendidos.add(p);
            }
            } else {
                System.out.println("Quantidade insuficiente em estoque.");
            }
            return;
        }
    }
    System.out.println("com.logisticlife.estoque.model.Produto não encontrado.");
}
    public double getPercentualVendas () {
        if (totalAdicionados == 0) return 0;
        return (totalVendas / (double)totalAdicionados) * 100;
    }
    public int getTotalVendas (){
        return totalVendas;
    }
    public void controleVendas (){
        System.out.printf("%n========Controle de Vendas========%n");
        System.out.printf("Total no adicionados no estoque: %d %n", totalAdicionados);
        System.out.printf("Total de Vendas: %d %n", getTotalVendas());
        System.out.printf("Percentual de vendas: %.1f%% ", getPercentualVendas());
    }

    //Mostrar estoque
    public void mostrarEstoque() {
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }

    //Mostrar os produtos 100% vendidos
    public void mostrarVendidos() {
        for (Produto p : produtosVendidos) {
            System.out.println(p);
        }
    }
    //Atulizar quantidade dentro do banco de dados
    private void atualizarProdutoBanco (Produto p){
        String sql = "UPDATE produtos SET quantidade = ?  WHERE nome = ?";

        try(Connection conn = ConectorMySql.getConexao();
            PreparedStatement smtm = conn.prepareStatement(sql)) {

            smtm.setInt(1, p.getQuantidade());
            smtm.setString(2, p.getNome());

            int linhasAfetadas = smtm.executeUpdate();

            if (linhasAfetadas > 0){
                System.out.println("Produto atulizado no Banco de dados");
            }
            else {
                System.out.println("Produto não encontrado");
            }
        } catch (SQLException e) {
            System.out.println("ERRO na atualização " + e.getMessage());
        }


    }
    //-------Delete um dado da tabela
    public void deletarDados (String nome){
        String sql = "DELETE FROM produtos WHERE nome = ?";
        try (Connection conn = ConectorMySql.getConexao();
            PreparedStatement smtm = conn.prepareStatement(sql)){

            smtm.setString(1, nome);
            int linhasAfetadas = smtm.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.printf("%n Produto %s deletado do banco de dados", nome);
            }
            else {
                System.out.println("Produto não encontrado!");
            }
        } catch (SQLException e) {

            System.out.println("ERRO em deletar o dado " + e.getMessage());
        }
    }

    //--------Mostrando produtos select
    public void mostraEstoqueBanco() {
        String sql = "SELECT * FROM produtos";
        try(Connection conn = ConectorMySql.getConexao();
            PreparedStatement smtm = conn.prepareStatement(sql);
            ResultSet rs = smtm.executeQuery();){

            System.out.println("\n--- Lista de Produtos ---");
            while (rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preco");
                int quantidade = rs.getInt("quantidade");
                int sku = rs.getInt("SKU");

                System.out.println("ID: " + id +
                                    "| Nome: " + nome +
                                    "| Quantidade: " + quantidade +
                                    "| SKU: " + sku +
                                    "| Preço: R$" + preco);
            }

        } catch (SQLException e) {
            System.out.println("ERRO em buscar banco de dados " + e.getMessage());
        }
    }








}
