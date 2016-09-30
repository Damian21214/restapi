package pl.langer.edu.restapi.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by DLanger on 2016-09-30.
 */
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", nullable = false, columnDefinition = "datetime")
    private LocalDateTime created;

    @Column(name = "modified_date", nullable = true, columnDefinition = "datetime")
    private LocalDateTime modified;

    public Long getId(){ return this.id;}
    public void setId(final Long id) {this.id=id;}

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    @Transient
    public boolean isNew() {return this.getId() == null;}

    @PrePersist
    void prePersist() {
        final LocalDateTime localDateTime = LocalDateTime.now();
        this.created = localDateTime;
        this.modified = localDateTime;
    }

    @PreUpdate
    void preUpdate() {
        this.modified = LocalDateTime.now();
    }
}
