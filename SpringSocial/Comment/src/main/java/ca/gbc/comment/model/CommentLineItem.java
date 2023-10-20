package ca.gbc.comment.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "t_comment_line_item")
public class CommentLineItem {
    @Id
    private Long id;
    private String comment;

    public void setComment(String comment) {
    }
}
