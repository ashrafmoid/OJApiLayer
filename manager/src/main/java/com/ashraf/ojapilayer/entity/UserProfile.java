package com.ashraf.ojapilayer.entity;

import com.ashraf.ojapilayer.models.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_profile")
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
@TypeDef(
        name = "jsonb",
        typeClass = JsonBinaryType.class
)
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

    @ManyToMany(mappedBy = "authors")
    private List<Question> questions = new ArrayList<>();

    @ManyToMany(mappedBy = "authors")
    private List<Editorial> editorials = new ArrayList<>();

    public void setRegisteredUserTable(RegisteredUser registeredUserTable) {
        this.registeredUserTable = registeredUserTable;
        registeredUserTable.setUserProfile(this);
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
        question.getAuthors().add(this);
    }

    public void addEditorial(Editorial editorial) {
        this.editorials.add(editorial);
        editorial.getAuthors().add(this);
    }
}
