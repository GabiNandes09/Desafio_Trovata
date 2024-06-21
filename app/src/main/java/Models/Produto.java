package Models;

import android.media.Image;

public class Produto {
    private Integer empresa;
    private Integer produto;
    private String descricao;
    private Integer apelido;
    private Integer grupo;
    private Integer subgrupo;
    private String situacao;
    private  Double peso;
    private Integer classificacao;
    private Integer codigoDeBarras;
    private Integer colecao;
    private Image img;

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = empresa;
    }

    public Integer getProduto() {
        return produto;
    }

    public void setProduto(Integer produto) {
        this.produto = produto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getApelido() {
        return apelido;
    }

    public void setApelido(Integer apelido) {
        this.apelido = apelido;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

    public Integer getSubgrupo() {
        return subgrupo;
    }

    public void setSubgrupo(Integer subgrupo) {
        this.subgrupo = subgrupo;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Integer getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(Integer classificacao) {
        this.classificacao = classificacao;
    }

    public Integer getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(Integer codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public Integer getColecao() {
        return colecao;
    }

    public void setColecao(Integer colecao) {
        this.colecao = colecao;
    }

    public Produto() {
    }

    public Produto(Integer empresa, Integer produto,
                   String descricao, Integer apelido,
                   Integer grupo, Integer subgrupo,
                   String situacao, Double peso,
                   Integer classificacao, Integer codigoDeBarras,
                   Integer colecao, Image img) {
        this.empresa = empresa;
        this.produto = produto;
        this.descricao = descricao;
        this.apelido = apelido;
        this.grupo = grupo;
        this.subgrupo = subgrupo;
        this.situacao = situacao;
        this.peso = peso;
        this.classificacao = classificacao;
        this.codigoDeBarras = codigoDeBarras;
        this.colecao = colecao;
        this.img = img;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
