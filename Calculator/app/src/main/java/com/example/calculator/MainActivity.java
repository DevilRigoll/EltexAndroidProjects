package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator.CalculationProcess;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private CalculationProcess mCalcProc;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.formula_text_view);
        mCalcProc = new CalculationProcess();
    }

    @SuppressLint("ResourceAsColor")
    public void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
            case R.id.zero:
            case R.id.dot:
            case R.id.left_bracket:
            case R.id.right_bracket: {
                Button btn = (Button) findViewById(view.getId());
                mTextView.setText(mTextView.getText() + btn.getText().toString());
                break;
            }
            case R.id.divide:
            case R.id.multiply:
            case R.id.minus:
            case R.id.plus:
            case R.id.exponentiate: {
                Button btn = (Button) findViewById(view.getId());
                mTextView.setText(mTextView.getText() + " " + btn.getText().toString() + " ");
                break;
            }

            case R.id.erase_one: {
                String text = mTextView.getText().toString();
                mTextView.setText(text.substring(0, text.length() > 0 ? text.length() - 1 : 0));
                break;
            }
            case R.id.clear_all: {
                mTextView.setText("");
                break;
            }
            case R.id.equal: {
                mCalcProc.compute(mTextView.getText().toString());

                if (!mCalcProc.isValid()) {
                    mTextView.setBackgroundColor(R.color.unvalid_formula_color);
                    break;
                }

                mTextView.setBackgroundColor(R.color.white);
                mTextView.setText(mCalcProc.getResult().toString());
                break;
            }
        }
    }
}