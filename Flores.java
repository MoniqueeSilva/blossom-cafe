public class Flores extends Produto {
    private String cor;
    private String tipo;

    public Flores(int idProduto, String nome, double preco, boolean disponivel, String cor, String tipo) {
        super(idProduto, nome, preco, disponivel);
        this.cor = cor;
        this.tipo = tipo;
    }

    public String getDescricao() {
        return getNome() + " - " + tipo + " (" + cor + ")";
    }
}
