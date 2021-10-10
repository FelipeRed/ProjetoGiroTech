public class Tarefa {
    private String nome;
    private boolean estado;

    public Tarefa(String nome, boolean estado){
        this.nome = nome;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return (getEstado() ? "Realizada" : "Pendente ") + " | " + nome;
    }

    public void setEstado(boolean estado){
        this.estado = estado;
    }

    public String getNome(){
        return nome;
    }
    public boolean getEstado(){
        return estado;
    }
}
