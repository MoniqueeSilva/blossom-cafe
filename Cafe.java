public class Cafe extends Produto {
    private String grao;
    private double peso;

    public Cafe(int id_produto, String nome, double preco, String grao, double peso){
        super(id_produto, nome, preco);
        this.grao = grao;
        this.peso = peso;
    }

    public String getGrao(){
        return grao;
    }
    public double getPeso(){
        return peso;
    }

    public String getDescricao(){
        return getNome()  + " (" + grao + ", " + peso + "g) - R$" + getPreco();
    }
}
