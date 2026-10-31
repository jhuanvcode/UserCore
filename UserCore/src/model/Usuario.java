package model;

import java.util.Objects;

/**
 * Modelo que representa a entidade Usuario no sistema.
 */
public class Usuario {
    
    private Integer id;
    private String nome;
    private String email;

    public Usuario() {
    }

    public Usuario(String nome, String email) {
        this.nome = nome != null ? nome.trim() : "";
        this.email = email != null ? email.trim() : "";
    }

    public Usuario(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome != null ? nome.trim() : "";
        this.email = email != null ? email.trim() : "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome != null ? nome.trim() : "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.trim() : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
               Objects.equals(nome, usuario.nome) &&
               Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
