package css.mrauzi.vogellasqlitedatabase;

/**
 * MySQLiteHelper - allows for the creation and upgrading of a database.
 * Created by mrauzi on 3/31/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMENTS = "comments";  // the name of the "comments" table
    public static final String COLUMN_ID = "_id";            // the id(primary key) column for each record
    public static final String COLUMN_COMMENT = "comment";   // the comment column for each comment
    public static final String COLUMN_RATING = "rating";     // the rating column for each comment

    private static final String DATABASE_NAME = "commments.db";  // the name of the database
    private static final int DATABASE_VERSION = 1;               // the version of the database

    /**
     * Create Table command
     * CREATE TABLE table_name (
     *  column1 datatype,
     *  column2 datatype,
     *
     *  PRIMARY KEY(),
     *  );
     */
    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COMMENTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_COMMENT
            + " text not null, " + COLUMN_RATING + " text);";

    /**
     * MySQLiteHelper() - constructor for the database helper class
     * @param context extended from SQLiteOpenHelper class
     */
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * onCreate() - method to create the database
     * @param database the database you will create
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    /**
     * onUpgrade() - upgrades the database by dropping the current one and creating a new one
     * @param db the datatabase to be upgraded
     * @param oldVersion the old version of the database
     * @param newVersion  the new version of the database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
    }

}