package com.example.mrjab.hermes;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by yatharth on 28/02/17.
 */

public class ChatContentProvider extends ContentProvider {

    // helper constants for use with the UriMatcher
    private static final int CHAT_LIST = 1;
    private static final int CHAT_ID = 2;
    private static final int MESSAGE_LIST = 5;
    private static final int MESSAGE_ID = 6;
    private static final int USER_LIST = 10;
    private static final int USER_ID = 11;
    private static final UriMatcher URI_MATCHER;
    private HermesDbHelper mHelper;
    // prepare the UriMatcher
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ChatItemsContract.AUTHORITY,
                "chats",
                CHAT_LIST);
        URI_MATCHER.addURI(ChatItemsContract.AUTHORITY,
                "chats/#",
                CHAT_ID);
        URI_MATCHER.addURI(ChatItemsContract.AUTHORITY,
                "messages",
                MESSAGE_LIST);
        URI_MATCHER.addURI(ChatItemsContract.AUTHORITY,
                "messages/#",
                MESSAGE_ID);
        URI_MATCHER.addURI(ChatItemsContract.AUTHORITY,
                "users",
                USER_LIST);
        URI_MATCHER.addURI(ChatItemsContract.AUTHORITY,
                "users/#",
                USER_ID);
    }

   /* @Override
    public boolean onCreate() {
        try {
            mHelper = new HermesDbHelper(getContext());
            return true;
        }
        catch (Exception e)
        {
            return  false;
        }

    }*/

    public boolean initialize(Context context) {
        try {
            mHelper = new HermesDbHelper(context);
            return true;
        }
        catch (Exception e)
        {
            return  false;
        }
    }


    @Override
    public boolean onCreate() {
        mHelper = new HermesDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        boolean useAuthorityUri = false;
        switch (URI_MATCHER.match(uri)) {
            case CHAT_LIST:
                builder.setTables("tbl_chat");
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ChatItemsContract.Chats.SORT_ORDER_DEFAULT;
                }
                break;
            case CHAT_ID:
                builder.setTables("tbl_chat");
                // limit query to one row at most:
                builder.appendWhere(ChatItemsContract.Chats._ID + " = " +
                        uri.getLastPathSegment());
                break;
            case MESSAGE_LIST:
                builder.setTables("tbl_message");
                break;
            case MESSAGE_ID:
                builder.setTables("tbl_message");
                // limit query to one row at most:
                /*builder.appendWhere(Photos._ID +
                        " = " +
                        uri.getLastPathSegment());*/
                break;
            case USER_LIST:
                useAuthorityUri = true;
                break;
            case USER_ID:
                /*builder.setTables(DBSchema.LEFT_OUTER_JOIN_STATEMENT);
                // limit query to one row at most:
                builder.appendWhere(DBSchema.TBL_ITEMS +
                        "." +
                        Items._ID +
                        " = " +
                        uri.getLastPathSegment());*/
                useAuthorityUri = true;
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported URI: " + uri);
        }
        Cursor cursor =
                builder.query(
                        db,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
        // if we want to be notified of any changes:
        if (useAuthorityUri) {
            cursor.setNotificationUri(
                    getContext().getContentResolver(),
                    ChatItemsContract.CONTENT_URI);
        }
        else {
            cursor.setNotificationUri(
                    getContext().getContentResolver(),
                    uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case CHAT_LIST:
                return ChatItemsContract.Chats.CONTENT_TYPE;
            case CHAT_ID:
                return ChatItemsContract.Chats.CONTENT_ITEM_TYPE;
            case MESSAGE_ID:
                //return ChatItemsContract.Messages.CONTENT_PHOTO_TYPE;
            case MESSAGE_LIST:
                //return ChatItemsContract.Messages.CONTENT_TYPE;
            case USER_ID:
                //return ChatItemsContract.Users.CONTENT_ENTITY_TYPE;
            case USER_LIST:
                //return ChatItemsContract.Users.CONTENT_TYPE;
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (URI_MATCHER.match(uri) != CHAT_LIST
                && URI_MATCHER.match(uri) != MESSAGE_LIST && URI_MATCHER.match(uri) != USER_LIST) {
            throw new IllegalArgumentException(
                    "Unsupported URI for insertion: " + uri);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();

        if (URI_MATCHER.match(uri) == CHAT_LIST) {
            long id =
                    db.insert(
                            "tbl_chat",
                            null,
                            values);
            return uri;
            //return getUriForId(id, uri);
        } else if(URI_MATCHER.match(uri) == MESSAGE_LIST) {
            // this insertWithOnConflict is a special case;
            // CONFLICT_REPLACE means that an existing entry
            // which violates the UNIQUE constraint on the
            // item_id column gets deleted. In this case this
            // INSERT behaves nearly like an UPDATE. Though
            // the new row has a new primary key.
            // See how I mentioned this in the Contract class.
            long id =
                    db.insertWithOnConflict(
                            "tbl_message",
                            null,
                            values,
                            SQLiteDatabase.CONFLICT_REPLACE);
            return getUriForId(id, uri);
        }
        else
        {
            long id =
                    db.insertWithOnConflict(
                            "tbl_user",
                            null,
                            values,
                            SQLiteDatabase.CONFLICT_REPLACE);
            return getUriForId(id, uri);
        }
    }

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            //if (!isInBatchMode()) {
                // notify all listeners of changes:
                getContext().
                        getContentResolver().
                        notifyChange(itemUri, null);
           // }
            return itemUri;
        }
        // s.th. went wrong:
        throw new SQLException(
                "Problem while inserting into uri: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int delCount = 0;
        switch (URI_MATCHER.match(uri)) {
            case CHAT_LIST:
                delCount = db.delete(
                        "tbl_chat",
                        selection,
                        selectionArgs);
                break;
            case CHAT_ID:
                String idStr = uri.getLastPathSegment();
                String where = ChatItemsContract.Chats._ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                delCount = db.delete(
                        "tbl_chat",
                        where,
                        selectionArgs);
                break;
            default:
                // no support for deleting photos or entities –
                // photos are deleted by a trigger when the item is deleted
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // notify all listeners of changes:
        if (delCount > 0 ){//&& !isInBatchMode()) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int updateCount = 0;
        switch (URI_MATCHER.match(uri)) {
            case CHAT_LIST:
                updateCount = db.update(
                        "tbl_chat",
                        values,
                        selection,
                        selectionArgs);
                break;
            case CHAT_ID:
                String idStr = uri.getLastPathSegment();
                String where = ChatItemsContract.Chats._ID + " = " + idStr;
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(
                        "tbl_chat",
                        values,
                        where,
                        selectionArgs);
                break;
            default:
                // no support for updating photos or entities!
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        // notify all listeners of changes:
        if (updateCount > 0 ){//&& !isInBatchMode()) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }
}
