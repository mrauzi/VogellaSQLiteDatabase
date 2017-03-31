package css.mrauzi.vogellasqlitedatabase;

/**
 * CommentsDataSource -data access object that contains the CRUD(create, read, update, delete) methods for the database
 * Created by mrauzi on 3/31/2017.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CommentsDataSource {

    // Database fields
    private SQLiteDatabase database;  // instance of the database
    private MySQLiteHelper dbHelper;  // instance of the database helper class
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,  // string array of all the columns in the database
            MySQLiteHelper.COLUMN_COMMENT };

    /**
     * CommentsDataSource() - constructor for CommentsDataSource class
     * @param context
     */
    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    /**
     * open() - opens the created database from the database helper class and makes a writable version
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * close() - closes the database
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * createComment() - saves a new comment to the comment table
     * @param comment the comment string to be added
     * @return returns the comment that was created
     */
    public Comment createComment(String comment) {
        // stores the new values into a ContentValues object
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
        // insert command into the table
        // SQL insert - insert(String table, String nullColumnHack, ContentValues values)
        // INSERT INTO table_name (column1, column2, column3, ...)
        // VALUES (value1, value2, value3, ...);
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
                values);
        // Maps the database query to the cursor
        // query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();  // close the cursor
        return newComment;
    }

    /**
     * deleteComment() - delete the comment from the comment table in the database
     * @param comment the comment to be deleted
     */
    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        // delete query
        // delete(String table, String whereClause, String[] whereArgs)
        // SQL DELETE FROM table_name
        // WHERE condition;
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    /**
     * getAllComments() - gets a list of all the comments in the database
     * @return returns the list of comments
     */
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();
        // query the database to for all of the field for all of the records in the comment table
        // query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit)
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        // loop through the cursor converting each row into a comment object and adding to the list of comments
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    /**
     * cursorToComment() - converts the current record the cursor points to into a comment object
     * @param cursor points to a record in the comment table
     * @return a comment object
     */
    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
        comment.setComment(cursor.getString(1));
        return comment;
    }
}
