/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banco.Model;

import java.sql.PreparedStatement;

/**
 *
 * @author Erick Alessi
 */
public class Cliente implements Comparable {

    private String nome;
    private String sobrenome;
    private int RG;
    private String CPF;
    private String endereco;
    private double salario;

    public Cliente(String nome, String sobrenome, int RG, String CPF, String endereco, double salario) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.RG = RG;
        this.CPF = CPF;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Cliente(String CPF) {
        this.CPF = CPF;
    }

    public Cliente() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public int getRG() {
        return RG;
    }

    public void setRG(int RG) {
        this.RG = RG;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return this.nome + " " + this.sobrenome + " " + this.RG + " " + this.CPF + " " + this.endereco + " " + this.salario;
    }

    @Override
    public int compareTo(Object outroCliente) {
        return this.nome.compareTo(((Cliente) outroCliente).getNome());
    }

}
