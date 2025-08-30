package com.blossomcafe.dao;

import com.blossomcafe.model.Cafe;
import com.blossomcafe.util.ConexaoBD;
import java.sql.*;

public class CafeDAO {
    public void inserir(Cafe cafe) {
        String sqlProduto = "INSERT INTO produto (nome, preco, disponivel) VALUES (?, ?, ?) RETURNING id_produto";
        String sqlCafe = "INSERT INTO cafe (id_produto, tipo_cafe) VALUES (?, ?)";
        
        try (Connection conn = ConexaoBD.getConnection()) {
            conn.setAutoCommit(false);
            
            // Inserir na tabela produto
            try (PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto)) {
                stmtProduto.setString(1, cafe.getNome());
                stmtProduto.setDouble(2, cafe.getPreco());
                stmtProduto.setBoolean(3, cafe.isDisponivel());
                
                ResultSet rs = stmtProduto.executeQuery();
                if (rs.next()) {
                    int idProduto = rs.getInt("id_produto");
                    
                    // Inserir na tabela cafe
                    try (PreparedStatement stmtCafe = conn.prepareStatement(sqlCafe)) {
                        stmtCafe.setInt(1, idProduto);
                        stmtCafe.setString(2, cafe.getTipoCafe());
                        stmtCafe.executeUpdate();
                    }
                }
            }
            conn.commit();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}