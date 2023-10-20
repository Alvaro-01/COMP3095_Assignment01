package ca.gbc.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_post_line_item")
public class UserLineItem {
    @Id
    private Long id;
    private String postMessage;

    public void setPostMessage(String postMessage) {

    }
}
