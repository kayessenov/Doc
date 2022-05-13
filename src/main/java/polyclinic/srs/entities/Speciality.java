package polyclinic.srs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "speciality")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Speciality extends BaseEntity{

    @Column(name = "spec_name")
    private String name;

    @Column(name = "short_code")
    private String short_code;

}
