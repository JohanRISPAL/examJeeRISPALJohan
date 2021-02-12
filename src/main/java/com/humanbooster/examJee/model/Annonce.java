package com.humanbooster.examJee.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Basic(optional = true)
    private String url;

    @Column
    @Basic(optional = false)
    @NotBlank(message = "Le contenu ne peut être vide")
    @Type(type="text")
    @Length(min = 20, message = "Le contenu doit faire plus de 20 caractères")
    private String contenu;

    @Column(name = "date_de_publication")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Basic(optional = false)
    private Date publicationDate;

    public Annonce() {

    }

    public Annonce(String url, @Length(min = 20) String contenu, Date publicationDate) {
        this.url = url;
        this.contenu = contenu;
        this.publicationDate = publicationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}
