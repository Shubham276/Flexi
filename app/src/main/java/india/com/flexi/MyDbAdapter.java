package india.com.flexi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class MyDbAdapter {
    myDBhelper myhelper;

    public MyDbAdapter(Context context)
    {
        myhelper = new myDBhelper(context);
    }

    public long insertData(String name, String contact, String operator,String amount,String Type) {

         SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDBhelper.NAME,name);
        contentValues.put(myDBhelper.dcontact, contact);
        contentValues.put(myDBhelper.doperator, operator);
        contentValues.put(myDBhelper.damount, amount);
        contentValues.put(myDBhelper.dtype, Type);
        long id = db.insert(myDBhelper.tb_name, null, contentValues);
        return id;
    }
    public String confirmData(String name,String mobile) {
        SQLiteDatabase db=myhelper.getReadableDatabase();
        String[] columns ={myDBhelper.NAME,myDBhelper.dcontact,myDBhelper.doperator,myDBhelper.damount,myDBhelper.dtype};
        String whereArgs[]={name,mobile};
        Cursor c = db.query(myhelper.tb_name,columns,
                myDBhelper.NAME+" =? AND "+myDBhelper.dcontact+" =?",
                whereArgs,null,null,null);
        StringBuffer buffer=new StringBuffer();
        while (c.moveToNext())
        {
            String tname =c.getString(c.getColumnIndex(myDBhelper.NAME));
            String tcontact =c.getString(c.getColumnIndex(myDBhelper.dcontact));
            String toperator =c.getString(c.getColumnIndex(myDBhelper.doperator));
            String tamount =c.getString(c.getColumnIndex(myDBhelper.damount));
            String tType =c.getString(c.getColumnIndex(myDBhelper.dtype));
            buffer.append(tname+" "+tcontact+" "+toperator+" "+tamount+" "+tType);
        }
        String s=buffer.toString();
        return s;
    }

    public String mobileData(String name,String mobile) {
        SQLiteDatabase db=myhelper.getReadableDatabase();
        String[] columns ={myDBhelper.dcontact};
        String whereArgs[]={name,mobile};
        Cursor c = db.query(myhelper.tb_name,columns,
                myDBhelper.NAME+" =? AND "+myDBhelper.dcontact+" =?",
                whereArgs,null,null,null);
        StringBuffer buffer=new StringBuffer();
        while (c.moveToNext())
        {
            String tcontact =c.getString(c.getColumnIndex(myDBhelper.dcontact));
            buffer.append(tcontact);
        }
        String s=buffer.toString();
        return s;
    }
    public  int deleteData(String name,String mobile)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={name,mobile};
        int count =db.delete(myDBhelper.tb_name, myDBhelper.NAME+" =? AND "+myDBhelper.dcontact+" =?",whereArgs);
            return  count;
    }



    class myDBhelper extends SQLiteOpenHelper {
        private static final String db_name = "Independent";
        private static final String tb_name = "Information";
        private static final int Version = 1;
        private static final String UID = "_id";
        private static final String NAME = "Name";
        private static final String dcontact = "contact";
        private static final String doperator = "operator";
        private static final String damount = "amount";
        private static final String dtype = "Type";

        private static final String create_query = "CREATE TABLE "+tb_name+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+
                " VARCHAR(50), "+dcontact+ " NUMBER(10), "+doperator+" VARCHAR(100), "+damount+" NUMBER(10), "+dtype+" VARCHAR(100))";
        private Context context;
        public myDBhelper(Context context) {
            super(context, db_name, null, Version);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase)
        {
            try
            {
                Toast.makeText(context, "onCreate called", Toast.LENGTH_SHORT).show();
                sqLiteDatabase.execSQL(create_query);
            } catch (SQLException e)
            {
                Toast.makeText(context, "database not created", Toast.LENGTH_SHORT).show();
            }
            }
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

                this.onCreate(sqLiteDatabase);
        }
    }
    }

