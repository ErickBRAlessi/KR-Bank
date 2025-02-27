/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Controller;

import Banco.DAO.ClienteDAO;
import Banco.DAO.ContaDAO;
import Banco.Model.ClienteTableModel;
import Banco.View.MainFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Erick Alessi
 */
public class Main extends JApplet {

    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();
        ContaDAO contaDAO = new ContaDAO();
        MainFrame view = new MainFrame();
        ClienteTableModel clienteTableModel = new ClienteTableModel();
        ClienteController clienteController = new ClienteController(clienteTableModel, view, clienteDAO, contaDAO);
        ContaController contaController = new ContaController(view,clienteDAO,contaDAO);
        
        contaController.initController();
        clienteController.initController();

    }

}
