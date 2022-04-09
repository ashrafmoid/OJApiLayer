package com.ashraf.ojapilayer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "editorial")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Editorial extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @ManyToMany
    @JoinTable(
            name = "editorial_author_relationship",
            joinColumns = @JoinColumn(name = "editorial_id"),
            inverseJoinColumns = @JoinColumn(name = "user_profile_id"))
    private List<UserProfile> authors = new ArrayList<>();

    @OneToMany(mappedBy = "editorial")
    private List<Question> questions = new ArrayList<>();

    @Column
    private String documentLink;

    public void addQuestion(Question question) {
        this.questions.add(question);
        question.setEditorial(this);
    }
}
