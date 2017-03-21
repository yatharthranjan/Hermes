package com.example.mrjab.hermes;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

import static android.icu.lang.UProperty.NAME;

/**
 * Created by yatharth on 28/02/17.
 */

public class ChatItemsContract {

        /**
         * The authority of the lentitems provider.
         */
        public static final String AUTHORITY =
                "com.example.mrjab.hermes.chatitems";
        /**
         * The content URI for the top-level
         * lentitems authority.
         */
        public static final Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY);

        /**
         * Constants for the Items table
         * of the lentitems provider.
         */
        public static final class Chats
                implements CommonColumns {

            public static final Uri CONTENT_URI =
                    Uri.withAppendedPath(
                            ChatItemsContract.CONTENT_URI,
                            "chats");
            /**
             * The mime type of a directory of items.
             */
            public static final String CONTENT_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE +
                            "/vnd.com.example.chatitems_chats";
            /**
             * The mime type of a single item.
             */
            public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE +
                            "/vnd.com.example.chatitems_chats";
            /**
             * A projection of all columns
             * in the items table.
             */
            public static final String[] PROJECTION_ALL =
                    {"ChatID", "fk_InitUserID_by", "fk_InitUserID_with","KeyValue","CreateDate"};
            /**
             * The default sort order for
             * queries containing NAME fields.
             */
            public static final String SORT_ORDER_DEFAULT =
                    "ChatID" + " ASC";
        }

        /**
         * Constants for the Photos table of the
         * lentitems provider. For each item there
         * is exactly one photo. You can
         * safely call insert with the an already
         * existing ITEMS_ID. You won’t get constraint
         * violations. The content provider takes care
         * of this.<br>
         * Note: The _ID of the new record in this case
         * differs from the _ID of the old record.
         */
        public static final class Messages
                implements BaseColumns {

        }

        /**
         * Constants for a joined view of Items and
         * Photos. The _id of this joined view is
         * the _id of the Items table.
         */
        public static final class Users
                implements CommonColumns {


        }

        /**
         * This interface defines common columns
         * found in multiple tables.
         */
        public static interface CommonColumns
                extends BaseColumns { }



    }
