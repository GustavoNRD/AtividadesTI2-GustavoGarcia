import java.util.*;

public class Principal {
	public static Scanner sc = new Scanner(System.in);
	public static DAO dao = new DAO();
	public static void main(String[] args) {
		
		
		dao.conectar();

        int opcao;
        do {
            System.out.println("1 - Listar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Excluir");
            System.out.println("4 - inserir");
            System.out.println("5 - Finalizar");

            opcao = sc.nextInt();
            
            switch (opcao) {
            	case 1:
            		ListarProdutos();
                break;
                case 2:
                    AtualizarProdutos();
                    break;
                case 3:
                    //ExcluirProduto();
                    break;
                case 4:
                    //InserirProduto();
                    break;
                case 5:
                    System.out.println("Finalizando o programa.");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }

        } while (opcao != 5);
		
		

		
		
		
		

		
		dao.close();
		sc.close();
	}
	
	
	public static void ListarProdutos() {
		Produtos[] produto = dao.getProdutos();
		for(int i = 0; i < produto.length; i++)
		{
			System.out.println(produto[i].toString());
		}
		sc.nextLine();
	}
	
	public static void AtualizarProdutos()
	{
		System.out.println("Qual o ID do produto a ser atualizado?");
		int IDProduto = sc.nextInt();
		sc.nextLine();
		
		System.out.println("Qual o nome novo do produto?");
		String nomeNovo = sc.nextLine();
		
		System.out.println("Qual o preço novo do produto?");
		double preçoNovo = sc.nextDouble();
		sc.nextLine();
		
		System.out.println("Qual a validade nova do produto?");
		int validadeNova = sc.nextInt();
		sc.nextLine();
		
		dao.atualizarProduto(IDProduto, nomeNovo, preçoNovo, validadeNova);
	}
	
	
	
}