/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.DAO;

import Banco.Model.Cliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erick Alessi
 */
public class ClienteDAO {

    private static final Connection c = ConnectionFactory.getConnection();

    public void inserir(Cliente cl) throws Exception {
        try {
            PreparedStatement st = c.prepareStatement("insert into banco.cliente(nome,sobrenome,rg,cpf,endereco,salario) values(?,?,?,?,?,?)");
            st.setString(1, cl.getNome());
            st.setString(2, cl.getSobrenome());
            st.setInt(3, cl.getRG());
            st.setString(4, cl.getCPF());
            st.setString(5, cl.getEndereco());
            st.setDouble(6, cl.getSalario());
            st.execute();
        } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
    }

    public void buscar(Cliente cl) throws Exception {
        try {
            PreparedStatement st = c.prepareStatement("select nome,sobrenome,rg,cpf,endereco,salario from banco.cliente where (cpf=?)");
            st.setString(1, cl.getCPF());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cl.setNome(rs.getString("nome"));
                cl.setSobrenome(rs.getString("sobrenome"));
                cl.setRG(rs.getInt("rg"));
                cl.setCPF(rs.getString("cpf"));
                cl.setEndereco(rs.getString("endereco"));
                cl.setSalario(rs.getDouble("salario"));
            }
            System.out.println(cl);
         } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
    }

    public List<Cliente> listarTodos() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("select nome,sobrenome,rg,cpf,endereco,salario from banco.cliente");
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Cliente cl = new Cliente();
                cl.setNome(rs.getString("nome"));
                cl.setSobrenome(rs.getString("sobrenome"));
                cl.setRG(rs.getInt("rg"));
                cl.setCPF(rs.getString("cpf"));
                cl.setEndereco(rs.getString("endereco"));
                cl.setSalario(rs.getDouble("salario"));
                clientes.add(cl);
            }
            System.out.println(clientes);
        } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
        return clientes;
    }

    public void delete(Cliente cliente) throws Exception {
        try {
            PreparedStatement st = c.prepareStatement("delete from banco.cliente where cpf = ?");
            st.setString(1, cliente.getCPF());
            st.execute();
            System.out.println("Deletou cliente " + cliente);
         } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
    }

    public void update(Cliente cl) throws Exception {
        try {
            PreparedStatement st = c.prepareStatement("update banco.cliente set nome=?, sobrenome=?, rg=?, cpf=?, endereco=?,salario=? WHERE cpf=?");
            st.setString(1, cl.getNome());
            st.setString(2, cl.getSobrenome());
            st.setInt(3, cl.getRG());
            st.setString(4, cl.getCPF());
            st.setString(5, cl.getEndereco());
            st.setDouble(6, cl.getSalario());
            st.setString(7, cl.getCPF().trim());
            st.executeUpdate();
         } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
    }

}
