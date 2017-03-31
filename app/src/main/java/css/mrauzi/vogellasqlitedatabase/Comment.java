package css.mrauzi.vogellasqlitedatabase;

/**
 * Comment - creates the comments as an id and a string and sets
 * up the methods to set and access them.
 * Created by mrauzi on 3/31/2017.
 */

public class Comment {
    private long id;  // the id of the comment
    private String comment;  // the string of the comment itself


    /**
     * getId() - returns the id of the comment
     * @return the id of the comment
     */
    public long getId() {
        return id;
    }

    /**
     * setId() - sets the id of the comment
     * @param id the id number of the comment
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * getComment() - returns the string of the comment
     * @return  the string of the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * setComment() - sets the comment
     * @param comment the string of the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * toString() - text display of the comment
     * @return  the text display of hte comment
     */
    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return comment;
    }
}
