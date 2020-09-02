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
public class ContaInvestimento extends Conta {

    private double depositoMinimo;
    private double montanteMinimo;

    public ContaInvestimento() {
    }

    ;
    public ContaInvestimento(Cliente cliente, double montanteMinimo, double depositoMinimo, double depositoInicial) {
        super(cliente);
        this.montanteMinimo = montanteMinimo;
        this.depositoMinimo = depositoMinimo;
        this.deposita(depositoInicial);
    }

    @Override
    public boolean deposita(double valor) {
        if (valor >= depositoMinimo) {
            return super.deposita(valor);
        }
        return false;
    }

    @Override
    public boolean saca(double valor) {
        if (getSaldo() - valor > montanteMinimo) {
            return super.saca(valor);
        }
        return false;
    }

    @Override
    public void remunera() {
        setSaldo(getSaldo() * 1.02);
    }

    public double getDepositoMinimo() {
        return depositoMinimo;
    }

    public void setDepositoMinimo(double depositoMinimo) {
        this.depositoMinimo = depositoMinimo;
    }

    public double getMontanteMinimo() {
        return montanteMinimo;
    }

    public void setMontanteMinimo(double montanteMinimo) {
        this.montanteMinimo = montanteMinimo;
    }
}
