import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id_pedido, quantProduto;
    private double precoUnidade;
    private  String local, status;
    private LocalDateTime data;
    private Cliente cliente;
    private List<Produto> produtos;

    public Pedido(int id_pedido, int quantProduto,double precoUnidade, String local, String status, Cliente cliente){
        this.id_pedido = id_pedido;
        this.quantProduto = quantProduto;
        this.precoUnidade = precoUnidade;
        this.local = local;
        this.status = status;
        this.cliente = cliente;
        this.data = LocalDateTime.now();
        this.produtos = new ArrayList<>();
    }

    public int getId_pedido(){
        return id_pedido;
    }
    public int getQuantProduto(){
        return quantProduto;
    }
    public String getLocal(){
        return local;
    }
    public String getStatus(){
        return status;
    }
    public Cliente getCliente(){
        return cliente;
    }
    public LocalDateTime getData(){
        return data;
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }
    
    public String getDataFormatter(){
        DateTimeFormatter formatar = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return data.format(formatar);
    }

    public void setId_pedido(int id_pedido){
        this.id_pedido = id_pedido;
    }
    public void setQuantProduto(int quantProduto){
        this.quantProduto = quantProduto;
    }
    public void setLocal(String local){
        this.local = local;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public void atualizarStatus(){
        switch (this.status) {
            case "PREPARANDO":
                this.status = "A CAMINHO";
                break;
            case "A CAMINHO":
                this.status = "ENTREGUE";
                break;
            default:
                throw new IllegalStateException("Não foi possível avançar informações sobre o status atual: " + this.status);
        }
    }

    public void cancelarPedido(){
        this.status = "CANCELADO";
    }

    public double calcularValorTotal(){
        return quantProduto * precoUnidade;
    }

}
