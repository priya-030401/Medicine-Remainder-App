//DESIGN
package com.nagashree.medicineremainderapp; 
import androidx.appcompat.app.AlertDialog; 
import androidx.appcompat.app.AppCompatActivity; 
import android.content.Intent; 
import android.database.Cursor; 
import android.os.Bundle; 
import android.provider.AlarmClock; 
import android.view.View; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.Spinner; 
import android.widget.Toast; 
public class insert extends AppCompatActivity { 
 Button back,add,viewr1,home1,profile1; 
 EditText medicineDate,medicineTime,minute; 
 Spinner MedicineName,timeofday; 
 DatabaseHelper connection; 
 
 @Override 
 protected void onCreate(Bundle savedInstanceState) { 
 super.onCreate(savedInstanceState); 
   connection=new DatabaseHelper(this); 
 setContentView(R.layout.insert); 
 back=(Button) findViewById(R.id.button08); 
 add=(Button) findViewById(R.id.button07); 
 MedicineName=findViewById(R.id.spinnerbp); 
 medicineDate=findViewById(R.id.textView012); 
 medicineTime=findViewById(R.id.timer1); 
 timeofday=(Spinner)findViewById(R.id.spinner02); 
 minute=findViewById(R.id.min1); 
 viewr1=findViewById(R.id.viewbtn1); 
 home1=findViewById(R.id.homebtn1); 
 profile1=findViewById(R.id.profilebtn1); 
 back.setOnClickListener(new View.OnClickListener() { 
 @Override 
 public void onClick(View view) { 
 Intent intent=new Intent(insert.this,SelectCategory.class); 
 startActivity(intent); 
 } 
 }); 
 home1.setOnClickListener(new View.OnClickListener() { 
 @Override 
 public void onClick(View view) { 
 Intent intent= new Intent(insert.this,homepage.class); 
 startActivity(intent); 
 } 
 }); 
 profile1.setOnClickListener(new View.OnClickListener() {
   @Override 
 public void onClick(View view) { 
 Intent intent= new Intent(insert.this,profilepage.class); 
 startActivity(intent); 
 } 
 }); 
 viewr1.setOnClickListener(new View.OnClickListener() { 
 @Override 
 public void onClick(View view) { 
 Cursor result = connection.getdata(); 
 if(result.getCount()==0){ 
 Toast.makeText(insert.this,"No Medicine 
Added",Toast.LENGTH_SHORT).show(); 
 } 
 StringBuilder buffer= new StringBuilder(); 
 while(result.moveToNext()) 
 { 
 buffer.append("Medicine Name:"+ result.getString(0)+"\n"); 
 buffer.append("Date:"+ result.getString(1)+"\n"); 
 buffer.append("Time of a day:"+ result.getString(2)+"\n\n\n\n"); 
 } 
 AlertDialog.Builder builder=new AlertDialog.Builder(insert.this); 
 builder.setCancelable(true); 
 builder.setTitle("Medicine Details"); 
 builder.setMessage(buffer.toString()); 
 builder.show(); 
 } 
 });
 add.setOnClickListener(new View.OnClickListener() { 
 @Override 
 public void onClick(View view1) { 
 String name=MedicineName.getSelectedItem().toString(); 
 String date=medicineDate.getText().toString(); 
 String daytime=timeofday.getSelectedItem().toString(); 
 String timee=medicineTime.getText().toString(); 
 int hour=Integer.parseInt(timee); 
 int minu=Integer.parseInt(minute.getText().toString()); 
 boolean insert1=connection.insertvalues(name,date,timee,daytime); 
 if(insert1) { 
 Toast.makeText(getApplicationContext(), "Data inserted", 
Toast.LENGTH_SHORT).show(); 
 medicineDate.setText(null); 
 medicineTime.setText(null); 
 } 
 else 
 { 
 Toast.makeText(getApplicationContext(), "Data not inserted", 
Toast.LENGTH_SHORT).show(); 
 } 
 Intent intent=new Intent(AlarmClock.ACTION_SET_ALARM); 
 intent.putExtra(AlarmClock.EXTRA_HOUR,hour); 
 intent.putExtra(AlarmClock.EXTRA_MINUTES,minu); 
  if(hour<=24 & minu<=60) { 
 startActivity(intent); 
 } 
 }}); 
} 
package com.nagashree.medicineremainderapp; 
import android.content.ContentValues; 
import android.database.Cursor; 
import android.database.sqlite.SQLiteDatabase; 
import android.content.Context; 
import android.database.sqlite.SQLiteOpenHelper; 
import androidx.annotation.Nullable; 
public class DatabaseHelper extends SQLiteOpenHelper { 
 public DatabaseHelper(Context context) { 
 super(context,"Medicinedb",null,1); 
 } 
 
 public void onCreate(SQLiteDatabase db) { 
 db.execSQL("Create Table medicinedata(MedicineName Text,date Text,time1 Text,time 
Text)"); 
 } 
 public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) { 
 }
 public boolean insertvalues(String mdname,String mddate,String mtime1,String mdtime) { 
 SQLiteDatabase database=this.getWritableDatabase(); 
 ContentValues values=new ContentValues(); 
 values.put("MedicineName",mdname); 
 values.put("date",mddate); 
 values.put("time1",mtime1); 
 values.put("time",mdtime); 
 long var =database.insert("medicinedata",null,values); 
 if(var==-1){ 
 return false; 
 } 
 else { 
 return true; 
 } 
 } 
 public Cursor getdata(){ 
 SQLiteDatabase database=this.getWritableDatabase(); 
 Cursor cur = database.rawQuery("select * from medicinedata",null); 
 return cur; 
 } 
}
