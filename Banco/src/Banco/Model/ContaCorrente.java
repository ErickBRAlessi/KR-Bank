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
public class ContaCorrente extends Conta {

    
    public ContaCorrente(Cliente cliente, double depositoInicial, double limite) {
        super(cliente);
        super.deposita(depositoInicial);
        this.limite = limite;
    }

    public ContaCorrente() {
    }
    ;

    private double limite;

    @Override
    public boolean saca(double valor) {
        if (getSaldo() - valor > -limite) {
            return super.saca(valor);
        }
        return false;
    }

    @Override
    public void remunera() {
        setSaldo(getSaldo() * 1.01);
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

}
