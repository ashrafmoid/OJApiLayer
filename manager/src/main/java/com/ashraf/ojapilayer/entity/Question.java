package com.ashraf.ojapilayer.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@Table(name = "question")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Question extends BaseEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String questionFileLink;

    private String testFileLink;

    private String correctOutputFileLink;

    @Column
    @ManyToMany
    @JoinTable(
            name = "question_author_relationship",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "user_profile_id"))
    @ToString.Exclude
    private List<UserProfile> authors = new ArrayList<>();

    @Type(type = "list-array")
    @Column(name = "topics", columnDefinition = "varchar[]")
    private List<String> topics;

    @Column
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;

    @OneToMany(mappedBy = "question")
    private List<Submission> submissions;
}
