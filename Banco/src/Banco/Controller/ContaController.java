/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Controller;

import Banco.DAO.ClienteDAO;
import Banco.DAO.ContaDAO;
import Banco.Model.Cliente;
import Banco.Model.Conta;
import Banco.Model.ContaCorrente;
import Banco.Model.ContaInvestimento;
import Banco.View.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.Action;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @auth or Erick Alessi
 */
public class ContaController {

    private final MainFrame view;
    private final ClienteDAO clienteDAO;
    private final ContaDAO contaDAO;
    private String tipoConta;

    public ContaController(MainFrame view, ClienteDAO clienteDAO, ContaDAO contaDAO) {
        this.view = view;
        this.clienteDAO = clienteDAO;
        this.contaDAO = contaDAO;
    }

    public void initController() {
        esconderCampos();
        view.getBuscarCPFButtonNovaConta().addActionListener((ActionEvent evt) -> buscarClienteCpf());
        view.getTipoContaComboBoxeNovaConta().addActionListener((ActionEvent evt) -> esconderCampos());
        view.getCriarContaButton().addActionListener((ActionEvent evt) -> criarConta());
        view.getBuscarContaOCButton().addActionListener((ActionEvent evt) -> buscarClienteConta());
        view.getDepositarOCButton().addActionListener((ActionEvent evt) -> depositarConta());
        view.getSacarOCButton().addActionListener((ActionEvent evt) -> sacarConta());
        view.getRenumeraOCButton().addActionListener((ActionEvent evt) -> remuneraConta());

    }

    public void buscarClienteCpf() {
        try {
            String cpf = view.getCpfFieldNovaConta().getText();
            Cliente cl = new Cliente(cpf);
            clienteDAO.buscar(cl);
            view.getClienteEncontradoNovaConta().setText(cl.toString());
        } catch (Exception ex) {
            view.showError(ex.getMessage());
        }
    }

    public void buscarClienteConta() {
        try {
            int numeroConta = Integer.valueOf(view.getNumeroContaOCField().getText());
            Conta c = new Conta();
            c.setNumero(numeroConta);
            Cliente cl = contaDAO.buscarClienteConta(c);
            view.getClienteOCField().setText(cl.getNome() + " " + cl.getSobrenome());
            double saldo = contaDAO.buscarNumeroConta(cl.getCPF()).getSaldo();
            view.getSaldoOCField().setText(Double.toString(saldo));
        } catch (Exception ex) {
            view.showError(ex.getMessage());
        }
    }

    public void esconderCampos() {
        try {
            tipoConta = (String) view.getTipoContaComboBoxeNovaConta().getSelectedItem();
            if (tipoConta.equals("Conta Investimento")) {
                view.getContaCorrenteJPanel().setVisible(false);
                view.getContaInvestimentoJPanel().setVisible(true);
            } else {
                view.getContaCorrenteJPanel().setVisible(true);
                view.getContaInvestimentoJPanel().setVisible(false);
            }
        } catch (Exception ex) {
            view.showError(ex.getMessage());
        }
    }

    public void criarConta() {
        try {
            String cpf = view.getCpfFieldNovaConta().getText();
            if (cpf.equals("")) {
                view.showError("Cliente não Informado");
                return;
            }
            Cliente cl = new Cliente(cpf);
            clienteDAO.buscar(cl);
            if (tipoConta.equals("Conta Investimento")) {
                Double depositoInicial = Double.valueOf(view.getDepositoInicialCIField().getText());
                Double depositoMinimo = Double.valueOf(view.getDepositoMinimoCIField().getText());
                Double montanteMinimo = Double.valueOf(view.getMontanteMinimoCIField().getText());
                ContaInvestimento ci = new ContaInvestimento(cl, montanteMinimo, depositoMinimo, depositoInicial);
                contaDAO.inserir(ci);
            } else {
                Double depositoInicial = Double.valueOf(view.getDepositoInicialCCField().getText());
                Double limite = Double.valueOf(view.getLimiteCCField().getText());
                ContaCorrente cc = new ContaCorrente(cl, depositoInicial, limite);
                System.out.println(cc);
                contaDAO.inserir(cc);
            }
            view.getNumeroDaContaField().setText(Integer.toString((contaDAO.buscarNumeroConta(cpf)).getNumero()));
        } catch (Exception ex) {
            view.showError(ex.getMessage());
        }

    }

    private void depositarConta() {
        try {
            String valorStr = view.getValorOCField().getText();
            if (valorStr == "") {
                view.showError("Valor Incorreto");
                return;
            }
            double valor = Double.valueOf(valorStr);
            int numeroConta = Integer.valueOf(view.getNumeroContaOCField().getText());
            Conta c = new Conta();
            c.setNumero(numeroConta);
            Conta conta = contaDAO.buscarContaNumero(c);
            if ("ContaCorrente".equals(conta.getClass().getSimpleName())) {
                ContaCorrente corrente = (ContaCorrente) conta;
                corrente.deposita(valor);
                contaDAO.atualiza(conta);
            } else {
                ContaInvestimento investimento = (ContaInvestimento) conta;
                investimento.deposita(valor);
                contaDAO.atualiza(conta);
            }
            buscarClienteConta();
        } catch (Exception ex) {
            view.showError(ex.getMessage());
        }

    }

    private void sacarConta() {
        try {
            String valorStr = view.getValorOCField().getText();
            if (valorStr == "") {
                view.showError("Valor Incorreto");
                return;
            }

            double valor = Double.valueOf(valorStr);
            int numeroConta = Integer.valueOf(view.getNumeroContaOCField().getText());
            Conta c = new Conta();
            c.setNumero(numeroConta);
            Conta conta = contaDAO.buscarContaNumero(c);
            if ("ContaCorrente".equals(conta.getClass().getSimpleName())) {
                ContaCorrente corrente = (ContaCorrente) conta;
                corrente.saca(valor);
                contaDAO.atualiza(conta);
            } else {
                ContaInvestimento investimento = (ContaInvestimento) conta;
                investimento.saca(valor);
                contaDAO.atualiza(conta);
            }
            buscarClienteConta();
        } catch (Exception ex) {
            view.showError(ex.getMessage());
        }

    }

    private void remuneraConta() {
        try {
            int numeroConta = Integer.valueOf(view.getNumeroContaOCField().getText());
            Conta c = new Conta();
            c.setNumero(numeroConta);
            Conta conta = contaDAO.buscarContaNumero(c);
            if ("ContaCorrente".equals(conta.getClass().getSimpleName())) {
                ContaCorrente corrente = (ContaCorrente) conta;
                corrente.remunera();
                contaDAO.atualiza(conta);
            } else {
                ContaInvestimento investimento = (ContaInvestimento) conta;
                investimento.remunera();
                contaDAO.atualiza(conta);
            }
            buscarClienteConta();
        } catch (Exception ex) {
            view.showError(ex.getMessage());
        }
    }

}
