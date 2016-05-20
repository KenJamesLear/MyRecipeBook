package edu.scranton.lear.myrecipebook;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class AddRecipeInstructionFragment extends Fragment {

    public interface OnRecipeDoneListener{
        void recipeDone(ArrayList<String> instructions);
    }

    private ArrayList<String> mInstructions;
    private EditText mEditText;
    private OnRecipeDoneListener mRecipeDoneListener;
    private int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;

    public AddRecipeInstructionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       mInstructions = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mRecipeDoneListener = (OnRecipeDoneListener) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_recipe_instruction, container, false);
        mEditText = (EditText) view.findViewById(R.id.recipeInstructionText);
        Button nextInstruction = (Button) view.findViewById(R.id.addNextInstructionButton);

        nextInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEditText.getText().toString();
                if (input.compareTo("") != 0)
                    storeInstruction(input);
                else
                    Toast.makeText(getActivity(),"Please fill out all fields",Toast.LENGTH_SHORT).show();
            }
        });
        Button doneButton = (Button) view.findViewById(R.id.doneWithAddingRecipeButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneAddingInstructions();

            }
        });
        Button imageButton = (Button) view.findViewById(R.id.addImageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

        mImageView = (ImageView) view.findViewById(R.id.imageViewAddInstruction);



        return view;
    }

    public void storeInstruction(String input){
        mInstructions.add(input);
        mEditText.setText("");


    }

    public void doneAddingInstructions(){
        mRecipeDoneListener.recipeDone(mInstructions);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }




}
