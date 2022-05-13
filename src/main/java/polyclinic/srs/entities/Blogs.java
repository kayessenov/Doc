package polyclinic.srs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "blogs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blogs extends BaseEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    private Users users;

    @Column(name = "Title", length = 512)
    private String title;

    @Column(name = "short_title")
    private String short_title;

    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @Column(name = "image")
    private String image;

}
