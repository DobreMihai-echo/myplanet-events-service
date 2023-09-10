package com.event.event.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "event_username")
    private String username;

    @Column(name = "event_type")
    private String type;

    @Column(name = "event_latitude")
    private Double latitude;

    @Column(name = "event_longitude")
    private Double longitude;

    @Column(name = "event_date")
    private LocalDateTime date;

    @Column(name = "completed")
    private Boolean completed;

    @Column(name = "event_description")
    private String description;

    @Column(name = "comment_count")
    private Long commentCount;

    @Column(name="event_joiners")
    @ElementCollection
    private List<String> eventJoiners;
}
