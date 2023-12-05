package ru.tai._10_work.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @CreationTimestamp
    private Instant createAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "news", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

}
