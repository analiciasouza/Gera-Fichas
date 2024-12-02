package geraFicha.servico;

import geraFicha.entidade.Personagem;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class PersonagemServico {
    private Map<String, String> usuarios;
    private Map<String, List<Personagem>> personagensPorUsuario;
    
    Scanner scanner = new Scanner(System.in);
    public PersonagemServico(Map<String, String> usuarios, Map<String, List<Personagem>> personagensPorUsuario) {
        this.usuarios = usuarios;
        this.personagensPorUsuario = personagensPorUsuario;
    }

    List<Personagem> teste = new ArrayList<>();
        public void cadastrarPersonagem(String usuario) {
            if (!usuarios.containsKey(usuario)) {
                System.out.println("Usuário não encontrado.");
                return; // encerra o método se o usuário não for encontrado
            }

            List<Personagem> personagens = personagensPorUsuario.get(usuario); // obtém a lista de personagens do usuário

            if (personagens.size() >= 5) {
                System.out.println("Você já cadastrou o número máximo de 5 personagens.");
                return; // encerra o método se o número máximo for alcançado
            }

            System.out.print("Digite o nome do personagem: ");
            String nome = scanner.next(); // lê o nome do personagem

            System.out.println("Escolha a classe do personagem:");
            System.out.println("1. Guerreiro");
            System.out.println("2. Ladino");
            System.out.println("3. Curandeiro");
            System.out.println("4. Mago");
            System.out.println("5. Bardo");
            System.out.println("6. Druida");
            System.out.print("Digite o número da classe: ");
            int classeEscolhida = scanner.nextInt(); // lê a escolha da classe

            String classe = "";
            switch (classeEscolhida) {
                case 1:
                    classe = "Guerreiro";
                    break;
                case 2:
                    classe = "Ladino";
                    break;
                case 3:
                    classe = "Curandeiro";
                    break;
                case 4:
                    classe = "Mago";
                    break;
                case 5:
                    classe = "Bardo";
                    break;
                case 6:
                    classe = "Druida";
                    break;
                default:
                    System.out.println("Classe inválida.");
                    return; // encerra o método se a classe for inválida
            }

            Personagem personagem = new Personagem(nome, classe); // cria o personagem com o nome e classe
            personagens.add(personagem); // adiciona o personagem à lista
            System.out.println("Personagem cadastrado com sucesso.");

            System.out.println("Atributos do personagem:");
            System.out.println("Nome: " + personagem.getNome());
            System.out.println("Classe: " + personagem.getClasse());
            System.out.println("Vida: " + personagem.getVida() + " (10 vida base + d10)" + personagem.getVida() + ")");
            System.out.println("Força: " + personagem.getForca() + " (d6)");
            System.out.println("Destreza: " + personagem.getDestreza() + " (d6)");
            System.out.println("Inteligência: " + personagem.getInteligencia() + " (d6)");
        }

        public void exibirPersonagens(String usuario) {
            if (!usuarios.containsKey(usuario)) {
                System.out.println("Usuário não encontrado.");
                return;
            }

            List<Personagem> personagens = personagensPorUsuario.get(usuario);

            if (personagens.isEmpty()) {
                System.out.println("Nenhum personagem cadastrado para este usuário.");
            }

            else {
                System.out.println("\nPersonagens cadastrados para " + usuario + ":");
                for (Personagem personagem : personagens) {
                    System.out.println("\nNome: " + personagem.getNome());
                    System.out.println("Classe: " + personagem.getClasse());
                    System.out.println("Vida: " + personagem.getVida());
                    System.out.println("Força: " + personagem.getForca());
                    System.out.println("Destreza: " + personagem.getDestreza());
                    System.out.println("Inteligência: " + personagem.getInteligencia());
                    System.out.println(); // linha em branco entre os personagens
                }
            }
        }



public void excluirPersonagem(String usuario) {


    } }