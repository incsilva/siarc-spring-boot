package br.edu.ifrn.jc.siarc.rh.dominio;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Aparelho {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String nome;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String enderecoip;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String enderecomac;

    @NotBlank
    @Column(nullable = false)
    private String local;

    @NotBlank
    @Column(nullable = false)
    private String status;

    private String codigoirligar;
    private String codigoirdesligar;

    Calendar c = Calendar.getInstance();
    Date dataRegistro = c.getTime();

    @Deprecated
    protected Aparelho() {
    }

    public Aparelho(String nome) {
        this.nome = nome;
        this.status = Status.INATIVO.getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEnderecoip() {
        return enderecoip;
    }

    public void setEnderecoip(String enderecoip) {
        this.enderecoip = enderecoip;
    }

    public String getEnderecomac() {
        return enderecomac;
    }

    public void setEnderecomac(String enderecomac) {
        this.enderecomac = enderecomac;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public @NotBlank String getStatus() {
        return status;
    }

    public void setStatus(@NotBlank String status) {
        this.status = status;
    }

    public String getCodigoirligar() {
        return codigoirligar;
    }

    public void setCodigoirligar(String codigoirligar) {
        this.codigoirligar = codigoirligar;
    }

    public String getCodigoirdesligar() {
        return codigoirdesligar;
    }

    public void setCodigoirdesligar(String codigoirdesligar) {
        this.codigoirdesligar = codigoirdesligar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aparelho other = (Aparelho) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Aparelho: [nome=" + nome + "]";
    }
}
