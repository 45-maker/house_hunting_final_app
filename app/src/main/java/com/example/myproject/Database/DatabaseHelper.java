package com.example.myproject.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myproject.Domain.PropertyDomain;
import com.example.myproject.R;
import com.example.myproject.model.Property;
import com.example.myproject.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "house_app.db";
    private static final int DATABASE_VERSION = 5; // Incremented for clean state

    // Table and Column Constants
    private static final String TABLE_PROPERTIES = "properties";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DESCRIPTION = "description";

    private static final String COLUMN_IMAGE ="image";


    private static final String COLUMN_TYPE = "type";

    private static final String COLUMN_IS_FAVORITE = "is_favorite";

    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "user_id";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "password";
    private static final String COLUMN_NAME ="name";
    private static final String COLUMN_REASON="reason";
    private static final String COLUMN_STATUS="status";
    private static final String TABLE_APPLICATION ="application" ;
    private static final String TABLE_FAVOURITES = "favourites";
    private static final String COL_ID ="id" ;
    private static final String COL_PROPERTY_ID = "propertyId";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROPERTIES_TABLE = "CREATE TABLE " + TABLE_PROPERTIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_PRICE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_IMAGE + " INTEGER,"
                + COLUMN_TYPE + " TEXT,"
                + COLUMN_IS_FAVORITE + " INTEGER DEFAULT 0)";

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT,"
                + "phone TEXT,"
                + COL_EMAIL + " TEXT,"
                + COL_PASSWORD + " TEXT)";

        String CREATE_TABLE_APPLICATION = "CREATE TABLE " + TABLE_APPLICATION + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_REASON + " TEXT,"
                + COLUMN_STATUS + " TEXT DEFAULT 'Pending')";

        String CREATE_TABLE_FAVOURITES = "CREATE TABLE " + TABLE_FAVOURITES + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_PROPERTY_ID + " INTEGER)";

        db.execSQL(CREATE_PROPERTIES_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_TABLE_APPLICATION);
        db.execSQL(CREATE_TABLE_FAVOURITES);
    }
    public boolean insertUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_EMAIL + "=? AND " + COL_PASSWORD + "=?";

        Cursor cursor = db.rawQuery(query, new String[]{email, password});



        int count = cursor.getCount();

        cursor.close();

        db.close();

        return count > 0; // returns true if user found

    }

    // --- FIX 3: GET ALL PROPERTIES ---
    public List<PropertyDomain> getAllProperties() {
        List<PropertyDomain> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROPERTIES, null);
        if (cursor.moveToFirst()) {
            do {
                PropertyDomain property = new PropertyDomain(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),


                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
                );
                property.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                property.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1);
                list.add(property); // ✅ Add to list
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
    public void insertSampleProperties( ) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Clear old data to prevent duplicates
        db.delete(TABLE_PROPERTIES, null, null);
        // Sample 1: Recommended
        addProperty(db, "Modern Apartment", "Nairobi West", "25,000", "2 bedroom with modern finishing",R.drawable.pic_4, "recommended");

        // Sample 2: Recommended
        addProperty(db, "Luxury Villa", "Karen", "120,000", "Luxury villa with garden and pool", R.drawable.house_1, "recommended");

        // Sample 3: Nearby (Fixed v3 issue)
        addProperty(db, "Bangola", "Kilimani", "240,000", "Spacious bungalow in a quiet area", R.drawable.house_2, "nearby");

        // Sample 4: Nearby
        addProperty(db, "Family Villa", "Karen", "9,800,000", "Large family estate with private security",R.drawable.house_3, "nearby");

        // Sample 5: Recommended
        addProperty(db, "Modern Suite", "Utawala", "36,000", "Cozy modern suite for young professionals",R.drawable.pic_4, "recommended");
        addProperty(db,"Modern Apartment", "Nairobi West", "25,000", "2 bedroom with modern finishing", R.drawable.house_1, "recommended");
        addProperty(db,"Luxury Villa", "Karen", "120,000", "Spacious 4 bedroom villa with pool", R.drawable.house_2, "recommended");
        addProperty(db,"Cozy Studio", "Langata", "15,000", "Perfect for students", R.drawable.house_3, "nearby");
    }

    // Helper to avoid repeating ContentValues code
    private void addProperty(SQLiteDatabase db, String title, String loc, String price, String desc,int image, String type) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_LOCATION, loc);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_DESCRIPTION, desc);

        cv.put(COLUMN_IMAGE, image);

        cv.put(COLUMN_TYPE, type.toLowerCase());

        db.insert(TABLE_PROPERTIES, null, cv);

    }

    // FIXED: Properly parameterized query
    public List<PropertyDomain> getPropertiesByType(String type) {
        List<PropertyDomain> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PROPERTIES + " WHERE " + COLUMN_TYPE + " = ?",
                new String[]{type.toLowerCase()});

        if (cursor.moveToFirst()) {
            do {
                // 1. Initialize the object correctly using column names
                PropertyDomain property = new PropertyDomain(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),


                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),

                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
                );

                // 2. Use the 'property' variable to set extra fields
                property.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));

                // Check favorite status (assuming 1 is true, 0 is false)
                int favoriteColIndex = cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE);
                property.setFavorite(cursor.getInt(favoriteColIndex) == 1);

                // 3. Add the 'property' variable to the list
                list.add(property);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // NEW: Toggle Favorite
    public void addToFavourite(int propertyId){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_PROPERTY_ID, propertyId);

        db.insert(TABLE_FAVOURITES, null, values);
    }
    public ArrayList<PropertyDomain> getFavouriteProperties() {
        ArrayList<PropertyDomain> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_PROPERTIES +
                " WHERE " + COLUMN_ID +
                " IN (SELECT " + COL_PROPERTY_ID + " FROM " + TABLE_FAVOURITES + ")";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                PropertyDomain property = new PropertyDomain(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
                );

                property.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                property.setFavorite(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_FAVORITE)) == 1);

                list.add(property);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }
    public void updateFavouriteStatus(int id, boolean isFavourite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_FAVORITE, isFavourite ? 1 : 0);
        db.update(TABLE_PROPERTIES, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    @SuppressLint("Range")
    public List<Property> getBookedPropertiesByUser(int userId) {
        List<Property> properties = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT p.* FROM property p JOIN bookings b ON p.id = b.property_id WHERE b.user_id=?",
                new String[]{String.valueOf(userId)}
        );

        if (cursor.moveToFirst()) {
            do {
                Property property;
                property = new Property(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("location")),
                        cursor.getString(cursor.getColumnIndex("price")),
                        cursor.getString(cursor.getColumnIndex("description")),
                        cursor.getInt(cursor.getColumnIndex("beds")),
                        cursor.getInt(cursor.getColumnIndex("baths")),
                        cursor.getInt(cursor.getColumnIndex("garage")),
                        cursor.getString(cursor.getColumnIndex("size")),
                        cursor.getInt(cursor.getColumnIndex("image"))
                );
                properties.add(property);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return properties;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROPERTIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITES);

        onCreate(db);
    }

    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;

        // FIX: Changed "id = ?" to COL_USER_ID + " = ?"
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_USERS + " WHERE " + COL_USER_ID + " = ?",
                new String[]{String.valueOf(userId)}
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // Use getColumnIndex safely to avoid crashes if columns are missing
                int idIndex = cursor.getColumnIndex(COL_USER_ID);
                int emailIndex = cursor.getColumnIndex(COL_EMAIL);
                int nameIndex = cursor.getColumnIndex("name");
                int phoneIndex = cursor.getColumnIndex("phone");

                int id = (idIndex != -1) ? cursor.getInt(idIndex) : 0;
                String email = (emailIndex != -1) ? cursor.getString(emailIndex) : "";
                String name = (nameIndex != -1) ? cursor.getString(nameIndex) : "User " + id;
                String phone = (phoneIndex != -1) ? cursor.getString(phoneIndex) : "No Phone";

                user = new User(id, name, email, phone);
            }
            cursor.close();
        }
        return user;
    }
    // Inside your DBHelper class
    public boolean updateStatus(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", "Approved");

        // Update the row where the name matches
        int result = db.update("applications", values, "name=?", new String[]{name});
        return result > 0;
    }
}
