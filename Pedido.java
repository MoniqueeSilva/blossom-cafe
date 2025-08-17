import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {
    private int id_pedido, quantProduto;
    private  String local, status;
    private LocalDateTime data;

    public Pedido(int id_pedido, int quantProduto, String local, String status){
        this.id_pedido = id_pedido;
        this.quantProduto = quantProduto;
        this.local = local;
        this.status = "PREPARANDO";
        this.data = LocalDateTime.now();
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
    public LocalDateTime getData(){
        return data;
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
    public void setStatus(String Status){
        this.status = status;
    }

    public void atualizarStatus(){
        switch (this.status) {
            case "PREPARANDO":
                this.status = "A_CAMINHO";
                break;
            case "A_CAMINHO":
                this.status = "ENTREGUE";
            default:
                throw new IllegalStateException("Não foi possível avançar informações sobre o status atual: " + this.status);
        }
    }

    //Método cancelar pedido
    //Método de comparação, para saber se os pedidos são iguais
    //Método valor total dos pedidos 
}
