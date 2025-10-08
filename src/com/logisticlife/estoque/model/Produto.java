package com.logisticlife.estoque.model;

public class Produto {
    //Atributos 
    private String nome;
    private double preco;
    private int quantidade;
    private final int SKU;
    public int totalAdicionados = 0;
    //Construtor

    public Produto(String nome, double preco, int quantidade, int SKU) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.SKU = SKU;
    }
    //Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public int getSKU() {
        return SKU;

    }




    //Métodos
    public void adiocionarEstoque(int quantidade) {
        this.totalAdicionados += quantidade;
        this.quantidade += quantidade;
    }
    public void mudarPreco (double preco) {
        this.preco = preco;
    }
    public void venderProduto(int SKU, int quant) {
        if (quant <= this.quantidade) {
            System.out.println("Venda realizada com sucesso!");
            this.quantidade -= quant;
        } else {
            System.out.println("Quantidade insuficiente em estoque.");
        }
    }


@Override
    public String toString() {
        return "Nome: " + nome +
            " | Quantidade: " + quantidade+
            " | SKU: " + SKU +
            " | Preço: R$ " + preco ;
    }

    
    
   
}