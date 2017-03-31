package css.mrauzi.vogellasqlitedatabase;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;


public class MainActivity extends ListActivity {
    private CommentsDataSource datasource;  // instance of the CommentsDataSource data access object class for the database
    EditText etRating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etRating = (EditText)findViewById(R.id.editTextRating);
        datasource = new CommentsDataSource(this);
        datasource.open();  // opens the database

        List<Comment> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    /**
     * onClick() - Will be called via the onClick attribute of the buttons in main.xml
     *
     * @param view
     */
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
        Comment comment = null;
        switch (view.getId()) {
            // allows user to add a comment to the comment
            case R.id.add:
                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                String userRating = etRating.getText().toString();
                comment = datasource.createComment(comments[nextInt], userRating);
                adapter.add(comment);
                break;
            case R.id.delete:
                // allows the user to delete a comment
                if (getListAdapter().getCount() > 0) {
                    comment = (Comment) getListAdapter().getItem(0);
                    // deletes comment from database
                    datasource.deleteComment(comment);
                    adapter.remove(comment);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * onResume() - opens the database when the app is resumed
     */
    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    /**
     * onPause() - closes the database when the app is paused
     */
    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
