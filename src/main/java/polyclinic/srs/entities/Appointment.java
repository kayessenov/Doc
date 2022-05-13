package polyclinic.srs.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.EAGER)
    private Departments departments;

    @ManyToOne(fetch = FetchType.EAGER)
    private DoctorEntity doctorEntity;

    @Column(name = "pickdate")
    private java.util.Date pickdate;

    @Column(name = "pickdate2")
    private Date pickdate2;

}
