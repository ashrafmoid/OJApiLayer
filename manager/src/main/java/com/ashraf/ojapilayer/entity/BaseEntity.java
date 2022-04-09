package com.ashraf.ojapilayer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)
public class BaseEntity {

    @CreationTimestamp
    @JsonIgnore
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    @Version
    @JsonIgnore
    private Integer version;
}
