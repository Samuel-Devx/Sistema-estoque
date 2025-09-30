import java.util.ArrayList;

public class Estoque {
    //Atributos
    private ArrayList<Produto> produtos = new ArrayList<>(); 
    //adicionar produtos ao estoque
    public void adicionarProduto(Produto p) {
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
                System.out.println("Venda realizada com sucesso!");
            } else {
                System.out.println("Quantidade insuficiente em estoque.");
            }
            return;
        }
    }
    System.out.println("Produto não encontrado.");
}

    //Mostrar estoque
    public void mostrarEstoque() {
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }

}
