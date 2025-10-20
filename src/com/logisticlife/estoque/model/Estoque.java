package com.logisticlife.estoque.model;
import com.logisticlife.estoque.model.Produto;
import com.logisticlife.estoque.mysqlconector.ConectorMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Estoque {
    //Atributos
    private ArrayList<Produto> produtos = new ArrayList<>();
    private ArrayList<Produto> produtosVendidos = new ArrayList<>();
    private double percentualVendas;
    private int totalVendas = 0;
    public int totalAdicionados = 0;

    public Estoque() throws SQLException {
    }

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
                atualizarVendas(p, quantidade);
                System.out.println("Venda realizada com sucesso!");

            if (p.getQuantidade() == 0){
                Produto vendido = new Produto(p.getNome(), p.getPreco(), 1, p.getSKU());
                atualizarVendas(p, quantidade);
                deletarDados(vendido.getNome());

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


    //Add tabela 100% vendidos
   public void atualizarVendas (Produto p, int quantidade){
       String sql = "INSERT INTO vendas (nome, preco, quantidade, SKU) VALUES (? , ?, ?, ?)";
       try (Connection coon = ConectorMySql.getConexao();

            PreparedStatement smtm = coon.prepareStatement(sql)) {
           smtm.setString(1, p.getNome());
           smtm.setDouble(2, p.getPreco());
           smtm.setInt(3, quantidade);
           smtm.setInt(4, p.getSKU());
           int linhasAfetadas = smtm.executeUpdate();
           if (linhasAfetadas > 0) {
               System.out.println("Produto adionado no Banco de dados");
           }
       } catch (SQLException e) {
           System.out.println("Erro ao conectar " + e.getMessage());
       }
   }
    //tabela vendas
    public void exibirTabelaVendas (){
        String sql = "SELECT * FROM vendas";
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
                String data = rs.getString("data_venda");
                System.out.println("ID: " + id +
                        "| Nome: " + nome +
                        "| Quantidade: " + quantidade +
                        "| SKU: " + sku +
                        "| Preço: R$" + preco +
                        "| Data da venda: " + data );
            }

        } catch (SQLException e) {
            System.out.println("ERRO em buscar banco de dados " + e.getMessage());
        }
    }
    public double percentualDeVendas(){
        String sqlTotalEstoque = "SELECT SUM(quantidade) AS total FROM produtos";
        String sqlTotalVendido = "SELECT SUM(quantidade) AS total FROM vendas";
        //Variaveis
        int totalEstoque = 0;
        int totalVendido = 0;
        try (Connection conn = ConectorMySql.getConexao()){

            try(PreparedStatement smt = conn.prepareStatement(sqlTotalEstoque);
                ResultSet rs = smt.executeQuery())     {
                //esse result set é para pegarmos os dados e usamos como variaveis
                if (rs.next())
                    totalEstoque =  rs.getInt("total");

            } catch (SQLException e) {
                System.out.println("ERRO em conseguir um o percentual " + e.getMessage());
            }
            try (PreparedStatement smtm = conn.prepareStatement(sqlTotalVendido);
                ResultSet rsr = smtm.executeQuery()){
                if(rsr.next()){
                    totalVendido = rsr.getInt("total");
                }


            }
        } catch (SQLException e) {
            System.out.println("ERRO em conseguir um o percentual " + e.getMessage());
        }


        int totalAdicionados = totalEstoque + totalVendido;
        if (totalAdicionados == 0) return  0;
        else return  (totalVendido / (double) totalAdicionados) * 100;

        //trabalhei com 2 try pois consultei 2 tabela
    }


    public void exibirControleDeVendas (){
        double percentual = percentualDeVendas();
        System.out.printf("==========Controle de vendas=======%n ");

        System.out.printf("Percentual de vendas: %.2f%n", percentual);
        System.out.printf("===================================");

    }








}
