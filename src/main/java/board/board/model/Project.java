package board.board.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="t_project")
@NoArgsConstructor
@Data
public class Project {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int projectidx;

    @Column(nullable=false)
    private String creatorid;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false)
    private String contents;

    private int startedday;

    public int getStartedday() {
        return startedday;
    }

    public void setStartedday(int startedday) {
        this.startedday = startedday;
    }

    public int getProjectidx() {
        return projectidx;
    }

    public void setProjectidx(int projectidx) {
        this.projectidx = projectidx;
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
