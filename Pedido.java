import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; //Formatar a data para o padrão ex: dd/mm/yyyy
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int idPedido, quantProduto;
    private String local, status;
    private LocalDateTime data;
    private Cliente cliente;
    private List<Produto> produtos;
    private Entrega entrega;

    public Pedido(int idPedido, String local, Cliente cliente, String status) {
        this.idPedido = idPedido;
        this.local = local;
        this.data = LocalDateTime.now();
        this.status = status;
        this.cliente = cliente;
        this.produtos = new ArrayList<>();
        this.quantProduto = 0;
    }

    public void adicionarProduto(Produto produto) {
        if (produto.isDisponivel()) {
            produtos.add(produto);
            quantProduto++;
        }
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Produto p : produtos) {
            total += p.getPreco();
        }
        return total;
    }

    public String getDataFormatada() {
        DateTimeFormatter formatar = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return data.format(formatar);
    }

    public int getIdPedido(){ 
        return idPedido;
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
    public List<Produto> getProdutos(){ 
        return produtos;
    }
    public int getQuantProduto(){
        return quantProduto; 
    }
    public Entrega getEntrega(){
        return entrega;
    }

    public void setEntrega(Entrega entrega){
        this.entrega = entrega;
    }

    public void atualizarStatus() {
        switch (status) {
            case "PREPARANDO": 
                status = "A CAMINHO"; 
                break;
            case "A CAMINHO": 
                status = "ENTREGUE"; 
                break;
        }
    }
}
