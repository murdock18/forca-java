package br.com.zup.estrelas.desafio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
	
public class Forca {
	
	public static String deAccent(String str) {
	    String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	public static String[] lerArquivo() throws IOException {

		FileReader estruturaArquivo = new FileReader("C:\\Users\\Zupper\\Desktop\\Desafio-Final\\palavras.txt", StandardCharsets.UTF_8);
		BufferedReader leitorArquivo = new BufferedReader(estruturaArquivo);
		String linha;
		int count = 0;
		while ((linha = leitorArquivo.readLine()) != null) {
			count++;
		}

		String[] palavras = new String[count];

		leitorArquivo.close();
		estruturaArquivo.close();

		estruturaArquivo = new FileReader("C:\\Users\\Zupper\\Desktop\\Desafio-Final\\palavras.txt", StandardCharsets.UTF_8);
		leitorArquivo = new BufferedReader(estruturaArquivo);
		count = 0;

		while ((linha = leitorArquivo.readLine()) != null) {
			palavras[count] = linha;
			count++;
		}

		leitorArquivo.close();
		estruturaArquivo.close();

		return palavras;

	}

	public static String pegaPalavraAleatoria(String[] palavras) {

		Random r = new Random();

		int a = r.nextInt(palavras.length);

		return palavras[a];

	}

	public static void ganhou(Scanner teclado) throws IOException {

		System.out.println("Você Ganhou \n"
				+ " Digite uma palavra nova para a Forca");
		String palavra = teclado.next();

		String novaPalavra = palavra;
		FileWriter writer = new FileWriter("C:\\Users\\Zupper\\Desktop\\Desafio-Final\\palavras.txt", true);
		writer.append(novaPalavra);
		writer.close();
		
		System.out.println("Obrigado por Jogar, volte Sempre ^^");
	}

	public static boolean verificaFimDoJogo(String palavraEscolhida, char[] letras) {
		
		String paravraSemEspaco = palavraEscolhida.replaceAll(" ", "");
		
		for(int i =0; i < paravraSemEspaco.length(); i++){
			
			if(!verificaLetra(letras, paravraSemEspaco.toLowerCase().charAt(i))) {
			    return true;
			}
		}
		return false;
	}

	public static void desenhaForca (String palavraEscolhida, char [] letras, String palavraSemAssento) {
		
		System.out.print("Digite uma letra \n"
				+ " A palavra é: ");
		
		for(int i =0; i < palavraEscolhida.length(); i++){
				
			if(verificaLetra(letras, palavraSemAssento.toLowerCase().charAt(i))) {
			    System.out.print(palavraEscolhida.charAt(i) +" ");
			} else if (palavraSemAssento.charAt(i) == 32) {
				System.out.print("  ");
			} else {
				System.out.print("_ ");
			}
		}
		
		System.out.println("");
		
		for(int c = 0; c < letras.length; c++){
			if(letras[c] == 0) {
				break;
		}
			System.out.print(letras[c]+", ");
		}
		
		System.out.println("");
	}

	public static boolean verificaLetra (char[] letras, char letra) {
		
		for(int c = 0; c < letras.length; c++){
			if(letras[c] == 0) {
				return false;
		}
			if(letras[c] == letra){
			      return true;
			}		
		}
		
		return false;
	}
	
	public static void main(String[] args) throws IOException {

		Scanner teclado = new Scanner(System.in);

		System.out.println("Bem Vindo! \n" 
				+ "Este é o Jogo da forca Estrelas. \n" 
				+ "Vamos Jogar!");

		String[] palavras = lerArquivo();
		String palavraEscolhida = pegaPalavraAleatoria(palavras);

		char[] letras = new char[26];
		
		int contador = 0;
		
		int tentativas = 0;
		int totalTentativas = 5;
		
		String palavraSemAssento = deAccent(palavraEscolhida);
		System.out.println(palavraEscolhida);
		
		do {
						
			desenhaForca(palavraEscolhida, letras, palavraSemAssento);
			
			char letra = teclado.next().toLowerCase().charAt(0);
			
			if(palavraSemAssento.indexOf(letra) < 0){
				tentativas++;
				if (tentativas < totalTentativas) {
					System.out.printf("\n-> Você errou pela %dª vez. Tente de novo!\n",tentativas);
				} else {
					System.out.printf("\n-> Você errou pela %dª vez.\n",tentativas);
				}
			}
			
			if(!verificaLetra(letras, letra)) {
				letras[contador] = letra;
				contador++;
			}	
			
		} while (verificaFimDoJogo(palavraSemAssento, letras) && tentativas <= totalTentativas);
		
		if(tentativas > totalTentativas) {
			   System.out.printf ("Você Perdeu, a palavra era %s",palavraEscolhida);
			   System.out.println("\nObrigado por Jogar, Volte Sempre ^^");
			} else {
			System.out.printf("%s Parabéns ",palavraEscolhida);
			  ganhou(teclado);
			}

		teclado.close();
	}
}
