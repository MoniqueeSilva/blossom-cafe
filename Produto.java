public abstract class Produto {
   private int id_produto;
   private String nome;
   private double preco;

   public Produto(int id_produto, String nome, double preco){
    this.id_produto = id_produto;
    this.nome = nome;
    this.preco = preco;
   }

   public int getId_produto(){
    return id_produto;
   }
   public String getNome(){
    return nome;
   }
   public double getPreco(){
    return preco;
   }

   public void setNome(String nome){
    this.nome = nome;
   }
   public void setPreco(double preco){
    this.preco = preco;
   }

   public String getDescricao(){
    return nome + " : R$" + preco;
   }
}
