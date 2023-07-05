package dev.patrick.batterycatalog.model;

public class ToolModel {
    private int id;
    private String nome;
    private String tamanho;
    private String image;

    public ToolModel() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ToolModel) {
            ToolModel c = (ToolModel) o;
            if (c.getId() == this.getId()) {
                return true;
            }
        }
        return false;
    }
}
