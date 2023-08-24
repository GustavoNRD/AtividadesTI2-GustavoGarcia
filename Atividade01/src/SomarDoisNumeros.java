import java.util.*;

class SomarDoisNumeros {
	public static Scanner sc = new Scanner(System.in);
	
	public static void main (String args[])
	{
		//variaveis
		int N1, N2, soma;
		
		//leitura da entrada
		System.out.println("Digite o primeiro número");
		N1 = sc.nextInt();
		
		System.out.println("Digite o segundo número");
		N2 = sc.nextInt();
		
		//somar 
		soma = N1 + N2;
		
		//saida
		
		System.out.println("A soma dos dois números é " + soma );		
	}
}
