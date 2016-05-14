package br.com.jonascosta.estudante;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StudentActivity extends AppCompatActivity  {

    private static final int RETURNCODE = 200 ;
    private static StudentHelperDB studentDB;
    private ListView listStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studant);



        
        this.studentDB = new StudentHelperDB(this);
        this.studentDB.open();

       // this.studentDB.removelAllStudents();
        //this.studentDB.insertStudentsSample();


        this.showStudents();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, 0, 0, "Incluir");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case 0:
                Intent i = new Intent(this, StudentEntryActivity.class);
                startActivityForResult(i, RETURNCODE);
                return true;
        }

        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.showStudents();
        super.onActivityResult(requestCode, resultCode, data);
    }

    //lista
    private void showStudents() {

        listStudent = (ListView)findViewById(R.id.studentView);

        String[] values = this.studentDB.getListStudents();

        ArrayAdapter<String> studentsData =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);


        //pega item selecionado
        listStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //pega studenr na posicao
                Integer idStudent = StudentHelperDB.getIdStudent(position);

                Intent i = new Intent(view.getContext(), StudentEntryActivity.class);
                i.putExtra("ID", idStudent);



                startActivityForResult(i, RETURNCODE);


            }
        });



        listStudent.setAdapter(studentsData);



    }


    public static StudentHelperDB getStudentDB(){

        return studentDB;
    }


}
