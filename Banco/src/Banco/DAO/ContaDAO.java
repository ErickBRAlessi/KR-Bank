/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.DAO;

import Banco.Model.Cliente;
import Banco.Model.Conta;
import Banco.Model.ContaCorrente;
import Banco.Model.ContaInvestimento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Erick Alessi
 */
public class ContaDAO {

    private static final Connection c = ConnectionFactory.getConnection();

    String stInserirCC = "insert into banco.conta(cpf,saldo,limite,tipo) values (?,?,?,?);";
    String stInserirCI = "insert into banco.conta(cpf,saldo,montanteMinimo,depositoMinimo,tipo) values (?,?,?,?,?);";
    String stBuscarContaCPF = "select id, saldo, limite, montanteMinimo, depositoMinimo from banco.conta where cpf = ?;";
    String stUpdateContaCC = "update banco.conta set saldo=? WHERE id=?;";
    String stBuscarClienteConta = "select cpf, nome, sobrenome from banco.cliente cl where cl.cpf = (select cpf from banco.conta where id = ?);";
    String stBuscarContaNumero = "select id, saldo, limite, montanteMinimo, depositoMinimo, tipo from banco.conta where id = ?;";

    public void inserir(ContaCorrente conta) throws Exception {
        try {
            PreparedStatement st = c.prepareStatement(stInserirCC);
            st.setString(1, conta.getDono().getCPF());
            st.setDouble(2, conta.getSaldo());
            st.setDouble(3, conta.getLimite());
            st.setString(4, "CC");
            st.execute();
        } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
    }

    public void inserir(ContaInvestimento conta) throws Exception {
        try {
            PreparedStatement st = c.prepareStatement(stInserirCI);
            st.setString(1, conta.getDono().getCPF());
            st.setDouble(2, conta.getSaldo());
            st.setDouble(3, conta.getMontanteMinimo());
            st.setDouble(4, conta.getDepositoMinimo());
            st.setString(5, "CI");
            st.execute();
        } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
    }

    public void atualiza(Conta conta) throws Exception {
        try {
            PreparedStatement st = c.prepareStatement(stUpdateContaCC);
            st.setDouble(1, conta.getSaldo());
            st.setInt(2, conta.getNumero());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
    }

    public Conta buscarNumeroConta(String cpf) throws Exception {
        Conta conta = new Conta();
        try {
            PreparedStatement st = c.prepareStatement(stBuscarContaCPF);
            st.setString(1, cpf);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                conta.setNumero(rs.getInt(1));
                conta.setSaldo(rs.getDouble(2));
            }
        } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
        return conta;
    }

    public Conta buscarContaNumero(Conta conta) throws Exception {
        try {
            PreparedStatement st = c.prepareStatement(stBuscarContaNumero);
            st.setInt(1, conta.getNumero());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                if (rs.getString(6).equals("CI")) {
                    ContaInvestimento contaInv = new ContaInvestimento();
                    contaInv.setNumero(rs.getInt(1));
                    contaInv.setSaldo(rs.getDouble(2));
                    contaInv.setDepositoMinimo(rs.getDouble(4));
                    contaInv.setMontanteMinimo(rs.getDouble(3));
                    return contaInv;
                } else {
                    ContaCorrente contaCor = new ContaCorrente();
                    contaCor.setNumero(rs.getInt(1));
                    contaCor.setSaldo(rs.getDouble(2));
                    contaCor.setLimite(rs.getDouble(3));
                    return contaCor;
                }
            }
        } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
        return conta;
    }

    public void deletarConta(Cliente cl) throws Exception {
        try {
            Conta conta = buscarNumeroConta(cl.getCPF());
            int numConta = conta.getNumero();
            PreparedStatement st = c.prepareStatement("delete from banco.conta where id = ?;");
            st.setInt(1, numConta);
            st.execute();
        } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
    }

    public Cliente buscarClienteConta(Conta conta) throws Exception {
        Cliente cliente = new Cliente();
        try {
            PreparedStatement st = c.prepareStatement(stBuscarClienteConta);
            st.setInt(1, conta.getNumero());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                cliente.setCPF(rs.getString(1));
                cliente.setNome(rs.getString(2));
                cliente.setSobrenome(rs.getString(3));
            }
            return cliente;
        } catch (SQLException ex) {
            throw new Exception("Algo deu errado :( " + ex.getMessage());
        }
    }

}
