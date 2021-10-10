import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Gerenciador {
    protected static ArrayList<Tarefa> tarefas = new ArrayList<>();

    public static void limparTela(){      //o Java não possui método para limpar a tela
        for(int i=0; i < 300; i++){
            printMsg("\r\n", false);
        }
    }

    public static ArrayList<Tarefa> selecionarTarefas(boolean perguntar, int caso){ //filtra a lista de tarefas
        ArrayList<Tarefa> tarefasSelecionadas = new ArrayList<>();
        String msg = "O que você gostaria de visualizar:\n  1- Realizadas.\n  2- Pendentes.\n  3- Ambas.";
        int opcao = perguntar ? lerInt(msg, 1, 3) : caso;

        for(int i = 0; i < tarefas.size(); i++) {
            if ((opcao==1 && tarefas.get(i).getEstado()) || (opcao==2 && !tarefas.get(i).getEstado()) || (opcao==3)){
                tarefasSelecionadas.add(tarefas.get(i));
            }
        }
        return tarefasSelecionadas;
    }

    public static void listarTarefas(ArrayList<Tarefa> listaSelecionada, boolean mostrarEstado){  //imprimir lista
        if (!listaVazia(listaSelecionada)) {
            printMsg("Lista das tarefas:", false);
            if (mostrarEstado){
                for (int i = 0; i < listaSelecionada.size(); i++) {
                    System.out.printf("%s%d%s%s%n", "  ", i, "- ", listaSelecionada.get(i).toString());
                }
            } else {
                for (int i = 0; i < listaSelecionada.size(); i++) {
                    System.out.printf("%s%d%s%s%n", "  ", i, "- ", listaSelecionada.get(i).getNome());
                }
            }
        }
    }

    public static int runMenu(){  //executa o menu
        limparTela();
        printMsg("---------------- MENU ----------------", false);
        printMsg("  1- Adicionar uma tarefa.\n" +
                "  2- Finalizar uma tarefa.\n" +
                "  3- Desfazer uma tarefa.\n" +
                "  4- Visualizar estado das tarefas.\n" +
                "  5- Excluir uma tarefa.\n" +
                "  6- Sair.\n" +
                "--------------------------------------", false);
        return lerInt("Insira qual operação gostaria de realizar: ", 1, 6);
    }

    public static boolean listaVazia(ArrayList<Tarefa> listaTarefas){   //certificar-se de que existem tarefas
        return (listaTarefas.size()==0);
    }

    public static void printMsg(String msg, boolean pausa){  //imprime mensagem com opção de pausa
        Scanner input = new Scanner(System.in);
        System.out.println(msg);
        if(pausa) {
            input.nextLine();
        }
    }

    public static String lerString(String msg){  //le uma string que o usuário informe
        Scanner input = new Scanner(System.in);
        System.out.println(msg);
        return input.nextLine();
    }

    public static int lerInt(String msg, int limiteInf, int limiteSup){  //le inteiro dentro de um intervalo
        Scanner input = new Scanner(System.in);
        boolean valorValido = false;
        int valor = 0;
        do {
            printMsg(msg, false);
            try {
                valor = input.nextInt();
                if (valor>=limiteInf && valor<=limiteSup) {
                    valorValido = true;
                } else {
                    System.out.printf("Por favor insira um valor entre %d e %d\n", limiteInf, limiteSup);
                }
            }catch(InputMismatchException e){
                printMsg("Por favor digite um INTEIRO.", false);
                input.nextLine();
            }
        } while(!valorValido);
        return valor;
    }

    public static void adicionarTarefa(){
        limparTela();
        String novaTarefa =lerString("Insira o nome da nova tarefa: ");

        Tarefa t = new Tarefa(novaTarefa, false); //criando objeto da tarefa desejada
        tarefas.add(t);                                 //e adicionando ao ArrayList de Tarefas
    }

    public static void finalizarTarefa(){
        limparTela();
        ArrayList<Tarefa> pendentes = selecionarTarefas(false, 2);
        if(listaVazia(pendentes))  {
            printMsg("Lista vazia. <ENTER> para continuar.", true);
        } else{
            listarTarefas(pendentes, true);
            int tSelecionada = lerInt("Insira qual tarefa gostaria de finalizar", 0,
                    pendentes.size()-1);
            pendentes.get(tSelecionada).setEstado(true); //transformando a tarefa em realizada (true)
        }
    }

    public static void desfazerTarefa(){
        limparTela();
        ArrayList<Tarefa> realizadas = selecionarTarefas(false, 1);
        if(listaVazia(realizadas)){
            printMsg("Lista vazia. <ENTER> para continuar.", true);
        } else{
            listarTarefas(realizadas, true);
            int tSelecionada = lerInt("Insira qual tarefa gostaria de desfazer.", 0,
                    realizadas.size()-1);
            realizadas.get(tSelecionada).setEstado(false); //transformando a tarefa em pendente (false)
        }
    }

    public static void visualizarTarefas(){
        limparTela();
        if(listaVazia(tarefas)){
            printMsg("Lista vazia. <ENTER> para continuar", true);
            return;
        }

        ArrayList<Tarefa> selecionadas = selecionarTarefas(true, 0);
        if(listaVazia(selecionadas)){
            printMsg("Lista vazia. <ENTER> para continuar.", true);
        } else{
            listarTarefas(selecionadas, true);
            printMsg("<ENTER> para continuar", true);
        }
    }

    public static void excluirTarefa(){
        limparTela();
        if(listaVazia(tarefas)){
            printMsg("Lista vazia. <ENTER> para continuar", true);
        }else{
            listarTarefas(tarefas, true);
            int tExcluida = lerInt("Insira qual tarefa gostaria de excluir", 0,
                    tarefas.size()-1);

            int certeza = lerInt("Certeza que deseja excluir a tarefa "
                    + tarefas.get(tExcluida).getNome() + "?\n  1- Sim\n  2- Não", 1, 2);
            if (certeza == 1) {
                tarefas.remove(tExcluida);
            }
        }
    }

    public static void sair(){
        limparTela();
        printMsg("Até logo!", false);
    }

    public static void main(String[] args) {
        while (true){
            switch (runMenu()) {
                case 1:
                    adicionarTarefa();
                    break;
                case 2:
                    finalizarTarefa();
                    break;
                case 3:
                    desfazerTarefa();
                    break;
                case 4:
                    visualizarTarefas();
                    break;
                case 5:
                    excluirTarefa();
                    break;
                case 6:
                    sair();
                    System.exit(0);
                    break;
            }
        }
    }
}
