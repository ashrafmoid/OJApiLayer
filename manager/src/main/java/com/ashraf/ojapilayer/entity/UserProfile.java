package com.ashraf.ojapilayer.entity;

import com.ashraf.ojapilayer.models.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_profile")
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserProfile extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column
    private String name;

    @Column
    private String collegeName;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Address address;

    @Column
    private String rating;

    @OneToOne(mappedBy = "userProfile", fetch = FetchType.LAZY)
    private RegisteredUser registeredUserTable;

    public void setRegisteredUserTable(RegisteredUser registeredUserTable) {
        this.registeredUserTable = registeredUserTable;
        registeredUserTable.setUserProfile(this);
    }
}
