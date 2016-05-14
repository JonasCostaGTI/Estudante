package br.com.jonascosta.estudante;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

/**
 * Created by jonascosta on 19/04/16.
 */
public class StudentHelperDB  {


    private final StudentActivity context;
    private final static String NAMEDATABASE = "student.db";
    private SQLiteDatabase db;
    private static Integer[] ids;

    public StudentHelperDB(StudentActivity context) {
        this.context = context;
    }

    public void open() {

        // Abrindo o Banco de Dados
        this.db = context.getBaseContext().openOrCreateDatabase(NAMEDATABASE, Context.MODE_WORLD_WRITEABLE, null);

        this.createTable();

    }

    private void createTable() {

        //cria tabela
        String sql = "CREATE TABLE IF NOT EXISTS Student ("+
                "Id integer primary key autoincrement, " +
                "Code text not null,"+
                "Name text not null," +
                "Email text not null);";
        this.db.execSQL(sql);
    }

    public void removelAllStudents() {
//        //remove todas as colunas
//        this.db.delete("Student",null, null);


        //deleta toda a tabela
        String sql = "DROP TABLE IF EXISTS Student;";
        this.db.execSQL(sql);

        this.createTable();


    }

    public void insertStudentsSample() {

        this.insertStudent("B01", "Beltrano", "beltrano@mail.com");
        this.insertStudent("S02", "Sicrano", "Sicrano@mail.com");
        this.insertStudent("F03", "Fulano", "fulano@mail.com");
        
        
    }

    //insere
    public void insertStudent(String code, String name, String email) {

        ContentValues initialValues = new ContentValues();
        initialValues.put("Code", code);
        initialValues.put("Name", name);
        initialValues.put("Email", email);

        // Executa o insert no DB
        db.insert("Student", null, initialValues);
    }

    //lista todos
    public String[] getListStudents() {

        String[] fields = {"Name", "Id", "Email"};
        Cursor ret =  db.query("Student", fields, null, null, null, null, null);

        //Carrega a lista percorrendo cursor



        String[] values = new String[ret.getCount()];
        ids = new Integer[ret.getCount()];

        boolean hasRecord = ret.moveToFirst();
        int posicao = 0;



         while ( hasRecord ){
            ids[posicao] = ret.getInt(ret.getColumnIndex("Id"));
             values[posicao++] = ret.getString(ret.getColumnIndex("Name"));


             hasRecord = ret.moveToNext();
         }

        return values;
    }

    //pega pelo id
    public static Integer getIdStudent(int position) {
        return ids[position];
    }

    public  Cursor getStudent(int id) {
        String[] fields = {"Id", "Code", "Name", "Email"};
        Cursor ret =  db.query("Student", fields, "Id = " + id,  null, null, null, null);

        ret.moveToFirst();

        return ret;
    }



    //altera
    public void updateStudent(String id, String code, String name, String email) {


        ContentValues values = new ContentValues();
        values.put("Code", code);
        values.put("Name", name);
        values.put("Email", email);

        db.update("Student", values, "Id = ? ", new String[]{id});

    }

    //deleta
    public void deleteStudent(String id) {
        db.delete("Student", "Id = ? ", new String[]{id});
    }
}
