package com.logisticlife.estoque.model;
import com.logisticlife.estoque.model.Produto;
import java.util.ArrayList;

public class Estoque {
    //Atributos
    private ArrayList<Produto> produtos = new ArrayList<>();
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
                this.totalVendas += quantidade;

                System.out.println("Venda realizada com sucesso!");
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




}
