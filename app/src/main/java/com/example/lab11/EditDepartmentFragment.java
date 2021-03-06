package com.example.lab11;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditDepartmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditDepartmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DEPARTMENT= "department";
    private static final String ARG_COURSES = "courses";

    private Department department;
    private ArrayList<Course> courses;
    private EditText txtName;
    private RecyclerView recyclerView;
    CourseAdapter mAdapter;

    Button btnNewCourse;

    public EditDepartmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditDepartmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditDepartmentFragment newInstance(Department department, ArrayList<Course> courses) {
        EditDepartmentFragment fragment = new EditDepartmentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_DEPARTMENT, department);
        args.putSerializable(ARG_COURSES, courses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            department = (Department) getArguments().getSerializable(ARG_DEPARTMENT);
            courses = (ArrayList<Course>) getArguments().getSerializable(ARG_COURSES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_department, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtName = view.findViewById(R.id.name);
        btnNewCourse = view.findViewById(R.id.new_course);
        recyclerView = view.findViewById(R.id.courses);

        txtName.setText(department.name);

        mAdapter = new CourseAdapter(courses);

        recyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        btnNewCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDepartmentFragment.this.getActivity());
                builder.setTitle("New Course");
                final EditText txtCode = new EditText(EditDepartmentFragment.this.getActivity());
                txtCode.setHint("Course Code");
                txtCode.setInputType(InputType.TYPE_CLASS_TEXT );
                final EditText txtName = new EditText(EditDepartmentFragment.this.getActivity());
                txtName.setHint("Course Name");
                txtName.setInputType(InputType.TYPE_CLASS_TEXT );
                LinearLayout viewGroup = new LinearLayout(EditDepartmentFragment.this.getActivity());
                viewGroup.setOrientation(LinearLayout.VERTICAL);
                viewGroup.addView(txtCode);
                viewGroup.addView(txtName);
                builder.setView(viewGroup);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String code = txtCode.getText().toString();
                        Log.d("Dialog", code);
                        String name = txtName.getText().toString();
                        Log.d("Dialog", name);
                        Course course = new Course();
                        course.code = code;
                        course.name = name;
                        addCourse(course);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    public void addCourse(Course course){
        courses.add(course);
        mAdapter.notifyDataSetChanged();
    }

    public Department getDepartment(){
        department.name = txtName.getText().toString();
        return department;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}