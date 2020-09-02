/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Controller;

import Banco.DAO.ClienteDAO;
import Banco.DAO.ContaDAO;
import Banco.Model.Cliente;
import Banco.Model.ClienteTableModel;
import Banco.Utils.ClienteComparadorSalario;
import Banco.Utils.ClienteComparadorSobreNome;
import Banco.View.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Erick Alessi
 */
public class ClienteController {

    private final ClienteTableModel clienteTableModel;
    private final MainFrame view;
    private int linhaClicada = -1;
    private final ClienteDAO clienteDAO;
    private final ContaDAO contaDAO;

    public ClienteController(ClienteTableModel clienteTableModel, MainFrame view, ClienteDAO clienteDAO, ContaDAO contaDAO) {
        this.clienteTableModel = clienteTableModel;
        this.view = view;
        this.clienteDAO = clienteDAO;
        this.contaDAO = contaDAO;
    }

    public void initController() {
        //Registra o modelo de tabela na tabela
        view.getClienteViewTable().setModel(clienteTableModel);
        //Registra os eventos
        view.getClienteListarButton().addActionListener((ActionEvent evt) -> listarTodos());
        view.getClienteOrdenarPorCombo().addActionListener((ActionEvent evt) -> listarTodos());
        view.getPesquisarClienteField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                listarTodos();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                listarTodos();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                listarTodos();
            }
        });
 
        view.getClienteNovoButton().addActionListener((ActionEvent evt) -> adicionarCliente());
        view.getClienteDeletarButton().addActionListener((ActionEvent evt) -> excluirClientes());
        view.getClienteAtualizarButton().addActionListener((ActionEvent evt) -> atualizarCliente());
        view.getLimpaClienteButton().addActionListener((ActionEvent evt) -> limpaCliente());
        view.getClienteViewTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                marcaContatosSelecionados(evt);
            }
        });

        initView();
    }

    public void initView() {
        java.awt.EventQueue.invokeLater(() -> {
            view.setVisible(true);
        });
    }

    private void listarTodos() {
        try{
        List<Cliente> clientes = clienteDAO.listarTodos();
        String filtro = view.getPesquisarClienteField().getText().toUpperCase();
        String ordenacao = (String) view.getClienteOrdenarPorCombo().getSelectedItem();
        if (!filtro.equalsIgnoreCase("")) {
            List<Cliente> clientesAux = new ArrayList<>();
            for (Cliente c : clientes) {
                if (!c.getNome().toUpperCase().contains(filtro) && !c.getSobrenome().toUpperCase().contains(filtro) && !c.getCPF().toUpperCase().contains(filtro) && !(Integer.toString(c.getRG())).contains(filtro)) {
                    clientesAux.add(c);
                }
            }
            clientes.removeAll(clientesAux);
        }
        if (ordenacao.equals("Nome")) {
            clienteTableModel.setListaClientes(clientes);
        } else if (ordenacao.equals("SobreNome")) {
            Collections.sort(clientes, new ClienteComparadorSobreNome());
            clienteTableModel.setListaClientes(clientes);
        } else if (ordenacao.equals("Salário")) {
            Collections.sort(clientes, new ClienteComparadorSalario());
            clienteTableModel.setListaClientes(clientes);
        } } catch (Exception ex) {
            view.showError(ex.getMessage());
        }

    }

    private void adicionarCliente() {
        try{
        clienteDAO.inserir(view.getClienteFromFields());
        this.listarTodos();
        } catch (Exception ex) {
            view.showError(ex.getMessage());
        }
    }

    private void excluirClientes() {
        try {
            if(view.confirmDialog("Deletando um cliente, todas as contas vinculadas serão deletadas também. Desaja continuar") == 0){;
            int[] linhasSelecionadas = view.getClienteViewTable().getSelectedRows();
            List<Cliente> listaExcluir = new ArrayList();
            for (int i = 0; i < linhasSelecionadas.length; i++) {
                Cliente cliente = clienteTableModel.getCliente(linhasSelecionadas[i]);
                contaDAO.deletarConta(cliente);
                clienteDAO.delete(cliente);
                listaExcluir.add(cliente);
            }
            listaExcluir.forEach((cliente) -> {
                clienteTableModel.removeCliente(cliente);
            });
            }

        } catch (Exception ex) {
            view.showError("Erro ao excluir contato. " + ex);
        }
    }

    private void atualizarCliente() {
        try {
            if (linhaClicada != -1) {
                Cliente cliente = view.getClienteFromFields();
                clienteDAO.update(cliente);
                //Atualiza tabela
                listarTodos();
               }
        } catch (Exception ex) {
            view.showError("Erro ao atualizar contato. " + ex);
        }

    }
    
      private void marcaContatosSelecionados(MouseEvent evt) {
       //Pega a linha clicada
        linhaClicada = view.getSelectedLine(evt);
        //Pega o contato da linha clicada
        Cliente cliente = clienteTableModel.getCliente(linhaClicada);
        //Seta os dados nos componentes
        view.setCliente(cliente);
    }
      
      private void limpaCliente(){
          view.setCliente(new Cliente());
      }


}
