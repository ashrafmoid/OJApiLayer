package com.ashraf.ojapilayer.entity;

import com.ashraf.ojapilayer.enums.ProgrammingLanguage;
import com.ashraf.ojapilayer.enums.SubmissionStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Duration;

@Entity
@Table(name = "submission")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class Submission extends BaseEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @JsonBackReference
    @ToString.Exclude
    private Question question;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference(value = "author")
    private UserProfile author;

    @Column
    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private ProgrammingLanguage language;

    @Column
    private Integer executionTime;

    @Column
    private Integer memory;

    private String documentLink;
}
