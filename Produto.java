public abstract class Produto {
    private int idProduto;
    private String nome;
    private double preco;
    private boolean disponivel;

    public Produto(int idProduto, String nome, double preco, boolean disponivel) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.preco = preco;
        this.disponivel = disponivel;
    }

    public int getId_produto(){
        return idProduto;
    }
    public String getNome(){ 
        return nome; 
    }
    public double getPreco(){
        return preco;
    }
    public boolean isDisponivel(){
        return disponivel; //Quando boolean o get começa com is
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    public void setPreco(double preco){
        this.preco = preco;
    }
    public void setDisponivel(boolean disponivel){
        this.disponivel = disponivel;
    }

    public abstract String getDescricao();
}
