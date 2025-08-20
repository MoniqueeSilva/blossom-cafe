public class Flores extends Produto {
    private String cor;
    private String tipo;

    public Flores(int id_produto, String nome, double preco, String cor, String tipo){
        super(id_produto, nome, preco);
            this.cor = cor;
            this.tipo = tipo;
    }

    public String getCor(){
        return cor;
    }
    public String getTipo(){
        return tipo;
    }

    public String getDescricao(){
        return getNome()  + " (" + cor + ", " + tipo + ") - R$" + getPreco();
    }
}

