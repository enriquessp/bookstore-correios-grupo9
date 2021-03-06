package br.unicamp.bookstore.dominio;

/**
 * Created by enrique1 on 10/4/16.
 */
public class Produto {

    private int peso;
    private int largura;
    private int altura;
    private int comprimento;
    private String codigoRastreio;

    public Produto() {}

    public Produto(int peso, int largura, int altura, int comprimento) {
        this.peso = peso;
        this.largura = largura;
        this.altura = altura;
        this.comprimento = comprimento;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getComprimento() {
        return comprimento;
    }

    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }

    public boolean temDimensaoNegativa() {
        return peso < 0 || largura < 0 || comprimento < 0 || altura < 0;
    }

    public String getCodigoRastreio() {
        return codigoRastreio;
    }

    public void setCodigoRastreio(String codigoRastreio) {
        this.codigoRastreio = codigoRastreio;
    }
}