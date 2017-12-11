package pl.dom3k.broadcaster2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class MyContentProvider extends ContentProvider {

    private MySQLiteHelper dbHelper;

    private static final int TABLE = 1;
    private static final int SINGLE_RECORD = 2;
    private static final String AUTHORITY = "pl.dom3k.broadcaster2.MyContentProvider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/table");
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "table", TABLE);
        uriMatcher.addURI(AUTHORITY, "table/#", SINGLE_RECORD);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MySQLiteHelper(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case TABLE:
                return "vnd.android.cursor.dir/vnd.com.as400samplecode.contentprovider.countries";
            case SINGLE_RECORD:
                return "vnd.android.cursor.item/vnd.com.as400samplecode.contentprovider.countries";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case TABLE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long id = db.insert("table", null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables("table");

        switch (uriMatcher.match(uri)) {
            case TABLE:
                break;
            case SINGLE_RECORD:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere("_id" + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        return queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case TABLE:
                break;
            case SINGLE_RECORD:
                String id = uri.getPathSegments().get(1);
                selection = "_id" + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete("table", selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case TABLE:
                //do nothing
                break;
            case SINGLE_RECORD:
                String id = uri.getPathSegments().get(1);
                selection = "_id" + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update("table", values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

}
