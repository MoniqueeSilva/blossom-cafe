public class Principal {
    public static void main(String[] args){
        Cliente c1 = new Cliente("R.tralala", "40028922", "João", "joao@gmail.com", "12345678954");

        Produto cafe = new Cafe(1, "Café Premium", 25.0, "Arábica", 500);
        Produto flor = new Flores(2, "Buquê", 50.0, "Vermelha", "Rosas");

        Pedido p1 = new Pedido(1, 2, 2.50, "Casa", "liluli", c1);
        Pedido p2 = new Pedido(0, 0, 0, null, null, c1);

        // adiciona produtos aos pedidos
        p1.adicionarProduto(cafe);
        p2.adicionarProduto(flor);

        // adiciona pedidos ao cliente
        c1.adicionarPedido(p1);
        c1.adicionarPedido(p2);

        System.out.println("Histórico de pedidos da " + c1.getNome() + ":");


        // está meio errado ainda :(
      
    }
}
