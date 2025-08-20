import java.util.ArrayList;
import java.util.List;

public class Cliente{
    private String endereco, telefone, nome, email, CPF;
    private List<Pedido> pedidos; 

    public Cliente(String endereco, String telefone, String nome, String email,String CPF){
        this.endereco = endereco;
        this.telefone = telefone;
        this.nome = nome;
        this.email = email;
        this.CPF = CPF;
        this.pedidos = new ArrayList<>();
    }

    public String getEndereco(){
        return endereco;
    }
    public String getTelefone(){
        return telefone;
    }
    public String getNome(){
        return nome;
    }
    public String getEmail(){
        return email;
    }
    public String getCPF(){
        return CPF;
    }
    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    public void setEndereco(String endereco){
        this.endereco = endereco;
    }
    public void setTelefone(String telefone){
        this.telefone = telefone;
    }
    public void setNome(String nome){
        this.nome = nome;
    }
    public void setEmail(String email){
        this.email =  email;
    }
    public void setCPF(String CPF){
        this.CPF = CPF;
    }

    public void adicionarPedido(Pedido pedido){
        pedidos.add(pedido);
    }

    public String atualizarEndereco(String novoEndereco) {
        try {
            if (novoEndereco == null || novoEndereco.trim().isEmpty()) {
                throw new IllegalArgumentException("Endereço não pode ser vazio!");
            }
            
            this.endereco = novoEndereco.trim();
            return "Endereço atualizado com sucesso!";
            
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        }
    }

    public String atualizarTelefone(String novoTelefone){
        try {
            if(novoTelefone == null || novoTelefone.trim().isEmpty()){
                throw new IllegalArgumentException("Telefone não pode ser vazio!");
            }
            this.telefone = novoTelefone.trim();
            return "Telefone atualizado com sucesso!";

        } catch (IllegalArgumentException e){
            return "Erro: " + e.getMessage();
        }
    }

    public List<Pedido> getHistoricoPedidos(){
        return new ArrayList<>(pedidos); // retorna uma cópia da lista de pedidos do cliente
    }

}