
import Banco.DAO.ClienteDAO;
import Banco.DAO.ConnectionFactory;
import Banco.Model.Cliente;
import Banco.Model.Conta;
import Banco.Model.ContaCorrente;
import Banco.Model.ContaInvestimento;
import Banco.Utils.ClienteComparadorSalario;
import Banco.Utils.ClienteComparadorSobreNome;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Erick Alessi
 */
public class TestModel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Cliente cl = new Cliente("Bruno", "Fernandes", 123456678, "1234567890", "Rua Boluda, 102", 1400d);
//    Cliente cl = new Cliente("09529184980");
        Conta cc = new ContaCorrente(cl, 75d, 200d);
        Conta ci = new ContaInvestimento(cl, 100d, 100d, 100d);
        System.out.println(cc);
        System.out.println(ci);
        cc.deposita(100);
        cc.saca(500);
        System.out.println(cc);
        ci.deposita(115);
        System.out.println(ci);
        ci.saca(105);
        ci.remunera();
        System.out.println(ci);
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.deletar(cl);
        clienteDAO.inserir(cl);
        clienteDAO.buscar(cl);
        List<Cliente> clientes = clienteDAO.listarTodos();
        ClienteComparadorSalario comparadorSal = new ClienteComparadorSalario();
        Collections.sort(clientes, comparadorSal);
        System.out.println(clientes);
        ClienteComparadorSobreNome comparadorSobreNome = new ClienteComparadorSobreNome();
        Collections.sort(clientes, comparadorSobreNome);
        System.out.println(clientes);
//        clienteDAO.deletar(cl);
//        clienteDAO.buscar(cl);
    }
}
