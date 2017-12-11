package pl.dom3k.broadcaster2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import static pl.dom3k.broadcaster2.MySQLiteHelper.KEY_ID;
import static pl.dom3k.broadcaster2.MySQLiteHelper.TABLE_NAME;

public class MyContentProvider extends ContentProvider {

    private MySQLiteHelper dbHelper;

    private static final int TABLE = 1;
    private static final int SINGLE_RECORD = 2;
    private static final String AUTHORITY = "pl.dom3k.broadcaster2.MyContentProvider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, TABLE);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", SINGLE_RECORD);
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
                return "vnd.android.cursor.dir/vnd.pl.dom3k.broadcaster2.MyContentProvider.records";
            case SINGLE_RECORD:
                return "vnd.android.cursor.item/vnd.pl.dom3k.broadcaster2.MyContentProvider.records";
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
        long id = db.insert(TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case TABLE:
                break;
            case SINGLE_RECORD:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ID + "=" + id);
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
                selection = KEY_ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete(TABLE_NAME, selection, selectionArgs);
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
                selection = KEY_ID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update(TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

}
