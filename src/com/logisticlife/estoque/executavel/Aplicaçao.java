package com.logisticlife.estoque.executavel;

import com.logisticlife.estoque.model.Estoque;
import com.logisticlife.estoque.model.Produto;

public class  Aplicaçao {
    public static void main(String[] args) throws Exception {
        //Cadastro de produtos
        Estoque estoque = new Estoque();
        Produto produto1 = new Produto("Shampoo", 20.99, 90, 807123);
        Produto produto2 = new Produto("Condicionador", 25.99, 50, 807124);
        Produto produto3 = new Produto("Sabonete", 5.99, 200, 807125);
        //Adicionando produtos ao estoque
        estoque.adicionarProduto(produto1);
        estoque.adicionarProduto(produto2);
        estoque.adicionarProduto(produto3);

        //Mostrando estoque
        estoque.procurarProduto(807123);
        //Mostrando estoque
        estoque.mostrarEstoque();
        //Vendendo produtos

        
        //Atualizando estoque e preço
        estoque.adicionarProduto(produto2);
        estoque.adicionarProduto(produto3);

}
 }
