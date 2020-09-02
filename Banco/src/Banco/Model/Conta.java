/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Model;

/**
 *
 * @author Erick Alessi
 */
public class Conta implements ContaI {
    private Cliente cliente;
    private int numero;
    private double saldo;

    public Conta() {};
    
    public Conta(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean deposita(double valor) {
        if (valor > 0) {
            setSaldo(getSaldo()+valor);
            return true;
        }
        return false;
    }

    @Override
    public boolean saca(double valor) {
        if (valor > 0) {
            setSaldo(getSaldo()-valor);
            return true;
        }
        return false;
    }

    @Override
    public Cliente getDono() {
        return this.cliente;
    }

    @Override
    public int getNumero() {
        return this.numero;
    }

    @Override
    public double getSaldo() {
        return this.saldo;
    }

    @Override
    public void remunera() {

    }

    ;
    
     public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return this.numero + " " + this.cliente + " " + this.saldo + " ";
    }

}
