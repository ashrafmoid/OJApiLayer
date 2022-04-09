package com.ashraf.ojapilayer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "registered_user_table")
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RegisteredUser extends BaseEntity {

    @Id
    @Column
    @JsonIgnore
    private String id;

    @Column
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;
}
