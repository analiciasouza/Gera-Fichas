package geraFicha.servico;

import geraFicha.database.DatabaseService;
import geraFicha.entidade.Personagem;

import java.util.*;

public class Login {
    private static final Scanner scanner = new Scanner(System.in);
    private Map<String, String> usuarios; // Armazena usuários e suas respectivas senhas
    private Map<String, List<Personagem>> personagensPorUsuario; // Armazena personagens associados a cada usuário
    private String usuarioLogado; // Armazena o nome do usuário logado
    private DatabaseService db; // Serviço de banco de dados

    public Login() {
        db = new DatabaseService();
        usuarios = db.listarUsuariosComSenha(); // Carrega os usuários e senhas do banco
        personagensPorUsuario = new HashMap<>(); // Inicializa o mapa de personagens por usuário

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado no banco de dados.");
        } else {
            System.out.println("Usuários carregados: " + usuarios.keySet());
        }
    }

    public boolean entrar() {
        System.out.print("\nDigite o seu usuário: ");
        String username = scanner.next();

        if (!usuarios.containsKey(username)) {
            System.out.println("\nUsuário não encontrado.\n");
            System.out.println("1. Tentar novamente");
            System.out.println("2. Cadastrar novo usuário\n");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    return entrar();
                case 2:
                    cadastrarUsuario();
                    return entrar();
                default:
                    System.out.println("Opção inválida.");
                    return false;
            }
        } else {
            System.out.print("Digite a sua senha: ");
            String password = scanner.next();

            if (usuarios.get(username).equals(password)) {
                System.out.println("Login bem-sucedido! Bem-vindo, " + username + ".");
                usuarioLogado = username; // Armazena o nome do usuário logado
                return true;
            } else {
                System.out.println("Senha incorreta.");
                return false;
            }
        }
    }

    public void cadastrarUsuario() {
        System.out.print("Digite o nome do novo usuário: ");
        String novoUsuario = scanner.next();

        if (usuarios.containsKey(novoUsuario)) {
            System.out.println("Usuário já cadastrado.");
        } else {
            System.out.print("Digite a senha: ");
            String novaSenha = scanner.next();

            db.inserirUsuario(novoUsuario, novaSenha); // Salva no banco de dados
            usuarios.put(novoUsuario, novaSenha); // Atualiza o mapa local
            personagensPorUsuario.put(novoUsuario, new ArrayList<>()); // Inicializa a lista de personagens do usuário
            System.out.println("Usuário cadastrado com sucesso.");
        }
    }

    public void cadastrarPersonagem(String usuario) {
        if (!usuarios.containsKey(usuario)) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        List<Personagem> personagens = personagensPorUsuario.computeIfAbsent(usuario, k -> new ArrayList<>());

        if (personagens.size() >= 5) {
            System.out.println("Você já cadastrou o número máximo de 5 personagens.");
            return;
        }

        System.out.print("Digite o nome do personagem: ");
        String nome = scanner.next();

        System.out.println("Escolha a classe do personagem:");
        System.out.println("1. Guerreiro");
        System.out.println("2. Ladino");
        System.out.println("3. Curandeiro");
        System.out.println("4. Mago");
        System.out.println("5. Bardo");
        System.out.println("6. Druida");
        System.out.print("Digite o número da classe: ");
        int classeEscolhida = scanner.nextInt();

        String classe = switch (classeEscolhida) {
            case 1 -> "Guerreiro";
            case 2 -> "Ladino";
            case 3 -> "Curandeiro";
            case 4 -> "Mago";
            case 5 -> "Bardo";
            case 6 -> "Druida";
            default -> {
                System.out.println("Classe inválida.");
                yield null; // Aqui você "finaliza" o switch com um valor nulo
            }
        };

        if (classe == null) {
            return; // Se a classe for inválida, retorna do método
        };


        Personagem personagem = new Personagem(nome, classe);
        personagens.add(personagem);

        System.out.println("Personagem cadastrado com sucesso.");
        System.out.println("Atributos do personagem:");
        System.out.println("Nome: " + personagem.getNome());
        System.out.println("Classe: " + personagem.getClasse());
        System.out.println("Vida: " + personagem.getVida() + " (10 vida base + d10)");
        System.out.println("Força: " + personagem.getForca() + " (d6)");
        System.out.println("Destreza: " + personagem.getDestreza() + " (d6)");
        System.out.println("Inteligência: " + personagem.getInteligencia() + " (d6)");

        db.inserirPersonagem(nome, classeEscolhida, personagem.getVida()); // Salva no banco de dados
    }

    public void exibirPersonagens(String usuario) {
        if (!usuarios.containsKey(usuario)) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        List<Personagem> personagens = personagensPorUsuario.getOrDefault(usuario, new ArrayList<>());

        if (personagens.isEmpty()) {
            System.out.println("Nenhum personagem cadastrado para este usuário.");
        } else {
            System.out.println("\nPersonagens cadastrados para " + usuario + ":");
            for (Personagem personagem : personagens) {
                System.out.println("\nNome: " + personagem.getNome());
                System.out.println("Classe: " + personagem.getClasse());
                System.out.println("Vida: " + personagem.getVida());
                System.out.println("Força: " + personagem.getForca());
                System.out.println("Destreza: " + personagem.getDestreza());
                System.out.println("Inteligência: " + personagem.getInteligencia());
                System.out.println();
            }
        }
    }


    public void excluirPersonagem(String usuario) {
        if (!usuarios.containsKey(usuario)) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        List<Personagem> personagens = personagensPorUsuario.get(usuario);

        if (personagens == null || personagens.isEmpty()) {
            System.out.println("Nenhum personagem cadastrado para este usuário.");
            return;
        }

        System.out.println("Digite o nome do personagem a ser excluído: ");
        String nomePersonagem = scanner.next();

        // Procurar o personagem pelo nome
        Personagem personagemExcluido = null;
        for (Personagem personagem : personagens) {
            if (personagem.getNome().equalsIgnoreCase(nomePersonagem)) {
                personagemExcluido = personagem;
                break;
            }
        }

        if (personagemExcluido != null) {
            // Excluir no banco de dados
            boolean sucesso = db.excluirPersonagem(nomePersonagem);

            if (sucesso) {
                // Remover da lista local
                personagens.remove(personagemExcluido);
                System.out.println("Personagem excluído com sucesso.");
            } else {
                System.out.println("Erro ao excluir o personagem no banco de dados.");
            }
        } else {
            System.out.println("Personagem não encontrado.");
        }
    }

    public void excluirUsuario() {
        System.out.print("Digite o nome do usuário a ser excluído: ");
        String usuario = scanner.next();

        if (usuarios.containsKey(usuario)) {
            usuarios.remove(usuario);
            personagensPorUsuario.remove(usuario);
            db.excluirUsuario(usuario); // Adicione o método correspondente no DatabaseService
            System.out.println("Usuário excluído com sucesso.");
        }

        else {
            System.out.println("Usuário não encontrado.");
        }

    }

    public String getUsuarioLogado() {
        return usuarioLogado;
    }
}
