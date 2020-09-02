/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Utils;

import Banco.Model.Cliente;
import java.util.Comparator;

/**
 *
 * @author Erick Alessi
 */
public class ClienteComparadorSobreNome implements Comparator {
        
    @Override
    public int compare(Object o1, Object o2) {
        Cliente c1 = (Cliente) o1;
        Cliente c2 = (Cliente) o2;
        return c1.getSobrenome().compareTo(c2.getSobrenome());
    }
    
    
}
