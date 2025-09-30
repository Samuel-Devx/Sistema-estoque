import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        Estoque estoque = new Estoque();    
        int op;
       
        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Adicionar produto");
            System.out.println("2 - Mostrar estoque");
            System.out.println("3 - Vender produto");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");
          
            op = teclado.nextInt();
            teclado.nextLine(); // limpa o buffer do Enter

            switch (op) {
                case 1:
                    // Adicionar produto
                    System.out.println("\n--- Cadastro de Produto ---");
                    System.out.print("Nome: ");
                    String nome = teclado.nextLine();

                    System.out.print("Preço: ");
                    String precoStr = teclado.nextLine().replace(",", ".");
                    double preco = Double.parseDouble(precoStr);

                    System.out.print("Quantidade: ");
                    int quantidade = teclado.nextInt();

                    System.out.print("SKU: ");
                    int SKU = teclado.nextInt();

                    Produto produto = new Produto(nome, preco, quantidade, SKU);
                    estoque.adicionarProduto(produto);
                    System.out.println("Produto adicionado com sucesso!");
                    break;

                case 2:
                    // Mostrar estoque
                    System.out.println("\n--- Estoque Atual ---");
                    estoque.mostrarEstoque();
                    break;

                case 3:
                    // Vender produto
                    System.out.print("Digite o nome do produto: ");
                    String nomesString = teclado.next();

                    System.out.print("Digite a quantidade a ser vendida: ");
                    int qtd = teclado.nextInt();

                    estoque.venderProduto(nomesString, qtd);
                    break;

                case 4:
                    // Sair
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (op != 4); 
        
        teclado.close();
    }
}
