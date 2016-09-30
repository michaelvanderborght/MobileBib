package ap.edu.bib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SQLHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "bibmap.db";
    private static final String TABLE_BIBLIOTHEKEN = "bibliotheken";
    private static final int DATABASE_VERSION = 1;

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_BIBLIOTHEKEN + "(naam STRING PRIMARY KEY, longitude STRING, latitude STRING)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIBLIOTHEKEN);
        onCreate(db);
    }

    public ArrayList<Bibliotheek> getAllBibliotheken() {
        ArrayList allBibliotheken = new ArrayList<Bibliotheek>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_BIBLIOTHEKEN, null);
        if (cursor.moveToFirst()) {
            do {
                String naam = cursor.getString(0);
                String longitude = cursor.getString(1);
                String latitude = cursor.getString(2);
                allBibliotheken.add(new Bibliotheek(naam, longitude, latitude));
            } while (cursor.moveToNext());
        }

        return allBibliotheken;
    }

    public void saveBibliotheken(JSONArray allBibliotheken) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (int i = 0; i < allBibliotheken.length(); i++) {
            try {
                JSONObject obj = (JSONObject) allBibliotheken.get(i);
                String naam = obj.getString("naam");
                String longitude = obj.getString("point_lng");
                String latitude = obj.getString("point_lat");

                ContentValues values = new ContentValues();
                values.put("naam", naam);
                values.put("longitude", longitude);
                values.put("latitude", latitude);

                db.insert(TABLE_BIBLIOTHEKEN, null, values);
            }
            catch(Exception ex) {
                Log.e("edu.ap.bib", ex.getMessage());
            }
        }
        db.close();
    }
}
