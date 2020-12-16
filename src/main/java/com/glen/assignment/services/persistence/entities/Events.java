package com.glen.assignment.services.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Events {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long _id;
    String websiteTitle;
    String title;
    String venue;
    String date;

    Date createdOn;
    Date updatedOn;

    @PreUpdate
    public void preUpdate() {
        updatedOn = new Date();
    }

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        createdOn = now;
        updatedOn = now;
    }

    @Override
    public String toString() {
        return "Events{" +
                "_id=" + _id +
                ", websiteTitle='" + websiteTitle + '\'' +
                ", title='" + title + '\'' +
                ", venue='" + venue + '\'' +
                ", dateTime=" + date +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
