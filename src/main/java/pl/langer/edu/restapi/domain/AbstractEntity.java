package pl.langer.edu.restapi.domain;

import javax.persistence.*;

/**
 * Created by DLanger on 2016-09-30.
 */
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId(){ return this.id;}
    public void setId(final Long id) {this.id=id;}

    @Transient
    public boolean isNew() {return this.getId() == null;}
}
