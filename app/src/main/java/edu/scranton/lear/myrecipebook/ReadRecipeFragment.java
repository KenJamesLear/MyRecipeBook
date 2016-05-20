package edu.scranton.lear.myrecipebook;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReadRecipeFragment extends Fragment {

    public interface OnDoneReadingRecipeListener{
        void resetMenu();
    }

    public static final String ARG_INSTRUCTIONS = "edu.scranton.lear.myrecipebook.ARG_INSTRUCTIONS";
    private ArrayList<String> mInstructions;
    int mNumberOfInstruction;
    private TextView mTextView;
    private OnDoneReadingRecipeListener mDoneReadingRecipeListener;

    public ReadRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDoneReadingRecipeListener = (OnDoneReadingRecipeListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInstructions = new ArrayList<>();
        Bundle bundle = getArguments();
        if (bundle != null) mInstructions = bundle.getStringArrayList(ReadRecipeFragment.ARG_INSTRUCTIONS);
        else mInstructions.add("Error getting argument");
        mNumberOfInstruction = 0;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read_recipe, container, false);
        mTextView = (TextView) view.findViewById(R.id.readRecipeInstruction);
        mTextView.setText((mNumberOfInstruction + 1) + ". " + mInstructions.get(mNumberOfInstruction));
        Button nextInstruction = (Button) view.findViewById(R.id.readNextInstructionButton);
        nextInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNumberOfInstruction < mInstructions.size()) {
                    mNumberOfInstruction++;
                    if (mNumberOfInstruction != mInstructions.size())
                        mTextView.setText((mNumberOfInstruction + 1) + ". " + mInstructions.get(mNumberOfInstruction));
                    else
                        mTextView.setText("Recipe Completed hit Next Button to return to menu");
                }
                else
                    mDoneReadingRecipeListener.resetMenu();
            }
        });

        return view;
    }

}
